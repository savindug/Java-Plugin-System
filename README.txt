Example JNI 'Hello World' Application
=====================================

This is an example Gradle project with two sub-projects: c_library and java_app. 

Running/Distributing
--------------------

The build logic up library paths automatically, so you can simply write your Java and C code, and 
type './gradlew run'.

You should also be able to run './gradlew build', to obtain a working distributable zip file in
java_app/build/distributions. This will contain both the compiled C library and .jar file. However,
it *won't* be platform independent.


How it Works
------------

The top-level build.gradle file is essentially irrelevant. All important constructs are in 
java_app/build.gradle and c_library/build.gradle.

The java_app subproject sets up dependencies on parts of the c_library subproject. In order to run
code, we simply need to know where the 'debug' version of the compiled C library is. In order to
create a distributable .zip file, we need to find and copy the 'release' version of the C library.

The :c_library subproject uses the 'cpp-library' plugin. This is the 'new' way of developing 
native libraries for C++. However, there are some things to note:

* It creates separate 'debug' and 'release' versions by applying different compiler/linker options.
* It cannot be used alongside the 'java' plugin, which is why we have two separate sub-projects.
* There's no equivalent 'new' C plugin, but we can simply put our C code in a .cpp file.


Troubleshooting
---------------

"No tool chain is available to build C++ for host operating system..."

If you get this error message, then the message is probably true: you don't have a C++ compiler 
installed. Unfortunately this is one thing that Gradle itself can't just fetch for you; you do 
need to install it yourself.

You'll need one of the following: gcc, clang, Visual Studio (if using Windows) or Xcode (if using 
MacOS). There is a build of gcc available for Windows called "MinGW-W64", and this may be the 
simplest Windows-based option.
