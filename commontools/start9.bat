set JAVA_HOME=C:\devtools\java\jdk-9
rem set JAVA_HOME=D:\Program Files\Java\jdk-17.0.12


set PATH=%JAVA_HOME%\bin;%PATH%


java --illegal-access=permit --add-exports=java.desktop/sun.awt.windows=ALL-UNNAMED -jar .\xanderView\target\xanderview-j7-1.0.0.jar a:\\