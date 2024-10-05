@echo off
rem set JAVA_HOME=C:\devtools\java\jdk-9
set JAVA_HOME=D:\Program Files\Java\jdk-17.0.12
rem set JAVA_HOME=A:\jdk

set MY_PATH=C:\devtools\cmder\vendor\conemu-maximus5\ConEmu\Scripts;C:\devtools\cmder\vendor\conemu-maximus5\ConEmu;C:\Windows\system32;C:\Windows;C:\Windows\System32\Wbem;C:\Windows\System32\WindowsPowerShell\v1.0\;C:\Windows\System32\OpenSSH\;C:\devtools\cmder\vendor\git-for-windows\usr\bin\;C:\devtools\cmder\vendor\git-for-windows\mingw64\bin;C:\devtools\cmder;C:\Program Files\Shells\Far Manager;C:\devtools\gpg\..\GnuPG\bin;C:\devtools\gradle\bin;C:\devtools\maven3\bin;C:\devtools\nodejs\;C:\Program Files\CMake\bin;C:\Users\Korvin\AppData\Local\Microsoft\WindowsApps;C:\Users\Korvin\AppData\Roaming\npm;C:\Users\Korvin\.dotnet\tools;


set PATH=%JAVA_HOME%\bin;%MY_PATH%
cls
java -version 
set /p DUMMY=Hit ENTER to continue...
mvn clean install -DskipTests


java -version
