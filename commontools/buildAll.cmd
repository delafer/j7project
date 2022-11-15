set JAVA_HOME="C:\devtools\java\jdk-8sun"
set PATH=%JAVA_HOME%\bin;%PATH%
set MAVEN_OPTS=-Djavax.net.ssl.trustAnchors=%JAVA_HOME%\jre\lib\security\cacerts -Djavax.net.ssl.trustStore=%JAVA_HOME%\jre\lib\security\cacerts
mvn clean install