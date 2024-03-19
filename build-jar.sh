#!/bin/bash
javac -d outDir -m com.hello,com.greeting --module-source-path "./*/src"

jar -cvf jar/com.hello.jar -C outDir/com.hello .

jar -cvf jar/com.greeting.jar -C outDir/com.greeting .

# may need sudo
# sudo bash ./build-jar.sh