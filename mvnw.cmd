@REM ----------------------------------------------------------------------------
@REM Maven Wrapper batch script
@REM ----------------------------------------------------------------------------
@echo off
setlocal
set MAVEN_WRAPPER_DIR=%~dp0.mvn\wrapper
set MAVEN_WRAPPER_JAR=%MAVEN_WRAPPER_DIR%\maven-wrapper.jar
set MAVEN_WRAPPER_PROPERTIES=%MAVEN_WRAPPER_DIR%\maven-wrapper.properties

if not exist "%MAVEN_WRAPPER_JAR%" (
  echo Maven Wrapper JAR not found at %MAVEN_WRAPPER_JAR%
  echo Please run: powershell -Command "Invoke-WebRequest -Uri https://repo.maven.apache.org/maven2/org/apache/maven/wrapper/maven-wrapper/3.2.0/maven-wrapper-3.2.0.jar -OutFile .mvn\\wrapper\\maven-wrapper.jar"
  exit /b 1
)

set MAVEN_PROJECTBASEDIR=%~dp0
java -classpath "%MAVEN_WRAPPER_JAR%" -Dmaven.multiModuleProjectDirectory=%MAVEN_PROJECTBASEDIR% -Dmaven.home= -Dmaven.ext.class.path= -Dclassworlds.conf="%MAVEN_WRAPPER_PROPERTIES%" org.apache.maven.wrapper.MavenWrapperMain %*
endlocal
