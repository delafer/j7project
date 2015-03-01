@echo off

rem setlocal EnableDelayedExpansion 
rem set MAVEN_OPTS=""
set JAVA_HOME=E:\Programme\Java\jdk1.8.0.64
set M2_HOME=G:\Mercurial\scm\maven3
set MVN_HOME=G:\Mercurial\scm\maven3
set MAVEN_HOME=G:\Mercurial\scm\maven1\maven
set ANT_HOME=E:\Programme\j7\ant


set PATH=%MAVEN_HOME%\bin;%M2_HOME%\bin\;%JAVA_HOME%\bin;%ANT_HOME%\bin;%MERCURIAL%;%PATH%

color 34

java -version
ant -version

cmd



