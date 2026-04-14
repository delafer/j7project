# Module Map

## Active Reactor
Evidence base: [pom.xml](/pom.xml), [commons-j7/pom.xml](/commons-j7/pom.xml), [thirddep-j7/pom.xml](/thirddep-j7/pom.xml), [xanderView/pom.xml](/xanderView/pom.xml).

| module | packaging | java target | depends on | role | key evidence |
| --- | --- | --- | --- | --- | --- |
| `commons-j7` | `jar` | 16 | none in reactor | Shared utilities, IO, path handling, native loader, and bit or stream helpers. | `LibraryLoader`, `AbstractFileProcessor` |
| `thirddep-j7` | `jar` | 16 | `commons-j7` | Vendored dependency wrapper jar containing SWT, TurboJPEG, 7-Zip, WebP, AVIF, JPEG XL, and OpenCL helpers. | `SevenZip`, `TJLoader`, `META-INF/services/javax.imageio.spi.ImageReaderSpi` |
| `xanderView` | `jar` | 21 | `commons-j7`, `thirddep-j7` | Main desktop viewer application and the only active GUI target in the root reactor. | `MainEntryPoint`, `MainWindow` |

Dependency shape:
- Root reactor includes only `commons-j7`, `thirddep-j7`, and `xanderView`.
- `xanderView` depends on both support jars.
- `thirddep-j7` uses `commons-j7` for native loading and shared helpers.
- The shaded `xanderView` jar sets `org.delafer.xanderView.gui.MainEntryPoint` as `Main-Class`.

## Legacy Modules
These directories exist and have POMs, but they are not listed in the root `<modules>` section.

| module | role | dependencies | evidence |
| --- | --- | --- | --- |
| `filecleaner-j7` | Legacy CLI and utility jar. | `commons-j7` | [filecleaner-j7/pom.xml](/filecleaner-j7/pom.xml) |
| `security` | Crypto playground. | `commons-j7`, BouncyCastle | [security/pom.xml](/security/pom.xml) |
| `xmlbench` | SWT and XML compression workbench. | `commons-j7`, `thirddep-j7` | [xmlbench/pom.xml](/xmlbench/pom.xml) |
| `zip5j` | Zip utility playground. | `commons-j7`, `thirddep-j7` | [zip5j/pom.xml](/zip5j/pom.xml) |

Operational rule:
- Treat legacy modules as repository context, not as part of xanderView runtime unless the task explicitly targets them.

## Boundary Rules
`xanderView`
- Put viewer-specific UI, container navigation, image entry wrappers, copy/export logic, and persisted viewer behavior here.
- Evidence: [MainWindow.java](/xanderView/src/main/java/org/delafer/xanderView/gui/MainWindow.java), [CopyService.java](/xanderView/src/main/java/org/delafer/xanderView/file/CopyService.java), [ImageDec.java](/xanderView/src/main/java/org/delafer/xanderView/file/entry/ImageDec.java).

`commons-j7`
- Put reusable filesystem, native loading, stream, string, and generic helper code here.
- Evidence: [LibraryLoader.java](/commons-j7/src/main/java/net/j7/commons/jni/LibraryLoader.java), [AbstractFileProcessor.java](/commons-j7/src/main/java/net/j7/commons/io/AbstractFileProcessor.java).

`thirddep-j7`
- Use for vendored wrappers and bundled third-party integrations. Avoid casual edits because most packages are external code copied into the repo.
- Evidence: [thirddep-j7/pom.xml](/thirddep-j7/pom.xml), [SevenZip.java](/thirddep-j7/src/main/java/net/sf/sevenzipjbinding/SevenZip.java), [SWT_AWT.java](/thirddep-j7/src/main/java/org/eclipse/swt/awt/SWT_AWT.java).

## Native And Resource Responsibilities
`commons-j7`
- Owns generic native extraction and loading plus appdata path resolution through `libs.properties`.

`thirddep-j7`
- Owns bundled codec SPI declarations and packaged DLLs such as `jxl_jni.dll`, `jxl.dll`, and `jxl_threads.dll`.

`xanderView`
- Owns app resources: `xander.properties`, icons, and `audio/sound01.wav`.

## Blind Spots
- `thirddep-j7` vendors a large amount of SWT code; only a small subset is directly exercised by xanderView.
- Native deployment still relies on `commons-j7/src/main/resources/libs.properties`, which currently points to `C:/Program Files/xview`; confirm whether packaged deployments override this.
