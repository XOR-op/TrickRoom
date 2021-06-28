#!/bin/sh
ssh vm 'cat > riscv-assembly/todo.c && /opt/riscv/bin/riscv32-unknown-elf-gcc -S riscv-assembly/todo.c -o /dev/stdout' <  builtin/builtin.c > builtin/builtin.s
