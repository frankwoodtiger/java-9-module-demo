## Introduction
This is a Helloworld project that demonstrates how Java 9 modules works and how to compile and run them on command line. We have two modules
1. ```com.hello```
2. ```com.greeting```

```com.greeting``` has a dependency of ```com.hello```. ```GreetingServiceImpl``` uses ```sayHello()``` method in HelloService.

## Compile using javac and run using java cli
### ```javac``` and ```java``` common flags
| Command | Flag                 | Shorthand | Explanation                                                                                                                                                                                                                                                                                                                                                                                                                   |
|---------|----------------------|-----------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| javac   | --module             | -m        | List of comma separated module names if we compile in multi-module mode, e.g. ```com.hello,com.greeting```                                                                                                                                                                                                                                                                                                                    |
|         | --module-path        | -p        | External module path that the module we compile depends on, e.g. ```com.hello/outDir```                                                                                                                                                                                                                                                                                                                                       |
|         |                      | -d        | Class output directory for class files                                                                                                                                                                                                                                                                                                                                                                                        |
|         | --module-source-path |           | Specifies where to find source files when compiling code in multiple modules.<br/><br/>The module-specific form allows an explicit search path to be given for any specific module. This form is:<br/><br/>```--module-source-path module-name=file-path (path-separator file-path)*```<br/><br/>The path separator character is ; on Windows, and : otherwise.                                                               |
| java    | --module             | -m        | Executes the main class in a module specified by mainclass if it is given, or, if it is not given, the value in the module.  In other words, mainclass can be used when it is not specified by the module, or to override the value when it is specified. <br/>```java -m module-name[/mainclass]```<br/>where ```module-name``` is the module name and mainclass is the full package class name, e.g ```com.greeting.Main``` |
|         | --module-path        | -p        | Specifies where to find application modules with a list of path elements. The elements of a module path can be a file path to a module or a directory containing modules. Each module is either a modular JAR or an exploded-module directory. <br/><br/>On Windows, semicolons (;) separate path elements in this list; on other platforms it is a colon (:).                                                                |

### Method 1 - Compile and run together, the output directory is at root which is required for compiling using the ```-m``` (```---module```) flag (a.k.a in multi-module mode)
* To compile all modules together
  * using module-pattern form 
    ```
    javac -d outDir -m com.hello,com.greeting --module-source-path "./*/src"
    ```
  * using module-specific form
    ```
    javac -d outDir -m com.hello,com.greeting --module-source-path com.hello=com.hello/src --module-source-path com.greeting=com.greeting/src
    ```
* To run com.greeting.Main
  ```
  java -p outDir -m com.greeting/com.greeting.Main
  ```
* The final output directories should look like this.
  ```
  Helloworld/outDir
  ├── com.greeting
  │   ├── com
  │   │   └── greeting
  │   │       ├── GreetingService.class
  │   │       ├── GreetingServiceImpl.class
  │   │       └── Main.class
  │   └── module-info.class
  └── com.hello
      ├── com
      │   └── hello
      │       ├── HelloService.class
      │       ├── HelloServiceImpl.class
      │       └── Main.class
      └── module-info.class
  ```
### Method 2 - Compile and run modules separately, each output directory is within the module itself
* To manually compile com.hello module in project root
  ```
  javac -d com.hello/outDir $(find com.hello -name "*.java")
  ```
  or preferably without the use of ```find``` to list all source files
  ```
  javac -d com.hello/outDir -m com.hello --module-source-path "./*/src"
  ```
  Note that the above command will create a different file hierarchy (one more nested com.hello module folder) in outDir but still work.

* To manually run com.hello.Main in project root
  ```
  java -p com.hello/outDir -m com.hello/com.hello.Main
  ```
* To manually compile com.greeting module in project root with com.hello as module dependency
  ```
  javac -d com.greeting/outDir $(find com.greeting -name "*.java") -p com.hello/outDir
  ```
* To manually run com.greeting.Main in project root with com.hello as module dependency (use semicolon to separate module path if you are in window)
  ```
  java -p com.hello/outDir:com.greeting/outDir -m com.greeting/com.greeting.Main
  ```
* The final output directories should look like this.
  ```
  Helloworld
  ├── com.hello/outDir
  │   ├── com
  │   │   └── hello
  │   │       ├── HelloService.class
  │   │       ├── HelloServiceImpl.class
  │   │       └── Main.class
  │   └── module-info.class
  └── com.greeting/outDir
      ├── com
      │   └── greeting
      │       ├── GreetingService.class
      │       ├── GreetingServiceImpl.class
      │       └── Main.class
      └── module-info.class
  ```
## Package a jar from modules
#### ```jar [OPTION ...] [ [--release VERSION] [-C dir] files] ...```
#### ```jar``` common flags
| Flag                                   | Explanation                                                                                                              |
|----------------------------------------|--------------------------------------------------------------------------------------------------------------------------|
| -C DIR                                 | Changes the specified directory and includes the files specified at the end of the command line.                         |
| -c or --create                         | Creates the archive.                                                                                                     |
| -v or --verbose                        | Sends or prints verbose output to standard output.                                                                       |
| -f FILE or --file=FILE                 | Specifies the archive file name.                                                                                         |
| -e CLASSNAME or --main-class=CLASSNAME | Specifies the application entry point for standalone applications bundled into a modular or executable modular JAR file. |

Modular JARs are just JARs with a module descriptor module-info.class.
* Assuming you have run ```javac``` to compile and have the class files in ```outDir```
* To create a modular jar from com.hello output
  ```
  jar -cvf jarDir/com.hello.jar -C outDir/com.hello .
  ```
### Method 1 to create com.greeting.jar
* To create a modular jar from com.greeting output
  ```
  jar -cvf jarDir/com.greeting.jar -C outDir/com.greeting .
  ```
* To run the jar (jarDir folder should contain com.greeting module and its dependent modules)
  ```
  java -p jarDir -m com.greeting/com.greeting.Main
  ```
* You can also run with class path flag (```-cp```) assuming you specified your implementation in ```META-INF/services/com.hello.HelloService```. This META-INF must be present in the jar in order to work.
  ```
  java -cp jarDir/com.hello.jar:jarDir/com.greeting.jar com.greeting.Main
  ```
### Method 2 to create com.greeting.jar with main class set for execution entry
* To create a modular jar from com.greeting and set main class with the e flag
  ```
   jar -cvfe jarDir/com.greeting.jar com.greeting.Main -C outDir/com.greeting .  
  ```
* To run the jar without the need of specifying main class
  ```
  java -p jarDir -m com.greeting
  ```
  with ```--show-module-resolution```
  ```
  java --show-module-resolution -p jarDir -m com.greeting 
  ```
### Build and cleanup
#### Use ```build-jar.sh``` to build using method 1 and create jar in jar folder in root.
#### Use ```clean-all.sh``` to clean all output directories if needed.
```sudo bash ./clean-all.sh```

 
