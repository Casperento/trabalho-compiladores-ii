# Hands-on Work - Compiler Back-end

Hands-on work for the Compilers II class. The main objective is the implementation of intruction selection and register allocation algorithms, for the MiniJava programming language [(Appel)](https://www.cs.princeton.edu/~appel/modern/java/). The target architecture is MIPS.

# Dependencies

- JDK 17
- make
- javacc (found inside **lib** folder)

# Build Instructions

To build and test the MiniJava compiler you need to execute the following commands:

1. ```$ make --directory src```
2. ```$ java -cp src:src/Parser Main```

(Obs: project built and tested on linux only)
