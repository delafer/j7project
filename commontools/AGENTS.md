# Repository Guide

Start with [docs/ai/FEATURE_MAP.yaml](/docs/ai/FEATURE_MAP.yaml). It is the canonical navigation index for features, modules, startup, and open questions. Use [docs/ai/STARTUP_FLOW.md](/docs/ai/STARTUP_FLOW.md) for runtime tracing, [docs/ai/MODULE_MAP.md](/docs/ai/MODULE_MAP.md) for boundaries, and [docs/ai/PACKAGE_INDEX.tsv](/docs/ai/PACKAGE_INDEX.tsv) for package-level lookup.

## Active Modules
- `xanderView`: the Java 21 desktop application. Canonical startup entry point is `org.delafer.xanderView.gui.MainEntryPoint` and the shaded jar manifest also points there in `xanderView/pom.xml`.
- `commons-j7`: shared infrastructure and utilities. Keep reusable IO, native-loading, text, collection, and generic helper code here.
- `thirddep-j7`: vendored SWT, archive, codec, and native wrapper code. Treat it as dependency/vendor territory; edit only when a change truly belongs in the bundled wrapper layer.

Legacy directories such as `filecleaner-j7`, `security`, `xmlbench`, and `zip5j` are present in the repository but are not part of the root reactor in `pom.xml`.

## Evidence Rules
- Prefer evidence over inference.
- Every architectural claim should point to a concrete class, method, config key, resource, POM entry, or native library reference.
- If something is likely but not proven, mark it as `inferred` or `weak-signal` in docs and explain why.
- Do not assume a feature belongs to a package based on naming alone; trace call paths first.

## Navigation Rules
- For "where is feature X?" questions, consult `docs/ai/FEATURE_MAP.yaml` first, then jump to the listed classes.
- For startup questions, begin at `org.delafer.xanderView.gui.MainEntryPoint`, then follow `initializeApp()`, `MainWindow.open()`, `CommonContainer`, and the reader/decode pipeline.
- For native or codec issues, inspect `commons-j7/net.j7.commons.jni.LibraryLoader`, `thirddep-j7` codec packages, and the resources under `thirddep-j7/src/main/resources`.

## Change Rules
- Keep diffs minimal and task-scoped.
- Do not refactor production code during documentation or reverse-engineering work unless required for correctness.
- Preserve existing formatting and brace style in touched files.
- Avoid editing vendored SWT or third-party wrapper code in `thirddep-j7` unless the task is explicitly about that layer.

## Documentation Maintenance
- When you confirm a new feature or runtime path, update `docs/ai/FEATURE_MAP.yaml` first.
- Keep `FEATURE_MAP.md`, `MODULE_MAP.md`, and `STARTUP_FLOW.md` aligned with the YAML vocabulary.
- Add or update `PACKAGE_INDEX.tsv` entries when introducing new packages or repurposing existing ones.
- Record unresolved gaps in the `open_questions` section instead of guessing.
