#!/usr/bin/env sh
# Maven Wrapper shell script
BASE_DIR=$(cd "$(dirname "$0")" && pwd)
WRAPPER_DIR="$BASE_DIR/.mvn/wrapper"
WRAPPER_JAR="$WRAPPER_DIR/maven-wrapper.jar"
WRAPPER_PROPERTIES="$WRAPPER_DIR/maven-wrapper.properties"

if [ ! -f "$WRAPPER_JAR" ]; then
  echo "Maven Wrapper JAR not found at $WRAPPER_JAR"
  echo "Download it with:" 
  echo "  curl -L https://repo.maven.apache.org/maven2/org/apache/maven/wrapper/maven-wrapper/3.2.0/maven-wrapper-3.2.0.jar -o $WRAPPER_JAR"
  exit 1
fi

exec java -classpath "$WRAPPER_JAR" -Dmaven.multiModuleProjectDirectory="$BASE_DIR" -Dmaven.home= -Dmaven.ext.class.path= -Dclassworlds.conf="$WRAPPER_PROPERTIES" org.apache.maven.wrapper.MavenWrapperMain "$@"
