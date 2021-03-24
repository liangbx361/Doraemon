#javac -h . AesJni.java
g++ -c -fPIC -I${JAVA_HOME}/include -I${JAVA_HOME}/include/darwin AesJni.c -o AesJni.o
g++ -dynamiclib -o aes-lib.dylib AesJni.o -lc
