/*
 * core_debug.c - Demonstrates debugging core dumps
 *
 * GDB Commands demonstrated:
 * 1. Load core dump: gdb program core
 * 2. Show crash location: where
 * 3. Examine variables at crash: print var
 * 4. Examine memory at crash: x/nfu addr
 * 5. Show registers: info registers
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

void process_data(int *data, int size) {
    // Intentional buffer overflow
    for (int i = 0; i <= size; i++) {  // Note: <= instead of <
        data[i] = i * 2;
        printf("data[%d] = %d\n", i, data[i]);
    }
}

void recursive_function(int n) {
    char large_buffer[1024];  // Large stack allocation
    sprintf(large_buffer, "Level %d", n);
    
    printf("Recursion level: %d\n", n);
    
    // Infinite recursion (will cause stack overflow)
    if (n > 0) {
        recursive_function(n + 1);
    }
}

void null_pointer_dereference() {
    int *ptr = NULL;
    *ptr = 42;  // Will cause segmentation fault
}

int main(int argc, char *argv[]) {
    printf("Starting core dump demonstration\n");
    printf("This program will demonstrate various crash scenarios\n\n");

    if (argc < 2) {
        printf("Usage: %s <scenario>\n", argv[0]);
        printf("Scenarios:\n");
        printf("1 - Buffer overflow\n");
        printf("2 - Stack overflow\n");
        printf("3 - Null pointer dereference\n");
        return 1;
    }

    int scenario = atoi(argv[1]);
    
    switch (scenario) {
        case 1: {
            printf("Demonstrating buffer overflow:\n");
            int data[5] = {0};
            process_data(data, 5);
            break;
        }
        
        case 2: {
            printf("Demonstrating stack overflow:\n");
            recursive_function(1);
            break;
        }
        
        case 3: {
            printf("Demonstrating null pointer dereference:\n");
            null_pointer_dereference();
            break;
        }
        
        default:
            printf("Invalid scenario number\n");
            return 1;
    }

    return 0;
}

/*
Example GDB core dump debugging session:

1. Enable core dumps (before running):
$ ulimit -c unlimited

2. Run program to generate core:
$ ./core_debug 1  # Or 2 or 3 for different scenarios

3. Load core dump in GDB:
$ gdb ./core_debug core

4. Basic core analysis:
(gdb) bt                  # Show backtrace
(gdb) frame N             # Select frame N
(gdb) info registers      # Show register values
(gdb) x/i $pc            # Examine instruction at crash

5. Examine variables at crash:
(gdb) info locals
(gdb) info args
(gdb) print variable

6. Memory examination:
(gdb) x/32wx $sp         # Examine stack
(gdb) x/s $rdi           # Examine string registers
(gdb) x/i $pc            # Examine current instruction

7. Thread information (if multi-threaded):
(gdb) info threads
(gdb) thread apply all bt

8. Signal information:
(gdb) info signals
(gdb) print $_siginfo

Note: Core dumps must be enabled on your system:
- Check current limit: ulimit -c
- Enable core dumps: ulimit -c unlimited
- Core pattern: /proc/sys/kernel/core_pattern
*/ 