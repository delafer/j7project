start javaw -Djava.library.path=c:/daten/libs -XX:ThreadPriorityPolicy=1 -Xverify:none -XX:+LazyBootClassLoader -Xshare:off -client -XX:+LazyBootClassLoader -XX:+DontCompileHugeMethods -XX:-DoEscapeAnalysis -Xincgc  -XX:MaxInlineSize=1024 -XX:FreqInlineSize=1024  -jar c:/daten/imageViewer2.jar %1