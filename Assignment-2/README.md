# GDB Command Examples

This repository contains examples demonstrating various GDB (GNU Debugger) commands and features. Each example includes a C program and corresponding GDB commands to illustrate debugging concepts.

## Programs and Features

1. `basic.c` - Basic program execution and symbol table loading
   - Running a program
   - Loading symbol table
   - Basic GDB commands

2. `breakpoints.c` - Breakpoint manipulation
   - Setting breakpoints
   - Listing breakpoints
   - Enable/disable breakpoints
   - Break conditions
   - Ignoring breakpoints

3. `variables.c` - Variable examination
   - Listing variables
   - Examining values
   - Printing arrays
   - Memory inspection

4. `functions.c` - Function debugging
   - Function arguments
   - Stack trace examination
   - Stepping into functions

5. `threads.c` - Multi-threaded debugging
   - Thread examination
   - Thread-specific breakpoints
   - Stack trace in multi-threaded context

6. `watchpoint.c` - Watchpoint demonstration
   - Setting watchpoints
   - Monitoring variable changes

7. `core_debug.c` - Core dump analysis
   - Generating core dumps
   - Analyzing core files

8. `attach_debug.c` - Attaching to running processes
   - Debugging running programs
   - Process attachment

## Building the Programs

Use the provided Makefile to build all examples:

```bash
make all
```

Or build individual programs:

```bash
make basic
make breakpoints
# etc...
```

## Common GDB Commands

1. Starting GDB:
   ```bash
   gdb ./program
   ```

2. Running the program:
   ```bash
   (gdb) run
   (gdb) start  # Run to main
   ```

3. Breakpoints:
   ```bash
   (gdb) break function_name
   (gdb) break filename:line_number
   (gdb) info breakpoints
   (gdb) disable breakpoint_number
   (gdb) enable breakpoint_number
   (gdb) ignore breakpoint_number count
   ```

4. Stepping:
   ```bash
   (gdb) next        # Step over
   (gdb) step        # Step into
   (gdb) continue    # Continue execution
   (gdb) finish      # Run until current function returns
   ```

5. Variables and Memory:
   ```bash
   (gdb) print variable
   (gdb) print array[index]
   (gdb) print *pointer
   (gdb) x/nfu address  # Examine memory
   ```

6. Stack:
   ```bash
   (gdb) backtrace
   (gdb) frame number
   (gdb) info locals
   (gdb) info args
   ```

7. Threads:
   ```bash
   (gdb) info threads
   (gdb) thread thread_number
   (gdb) thread apply all command
   ```

## Example Usage

For each program, specific instructions and example debugging sessions are provided in the source code comments.

## Requirements

- GCC compiler
- GDB (GNU Debugger)
- Linux environment (examples are tested on Linux)

## Compilation Flags

The programs are compiled with debugging symbols (-g flag) to enable proper debugging:

```bash
gcc -g program.c -o program
``` 