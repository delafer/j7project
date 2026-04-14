# Architecture

## High-Level Shape
`xanderView` is a desktop image viewer that sits on three layers:
- app-specific viewer logic in `xanderView`
- shared support utilities in `commons-j7`
- vendored UI, native, and codec wrappers in `thirddep-j7`

Evidence:
- [pom.xml](/pom.xml)
- [xanderView/pom.xml](/xanderView/pom.xml)
- [thirddep-j7/pom.xml](/thirddep-j7/pom.xml)

## UI Architecture
The runtime UI is SWT-first, but actual image painting is AWT.

Confirmed structure:
- `MainEntryPoint` creates `MainWindow`.
- `MainWindow` creates SWT `Display`, `MultiShell`, menu bar, and an SWT `Composite`.
- `MainWindow.createImageCanvas()` embeds an AWT `Frame` with `SWT_AWT.new_Frame(...)`.
- `ImageCanvas` is an AWT `Canvas` that owns the current `BufferedImage`, scale, gamma, orientation, and paint path.

Evidence:
- [MainWindow.java](/xanderView/src/main/java/org/delafer/xanderView/gui/MainWindow.java)
- [ImageCanvas.java](/xanderView/src/main/java/org/delafer/xanderView/gui/ImageCanvas.java)
- [MultiShell.java](/xanderView/src/main/java/org/delafer/xanderView/gui/helpers/MultiShell.java)
- [SWT_AWT.java](/thirddep-j7/src/main/java/org/eclipse/swt/awt/SWT_AWT.java)

Threading model:
- SWT drives shell lifecycle and key events.
- AWT `EventQueue.invokeLater(...)` is used for the first image load.
- SWT `Display.asyncExec(...)` is used for fullscreen toggles and copy-result feedback.

## Archive And IO Flow
Input resolution is path-based and normalizes directories and archives into the same `ImageAbstract` model.

Confirmed flow:
1. `CommonContainer.getReader(File)` chooses `SevenZipReader` for archive extensions and `FileReader` otherwise.
2. `FileReader.read(...)` walks directories with `AbstractFileProcessor`.
3. In recursive mode, `FileReader` descends into archives by spinning up `SevenZipReader`.
4. `ImageFS` and `ImageZip` expose filesystem and archive members behind the same API.
5. `Buf` wraps direct or mapped byte buffers and centralizes buffer cleanup.

Evidence:
- [CommonContainer.java](/xanderView/src/main/java/org/delafer/xanderView/file/CommonContainer.java)
- [FileReader.java](/xanderView/src/main/java/org/delafer/xanderView/file/readers/FileReader.java)
- [SevenZipReader.java](/xanderView/src/main/java/org/delafer/xanderView/file/readers/SevenZipReader.java)
- [ImageFS.java](/xanderView/src/main/java/org/delafer/xanderView/file/entry/ImageFS.java)
- [ImageZip.java](/xanderView/src/main/java/org/delafer/xanderView/file/entry/ImageZip.java)
- [HelperFS.java](/xanderView/src/main/java/org/delafer/xanderView/file/entry/HelperFS.java)

Live update behavior:
- `FileReader.initialize()` registers a `WatchService` with `FILE_TREE`.
- `CommonContainer` and `CopyService` both react to create and delete events and mutate their in-memory sets.

## Image Decode And Processing Pipeline
Decode is lazy and entry-driven.

Confirmed flow:
1. `ImageAbstract.content()` records the last opened entry and retrieves a `Buf`.
2. `ImageLoader.loadImage(...)` dispatches by `ImageType`.
3. JPEG takes a TurboJPEG fast path in `loadJpegImage(...)`.
4. AVIF, WebP, JXL, PNG, and BMP fall back to `ImageIO.read(...)`.
5. `.aim` is unpacked by `ImageAimDec` before decode.
6. `ImageCanvas.preRenderImage()` handles fit-to-window scaling, rotation and flip, and gamma correction.

Evidence:
- [ImageLoader.java](/xanderView/src/main/java/org/delafer/xanderView/gui/ImageLoader.java)
- [IImageEntry.java](/xanderView/src/main/java/org/delafer/xanderView/interfaces/IImageEntry.java)
- [ImageAimDec.java](/xanderView/src/main/java/org/delafer/xanderView/file/entry/ImageAimDec.java)
- [ImageCanvas.java](/xanderView/src/main/java/org/delafer/xanderView/gui/ImageCanvas.java)
- [ScaleFactory.java](/xanderView/src/main/java/org/delafer/xanderView/scale/ScaleFactory.java)
- [GammaCorrection.java](/xanderView/src/main/java/org/delafer/xanderView/process/GammaCorrection.java)

Performance-critical areas:
- JPEG decode via TurboJPEG.
- OpenCV resizers in `ResizerNewOpenCV` and `ResizerOpenCV`.
- memory-mapped reads in `HelperFS.readDataBuf(...)`.
- CRC hashing in `Hasher.calc(...)`.
- 7-Zip extract callback path in `ImageZip.MyExtractCallback`.

## Crypto Flow
Encrypted images are treated as regular image entries with a decryption wrapper.

Confirmed flow:
1. `ImageFS.getInstance(...)` and `ImageZip.getInstance(...)` wrap `.cry` files in `ImageDec`.
2. `Encryptor.getCipher(...)` derives a key from `ApplConfiguration.pwd()`.
3. `ImageDec.init()` decrypts content, derives the original file name, and exposes the plaintext payload.
4. `ImageEnc` can re-wrap plaintext bytes for encrypted export.

Evidence:
- [ImageFS.java](/xanderView/src/main/java/org/delafer/xanderView/file/entry/ImageFS.java)
- [ImageZip.java](/xanderView/src/main/java/org/delafer/xanderView/file/entry/ImageZip.java)
- [Encryptor.java](/xanderView/src/main/java/org/delafer/xanderView/file/entry/Encryptor.java)
- [ImageDec.java](/xanderView/src/main/java/org/delafer/xanderView/file/entry/ImageDec.java)
- [ImageEnc.java](/xanderView/src/main/java/org/delafer/xanderView/file/entry/ImageEnc.java)

## Logging, Config, And Persistence
State is split between configuration properties, per-image view state, last-entry recovery, and optional copy-history logs.

Confirmed artifacts:
- `%APPDATA%/xanderView/xander.properties`
- `%APPDATA%/xanderView/imagedata.bin`
- `_zlst.csv`
- `history_dd_MM_yy.log` in the active copy directory

Evidence:
- [ApplConfiguration.java](/xanderView/src/main/java/org/delafer/xanderView/gui/config/ApplConfiguration.java)
- [OrientationStore.java](/xanderView/src/main/java/org/delafer/xanderView/gui/config/OrientationStore.java)
- [CopyLogger.java](/xanderView/src/main/java/org/delafer/xanderView/common/CopyLogger.java)
- [Shutdown.java](/xanderView/src/main/java/org/delafer/xanderView/general/Shutdown.java)

## Native Integration
Native loading is centralized, but actual triggers are spread across codec and archive wrappers.

Confirmed pieces:
- `LibraryLoader` resolves OS and arch, copies DLLs or shared objects out of resources or configured locations, and calls `System.load(...)`.
- `SevenZip.ensureLibraryIsInitialized()` forces 7-Zip native libs before archive access.
- `ResizerNewOpenCV` and `ResizerOpenCV` load `opencv_java460`.
- `TJLoader` routes TurboJPEG loading through the vendored SWT `Library`.
- WebP and JPEG XL readers also perform lazy native loads.

Evidence:
- [LibraryLoader.java](/commons-j7/src/main/java/net/j7/commons/jni/LibraryLoader.java)
- [libs.properties](/commons-j7/src/main/resources/libs.properties)
- [SevenZip.java](/thirddep-j7/src/main/java/net/sf/sevenzipjbinding/SevenZip.java)
- [ResizerNewOpenCV.java](/xanderView/src/main/java/org/delafer/xanderView/scale/ResizerNewOpenCV.java)
- [TJLoader.java](/thirddep-j7/src/main/java/org/libjpegturbo/turbojpeg/TJLoader.java)
- [thirddep-j7/src/main/resources/win32-x86-64](/thirddep-j7/src/main/resources/win32-x86-64)

## Coupling Hotspots
- `MainWindow` is a control hub for startup, menu creation, key bindings, fullscreen, copy actions, and initial image loading.
- `CommonContainer` mixes startup recovery, reader selection, list sorting, and navigation state.
- `CopyService` mixes copy execution, target indexing, live watching, observer notifications, and history logging.
- `thirddep-j7` bundles large vendored subsystems, so changes there carry high unintended-coupling risk.

Evidence:
- [MainWindow.java](/xanderView/src/main/java/org/delafer/xanderView/gui/MainWindow.java)
- [CommonContainer.java](/xanderView/src/main/java/org/delafer/xanderView/file/CommonContainer.java)
- [CopyService.java](/xanderView/src/main/java/org/delafer/xanderView/file/CopyService.java)

## Unresolved Questions
- `MainWindow.open(String)` can throw when no CLI path is provided.
- `xav` is typed as a virtual directory but no concrete implementation was confirmed.
- The string-based filter subsystem looks unfinished or unused.
