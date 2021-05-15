# TrickRoom
[![Version:0.0.1](https://img.shields.io/badge/TrickRoom-v0.0.1-blue?style=flat-square)]()

<img src="https://cdn.bulbagarden.net/upload/thumb/8/87/Trick_Room_VIII.png/800px-Trick_Room_VIII.png" alt="Trick Room" width="50%"/>

Compiler for Mx* language.
## Mx* language
[Mx*](https://github.com/ACMClassCourses/Compiler-Design-Implementation) is a programming language designed for teaching purpose, 
which resembles a mixture of Java and CPP.

The precise grammar definition can be referenced in MxStar.g4 in the repository.

## Command Options
- `-fsyntax-only`: Perform semantic check only.
- `-emit-llvm`: Generate llvm IR code and stop.
- `-O2`: Do IR-level optimization.
- `-i $FILE`: Redirect input from stdin to file.
- `-o $FILE`: Redirect output from stdout to file.
- `-ir64`: 64-bit address mode to support correct llvm-IR for 64-bit machine.(Incompatible with GC)
## Technique Specs
- Support llvm output
- Implement the following optimizations:
  - ADCE
  - Copy propagation
  - Local CSE
  - Inline
  - LICM
  - SCCP
  - Tail recursion optimization
  - Basic peephole
  