#!/bin/bash
echo "Building JavaFX Client..."
MAVEN_OPTS="--add-opens java.base/java.lang=ALL-UNNAMED" mvn clean package

if [ $? -eq 0 ]; then
echo "Build OK!"
echo "JAR: target/javafx-klijent-1.0.0.jar"
else
echo "Build failed!"
exit 1
fi