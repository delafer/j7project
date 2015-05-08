@echo off

rem setlocal EnableDelayedExpansion 
rem set MAVEN_OPTS="-Xmx1536m -server -Xss1m -Xverify:none -XX:+RelaxAccessControlCheck -XX:+AggressiveOpts -XX:+UnlockExperimentalVMOptions -XX:+UseConcMarkSweepGC -XX:-DontCompileHugeMethods"
set JAVA_HOME=E:\Programme\Java\jdk1.6.0_27.64
set M2_HOME=E:\Programme\j7\mvn
set MVN_HOME=E:\Programme\j7\mvn
set MAVEN_HOME=E:\Mercurial\scm\maven1\maven
set ANT_HOME=E:\Programme\j7\ant


set PATH=%MAVEN_HOME%\bin;%M2_HOME%\bin\;%JAVA_HOME%\bin;%ANT_HOME%\bin;%MERCURIAL%;%PATH%

color 34

java -version
ant -version

cmd



