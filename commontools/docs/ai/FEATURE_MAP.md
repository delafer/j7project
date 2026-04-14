# Feature Map

Canonical source: [FEATURE_MAP.yaml](/docs/ai/FEATURE_MAP.yaml).

## Input And Browsing
| id | status | summary | primary classes | evidence |
| --- | --- | --- | --- | --- |
| `archive_browsing` | confirmed | Opens `.zip`, `.rar`, `.7z`, and `.jzip` containers as virtual image sets. | `CommonContainer`, `SevenZipReader`, `ImageZip` | `CommonContainer.getReader`, `SevenZipReader.read`, `ImageZip.rawData` |
| `recursive_scan_and_live_watch` | confirmed | Walks directories, optionally descends into archives, and applies create/delete events to in-memory lists. | `FileReader`, `CommonContainer`, `CopyService` | `FileReader.read`, `FileReader.initialize`, `CommonContainer.initialize` |
| `sort_navigation_and_selection` | confirmed | Sorts images by config, supports random mode and copy-existence filtering, and restores last opened entries. | `ComparatorFactory`, `CommonContainer`, `CommonContainerExt` | `ComparatorFactory.getComparator`, `CommonContainerExt.switchRandomAccess`, `MainWindow.bindKeyEvent` |
| `filter_subsystem` | weak-signal | Filter classes exist, but no confirmed viewer integration was found. | `FilterParser`, `FilterFactory`, `FilterSize` | `FilterParser.parse`, `ApplConfiguration.getFilterCount` |

Evidence notes:
- Archive reader selection is in [CommonContainer.java](/xanderView/src/main/java/org/delafer/xanderView/file/CommonContainer.java).
- Recursive file walk and live watch are in [FileReader.java](/xanderView/src/main/java/org/delafer/xanderView/file/readers/FileReader.java).
- Sort behavior is configured in [ApplConfiguration.java](/xanderView/src/main/java/org/delafer/xanderView/gui/config/ApplConfiguration.java) and resolved in [ComparatorFactory.java](/xanderView/src/main/java/org/delafer/xanderView/comparator/ComparatorFactory.java).

## Decode And Rendering
| id | status | summary | primary classes | evidence |
| --- | --- | --- | --- | --- |
| `image_decode_pipeline` | confirmed | Resolves image bytes and decodes through TurboJPEG or ImageIO-registered codecs. | `ImageLoader`, `ImageAbstract`, `Buf` | `ImageLoader.loadImage`, `ImageLoader.loadJpegImage`, `ImageAbstract.content` |
| `encrypted_image_flow` | confirmed | Wraps `.cry` entries, derives a key from `cry.keyfile`, decrypts on demand, and can re-export encrypted data. | `ImageDec`, `ImageEnc`, `Encryptor` | `ImageFS.getInstance`, `ImageZip.getInstance`, `Encryptor.decrypt` |
| `aim_container_decode` | confirmed | Parses `.aim` payload headers and exposes the first embedded image as a normal image entry. | `ImageAimDec`, `ImageLoader` | `ImageLoader.loadImage`, `ImageAimDec.extractFirstFile` |
| `resize_orientation_gamma` | confirmed | Fits to canvas, chooses a resizer, rotates/flips, and applies gamma correction before paint. | `ImageCanvas`, `ScaleFactory`, `ResizerNewOpenCV`, `Rotator2D`, `GammaCorrection` | `ImageCanvas.preRenderImage`, `ScaleFactory.getInstanceByType0` |
| `native_codec_and_library_loading` | confirmed | Extracts and loads native libraries and registers bundled ImageIO codecs. | `LibraryLoader`, `SevenZip`, `TJLoader`, `WebP`, `JpegXLImageReader` | `LibraryLoader.loadLibrary`, `SevenZip.ensureLibraryIsInitialized`, `META-INF/services/...ImageReaderSpi` |
| `swt_awt_threading_bridge` | confirmed | Embeds AWT inside SWT and crosses `EventQueue` and `Display.asyncExec` for rendering and feedback. | `MainWindow`, `MultiShell`, `ImageCanvas`, `SWT_AWT` | `MainWindow.createImageCanvas`, `MainWindow.show`, `CopyService.CopyObserver.done` |

Evidence notes:
- JPEG fast path lives in [ImageLoader.java](/xanderView/src/main/java/org/delafer/xanderView/gui/ImageLoader.java).
- Type registration is in [IImageEntry.java](/xanderView/src/main/java/org/delafer/xanderView/interfaces/IImageEntry.java).
- OpenCV loaders are in [ResizerNewOpenCV.java](/xanderView/src/main/java/org/delafer/xanderView/scale/ResizerNewOpenCV.java) and [ResizerOpenCV.java](/xanderView/src/main/java/org/delafer/xanderView/scale/ResizerOpenCV.java).
- Codec SPI registration is in [thirddep-j7/src/main/resources/META-INF/services/javax.imageio.spi.ImageReaderSpi](/thirddep-j7/src/main/resources/META-INF/services/javax.imageio.spi.ImageReaderSpi).

## Output And Persistence
| id | status | summary | primary classes | evidence |
| --- | --- | --- | --- | --- |
| `copy_collect_export` | confirmed | Copies current images to the active target directory and supports encrypted, decrypted, and original variants. | `CopyService`, `MainWindow`, `ImageEnc`, `ImageDec` | `MainWindow.bindKeyEvent`, `CopyService.copySync` |
| `config_and_state_persistence` | confirmed | Saves window and config properties plus per-image view state in the appdata directory. | `ApplConfiguration`, `OrientationStore`, `Shutdown` | `ApplConfiguration.initialize`, `OrientationStore.save`, `Shutdown.addHook` |
| `history_logging` | confirmed | Buffers copy history and writes date-stamped logs in the target directory when `history` is enabled. | `CopyLogger`, `CopyService` | `CopyLogger.add`, `CopyService.nioTransferCopy` |

Evidence notes:
- Copy hotkeys and menu actions are in [MainWindow.java](/xanderView/src/main/java/org/delafer/xanderView/gui/MainWindow.java).
- Appdata persistence is implemented in [ApplConfiguration.java](/xanderView/src/main/java/org/delafer/xanderView/gui/config/ApplConfiguration.java) and [OrientationStore.java](/xanderView/src/main/java/org/delafer/xanderView/gui/config/OrientationStore.java).
- Shutdown persistence is centralized in [Shutdown.java](/xanderView/src/main/java/org/delafer/xanderView/general/Shutdown.java).

## Module Ownership
| module | owns |
| --- | --- |
| `xanderView` | Viewer UI, startup, navigation, entry abstractions, decode/export workflows, config, and state persistence. |
| `commons-j7` | Shared utility code and the native library extraction/loading path. |
| `thirddep-j7` | Vendored SWT, codecs, archive bindings, and native wrappers consumed by `xanderView`. |

## Weak Signals And Blind Spots
- `filter_subsystem`: code exists, but there is no confirmed call site in the live viewer path and `FilterParser.parse()` currently loops into itself.
- `xav` support: `IImageEntry.types` and `HelperFS.isVirtualDirectory()` mention `xav`, but no concrete decoder or container reader was confirmed.
- No dedicated EXIF or rich metadata subsystem was confirmed beyond size, last-modified, CRC, and persisted view state.
