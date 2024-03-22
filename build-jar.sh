#!/bin/bash
javac -d outDir -m com.hello,com.greeting --module-source-path "./*/src"

jar -cvf jarDir/com.hello.jar -C outDir/com.hello .

jar -cvfe jarDir/com.greeting.jar com.greeting.Main -C outDir/com.greeting .

# may need sudo
# sudo bash ./build-jar.sh