#!/bin/bash
javac -d outDir -m com.hello,com.greeting --module-source-path "./*/src"
# If you want to run the jar with class path flag only:
#
#   java -cp jarDir/com.hello.jar:jarDir/com.greeting.jar com.greeting.Main
#
# , then you need META-INF to tell java exactly which implementation of service you want to include.
# Otherwise, you get this error:
# Exception in thread "main" java.util.NoSuchElementException
#        at java.base/java.util.ServiceLoader$2.next(ServiceLoader.java:1318)
#        at java.base/java.util.ServiceLoader$2.next(ServiceLoader.java:1306)
#        at java.base/java.util.ServiceLoader$3.next(ServiceLoader.java:1403)
#        at com.greeting.GreetingServiceImpl.<init>(GreetingServiceImpl.java:13)
#        at com.greeting.Main.main(Main.java:7)
# See more in https://docs.oracle.com/javase/7/docs/technotes/guides/jar/jar.html#The_META-INF_directory
# There might be better way to just use javac without cp explicitly but I could not figure it out yet.
cp -r com.hello/META-INF outDir/com.hello

jar -cvf jarDir/com.hello.jar -C outDir/com.hello .

jar -cvfe jarDir/com.greeting.jar com.greeting.Main -C outDir/com.greeting .