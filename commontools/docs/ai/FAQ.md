# FAQ

## Where does the application really start?
Short answer: The packaged desktop app starts at `org.delafer.xanderView.gui.MainEntryPoint`.
Where to look: [xanderView/pom.xml](/xanderView/pom.xml), [MainEntryPoint.java](/xanderView/src/main/java/org/delafer/xanderView/gui/MainEntryPoint.java), [STARTUP_FLOW.md](/docs/ai/STARTUP_FLOW.md).
Relevant classes/files: `MainEntryPoint.main`, `MainEntryPoint.initializeApp`, `start17.bat`, `start23.bat`.
Common pitfalls: `MainWindow.open(String)` assumes the path is non-null, even though `MainEntryPoint.main` can pass null when no CLI argument is present.

## Which module owns what?
Short answer: `xanderView` owns viewer behavior, `commons-j7` owns shared helpers and native loading, `thirddep-j7` owns vendored SWT, codec, and archive wrappers.
Where to look: [MODULE_MAP.md](/docs/ai/MODULE_MAP.md), [pom.xml](/pom.xml).
Relevant classes/files: `MainWindow`, `CopyService`, `LibraryLoader`, `SevenZip`.
Common pitfalls: Do not treat `xmlbench`, `zip5j`, `security`, or `filecleaner-j7` as part of the active viewer runtime unless the task explicitly targets them.

## Where are native libraries loaded?
Short answer: Generic extraction and loading is centralized in `net.j7.commons.jni.LibraryLoader`, while actual triggers are scattered across bundled wrappers.
Where to look: [LibraryLoader.java](/commons-j7/src/main/java/net/j7/commons/jni/LibraryLoader.java), [libs.properties](/commons-j7/src/main/resources/libs.properties), [FEATURE_MAP.yaml](/docs/ai/FEATURE_MAP.yaml).
Relevant classes/files: `LibraryLoader.loadLibrary`, `SevenZip.ensureLibraryIsInitialized`, `TJLoader.load`, `ResizerNewOpenCV.<clinit>`, `WebP.loadNativeLibrary`, `JpegXLImageReader.<clinit>`.
Common pitfalls: Native lookup depends on OS and arch naming plus a location hint in `libs.properties`; failures may come from deployment layout rather than Java code.

## How are archives traversed?
Short answer: `CommonContainer` selects `SevenZipReader` for archive paths, and `SevenZipReader` exposes archive members as `ImageZip` entries.
Where to look: [CommonContainer.java](/xanderView/src/main/java/org/delafer/xanderView/file/CommonContainer.java), [SevenZipReader.java](/xanderView/src/main/java/org/delafer/xanderView/file/readers/SevenZipReader.java).
Relevant classes/files: `ArcTypes`, `CommonContainer.getReader`, `SevenZipReader.read`, `ImageZip.rawData`.
Common pitfalls: Recursive directory scan can also descend into archives through `FileReader`, so archive traversal is not limited to opening an archive directly.

## How are encrypted images opened on the fly?
Short answer: `.cry` files are wrapped in `ImageDec`, which decrypts content lazily through `Encryptor` using the keyfile configured by `cry.keyfile`.
Where to look: [ImageFS.java](/xanderView/src/main/java/org/delafer/xanderView/file/entry/ImageFS.java), [ImageZip.java](/xanderView/src/main/java/org/delafer/xanderView/file/entry/ImageZip.java), [Encryptor.java](/xanderView/src/main/java/org/delafer/xanderView/file/entry/Encryptor.java).
Relevant classes/files: `ImageDec.init`, `Encryptor.getCipher`, `ApplConfiguration.pwd`.
Common pitfalls: Archives and filesystem entries both use the same decrypt wrapper, and encrypted entries are skipped entirely if no password is available.

## Where is image resizing and manipulation implemented?
Short answer: `ImageCanvas.preRenderImage()` orchestrates it, `ScaleFactory` chooses the resizer, `OrientationCommons` and `Rotator2D` handle geometry, and `GammaCorrection` handles gamma.
Where to look: [ImageCanvas.java](/xanderView/src/main/java/org/delafer/xanderView/gui/ImageCanvas.java), [ScaleFactory.java](/xanderView/src/main/java/org/delafer/xanderView/scale/ScaleFactory.java).
Relevant classes/files: `ResizerNewOpenCV`, `ResizerJava2D`, `ResizerNobel`, `Rotator2D`, `GammaCorrection`.
Common pitfalls: Some deprecated resizers and tests remain in the tree; the active viewer path goes through `ScaleFactory`, not the older experimental classes.

## How do collect, copy, and export flows work?
Short answer: `MainWindow.bindKeyEvent()` maps copy hotkeys to `CopyService`, which writes output files and maintains a live index of the target directory.
Where to look: [MainWindow.java](/xanderView/src/main/java/org/delafer/xanderView/gui/MainWindow.java), [CopyService.java](/xanderView/src/main/java/org/delafer/xanderView/file/CopyService.java).
Relevant classes/files: `CopyService.copy`, `CopyService.copySync`, `ImageEnc.getEncrypted`, `ImageDec.getOriginal`.
Common pitfalls: Copy state is checked by size plus CRC, so expensive CRC calculation can happen during de-duplication or UI markers.

## How are sorting and filtering rules implemented?
Short answer: Sorting is live and confirmed; filtering code exists but is not confirmed to be wired into the viewer.
Where to look: [ComparatorFactory.java](/xanderView/src/main/java/org/delafer/xanderView/comparator/ComparatorFactory.java), [CommonContainerExt.java](/xanderView/src/main/java/org/delafer/xanderView/file/CommonContainerExt.java), [FilterParser.java](/xanderView/src/main/java/org/delafer/xanderView/filter/FilterParser.java).
Relevant classes/files: `ApplConfiguration.SortBy`, `BasicFileComparator`, `FileDateComparator`, `RandomComparator`, `FilterFactory`.
Common pitfalls: `filterCount` is used for sampling during scans, while the string-based filter package appears dormant and currently contains a recursive parser bug.

## What belongs in commons-j7 and what must stay in xanderView?
Short answer: Generic reusable helpers belong in `commons-j7`; viewer-specific UI, image-entry semantics, and workflows belong in `xanderView`.
Where to look: [MODULE_MAP.md](/docs/ai/MODULE_MAP.md), [PACKAGE_INDEX.tsv](/docs/ai/PACKAGE_INDEX.tsv).
Relevant classes/files: `LibraryLoader`, `AbstractFileProcessor`, `MainWindow`, `ImageAbstract`, `CopyService`.
Common pitfalls: Do not move app-specific archive, decode, or export logic into `commons-j7`; that module should stay generic.

## Where should a new image format decoder be added?
Short answer: Usually add extension and type recognition in `IImageEntry.types`, then either rely on ImageIO SPI in `thirddep-j7` or add a viewer-side wrapper similar to `ImageAimDec`.
Where to look: [IImageEntry.java](/xanderView/src/main/java/org/delafer/xanderView/interfaces/IImageEntry.java), [ImageLoader.java](/xanderView/src/main/java/org/delafer/xanderView/gui/ImageLoader.java), [thirddep-j7/src/main/resources/META-INF/services/javax.imageio.spi.ImageReaderSpi](/thirddep-j7/src/main/resources/META-INF/services/javax.imageio.spi.ImageReaderSpi).
Relevant classes/files: `ImageLoader.loadImage`, `ImageAimDec`, `AvifImageReaderSpi`, `WebPImageReaderSpi`, `JpegXLImageReaderSpi`.
Common pitfalls: If the format needs container semantics or custom naming, ImageIO SPI alone is not enough; you may need a new `ImageAbstract` wrapper.

## Which parts are performance-critical?
Short answer: Byte acquisition, JPEG decode, archive extraction, scaling, and CRC hashing.
Where to look: [ImageLoader.java](/xanderView/src/main/java/org/delafer/xanderView/gui/ImageLoader.java), [ImageZip.java](/xanderView/src/main/java/org/delafer/xanderView/file/entry/ImageZip.java), [ScaleFactory.java](/xanderView/src/main/java/org/delafer/xanderView/scale/ScaleFactory.java), [Hasher.java](/xanderView/src/main/java/org/delafer/xanderView/hash/Hasher.java).
Relevant classes/files: `TJDecompressor`, `HelperFS.readDataBuf`, `ImageZip.MyExtractCallback`, `ResizerNewOpenCV`, `Hasher.calc`.
Common pitfalls: Many test classes are ad hoc benchmarks rather than regression tests; do not treat them as authoritative behavior specs.

## Which areas are likely fragile because of SWT, AWT, or native interaction?
Short answer: The embedded canvas boundary and any code path that lazily loads native libraries.
Where to look: [MainWindow.java](/xanderView/src/main/java/org/delafer/xanderView/gui/MainWindow.java), [SWT_AWT.java](/thirddep-j7/src/main/java/org/eclipse/swt/awt/SWT_AWT.java), [LibraryLoader.java](/commons-j7/src/main/java/net/j7/commons/jni/LibraryLoader.java).
Relevant classes/files: `MainWindow.createImageCanvas`, `MainWindow.show`, `CopyService.CopyObserver.done`, `ResizerNewOpenCV`, `SevenZip.ensureLibraryIsInitialized`.
Common pitfalls: Bugs here often look like rendering glitches, dead UI updates, or missing DLL errors rather than clean Java exceptions.

## Where is runtime state persisted?
Short answer: Config goes to `%APPDATA%/xanderView/xander.properties`, per-image view state goes to `%APPDATA%/xanderView/imagedata.bin`, and last-opened entry goes to `_zlst.csv`.
Where to look: [ApplConfiguration.java](/xanderView/src/main/java/org/delafer/xanderView/gui/config/ApplConfiguration.java), [OrientationStore.java](/xanderView/src/main/java/org/delafer/xanderView/gui/config/OrientationStore.java).
Relevant classes/files: `ApplConfiguration.initialize`, `ApplConfiguration.save`, `OrientationStore.save`, `ImageAbstract.content`.
Common pitfalls: `xander.properties` also exists as a resource template under `src/main/resources`, but live state is read from the appdata copy, not from the classpath resource.
