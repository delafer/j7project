# Startup Flow

Canonical entry class: `org.delafer.xanderView.gui.MainEntryPoint`

## Launch Path
1. Packaging declares the runtime main class.
   Evidence: [xanderView/pom.xml](/xanderView/pom.xml), `ManifestResourceTransformer.mainClass = org.delafer.xanderView.gui.MainEntryPoint`.
2. Local launch scripts invoke the shaded jar with a starting path.
   Evidence: [start17.bat](/start17.bat), [start23.bat](/start23.bat).

## Bootstrap Sequence
1. `MainEntryPoint.main(String[] args)`
   Evidence: [MainEntryPoint.java](/xanderView/src/main/java/org/delafer/xanderView/gui/MainEntryPoint.java)
   Does:
   - disables several Java2D and HiDPI properties
   - installs the shutdown hook via `Shutdown.addHook()`
   - disables `Metrics`
   - resolves the first CLI argument as `toOpen`
   - calls `initializeApp()`
2. `MainEntryPoint.initializeApp()`
   Evidence: [MainEntryPoint.java](/xanderView/src/main/java/org/delafer/xanderView/gui/MainEntryPoint.java)
   Does:
   - `ImageRepository.loadImages()`
   - `OrientationStore.instance().load()`
   - `CopyService.instance()`
3. `CopyService.instance()`
   Evidence: [CopyService.java](/xanderView/src/main/java/org/delafer/xanderView/file/CopyService.java)
   Does:
   - resolves `target.folder`
   - creates a `FileReader` over the current copy directory
   - asynchronously scans existing copied images
   - starts watching that directory for create and delete changes
4. `new MainWindow().open(toOpen)`
   Evidence: [MainWindow.java](/xanderView/src/main/java/org/delafer/xanderView/gui/MainWindow.java)
   Does:
   - constructs `CommonContainerExt(path)`
   - calls `intitialize()`
   - calls `show()`
   - enters `runGlobalEventLoop()`

## Path And Reader Resolution
1. `CommonContainerExt` delegates to `CommonContainer`.
2. `CommonContainer` restores `_zlst.csv` when no explicit path is supplied.
3. `CommonContainer.getReader(File)` selects:
   - `SevenZipReader` for archive extensions from `ArcTypes`
   - `FileReader` otherwise
4. `CommonContainer.initialize()`:
   - registers content-change callbacks
   - calls `reader.initialize()`
   - calls `reader.read(images)`
   - sets the initial iterator and current item

Evidence:
- [CommonContainer.java](/xanderView/src/main/java/org/delafer/xanderView/file/CommonContainer.java)
- [ArcTypes.java](/xanderView/src/main/java/org/delafer/xanderView/file/readers/ArcTypes.java)
- [FileReader.java](/xanderView/src/main/java/org/delafer/xanderView/file/readers/FileReader.java)
- [SevenZipReader.java](/xanderView/src/main/java/org/delafer/xanderView/file/readers/SevenZipReader.java)

## UI Bootstrap
1. `MainWindow.intitialize()`
   - creates SWT `Display`
   - records display size
   - disables SWT warnings
   - installs a global SWT key filter that forwards to `bindKeyEvent(Event)`
2. `MainWindow.show()`
   - creates `MultiShell`
   - restores window position from `ApplConfiguration`
   - creates menu bar
   - creates the embedded image canvas
   - opens the active shell and applies fullscreen state
3. `MainWindow.createImageCanvas()`
   - creates `ImageCanvas`
   - creates an SWT `Composite`
   - embeds an AWT `Frame` with `SWT_AWT.new_Frame`
   - mounts the AWT canvas in that frame

Evidence:
- [MainWindow.java](/xanderView/src/main/java/org/delafer/xanderView/gui/MainWindow.java)
- [MultiShell.java](/xanderView/src/main/java/org/delafer/xanderView/gui/helpers/MultiShell.java)
- [ImageRepository.java](/xanderView/src/main/java/org/delafer/xanderView/gui/helpers/ImageRepository.java)
- [SWT_AWT.java](/thirddep-j7/src/main/java/org/eclipse/swt/awt/SWT_AWT.java)

## First Image Load
1. After the shell opens, `MainWindow.show()` checks `pointer.getCurrent()`.
2. If an entry exists, it schedules `loadImage(pointer, pointer.getCurrent(), panel)` on `java.awt.EventQueue`.
3. `ImageLoader.loadImage(...)`:
   - resolves entry text and index info
   - loads bytes via `ImageAbstract.content()`
   - dispatches by `ImageType`
   - sends the decoded image to `ImageCanvas.setImage(...)`
4. `ImageCanvas.preRenderImage()`:
   - computes target size
   - chooses a scaler via `ScaleFactory`
   - applies orientation with `Rotator2D`
   - applies gamma correction when needed
5. `ImageCanvas.showImage()` paints to the AWT canvas.

Evidence:
- [ImageLoader.java](/xanderView/src/main/java/org/delafer/xanderView/gui/ImageLoader.java)
- [ImageCanvas.java](/xanderView/src/main/java/org/delafer/xanderView/gui/ImageCanvas.java)
- [ScaleFactory.java](/xanderView/src/main/java/org/delafer/xanderView/scale/ScaleFactory.java)

## Event Loop And Shutdown
1. `MainWindow.runGlobalEventLoop()` runs SWT dispatch until the shells collapse.
2. User actions remain centralized in `MainWindow.bindKeyEvent(Event)`.
3. On JVM shutdown, `Shutdown.addHook()` persists:
   - `xander.properties`
   - `imagedata.bin`
   - pending `CopyLogger` lines
4. The hook waits briefly for outstanding copy tasks via `ApplInstance.openTasks`.

Evidence:
- [MainWindow.java](/xanderView/src/main/java/org/delafer/xanderView/gui/MainWindow.java)
- [Shutdown.java](/xanderView/src/main/java/org/delafer/xanderView/general/Shutdown.java)
- [ApplConfiguration.java](/xanderView/src/main/java/org/delafer/xanderView/gui/config/ApplConfiguration.java)
- [OrientationStore.java](/xanderView/src/main/java/org/delafer/xanderView/gui/config/OrientationStore.java)

## Observed Risks
- `MainWindow.open(String path)` calls `path.startsWith(...)` without guarding for null, but `MainEntryPoint.main` can pass null when no CLI argument is present.
- The initial image decode is dispatched through AWT while keyboard handling and shell management stay on SWT, so thread-boundary bugs will tend to surface here first.
