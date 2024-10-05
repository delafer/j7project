rem set JAVA_HOME=C:\devtools\java\jdk-9
rem set JAVA_HOME=D:\Program Files\Java\jdk-23
rem set JAVA_HOME=D:\Program Files\Java\jdk-24
rem set JAVA_HOME=D:\Program Files\Java\graalvm-23
set JAVA_HOME=A:\jdk
set MY_PATH=C:\devtools\cmder\vendor\conemu-maximus5\ConEmu\Scripts;C:\devtools\cmder\vendor\conemu-maximus5\ConEmu;C:\Windows\system32;C:\Windows;C:\Windows\System32\Wbem;C:\Windows\System32\WindowsPowerShell\v1.0\;C:\Windows\System32\OpenSSH\;C:\devtools\cmder\vendor\git-for-windows\usr\bin\;C:\devtools\cmder\vendor\git-for-windows\mingw64\bin;C:\devtools\cmder;C:\Program Files\Shells\Far Manager;C:\devtools\gpg\..\GnuPG\bin;C:\devtools\gradle\bin;C:\devtools\maven3\bin;C:\devtools\nodejs\;C:\Program Files\CMake\bin;C:\Users\Korvin\AppData\Local\Microsoft\WindowsApps;C:\Users\Korvin\AppData\Roaming\npm;C:\Users\Korvin\.dotnet\tools;

set PATH=%JAVA_HOME%\bin;%MY_PATH%
java --version

java --illegal-access=permit --add-exports=java.desktop/sun.awt.windows=ALL-UNNAMED -jar .\xanderView\target\xanderview-j7-1.0.0.jar a:\\