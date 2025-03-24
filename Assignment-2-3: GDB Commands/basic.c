/*
 * basic.c - Demonstrates basic GDB commands
 * 
 * GDB Commands demonstrated:
 * 1. Starting GDB: gdb ./basic
 * 2. Loading symbols is automatic with -g flag
 * 3. Running: (gdb) run
 * 4. Setting breakpoint: (gdb) break main
 * 5. Examining variables: (gdb) print sum
 */

#include <stdio.h>

int add_numbers(int a, int b) {
    int sum = a + b;
    return sum;
}

int main() {
    int x = 5;
    int y = 10;
    int result;

    printf("Going to add two numbers...\n");
    result = add_numbers(x, y);
    printf("Result of addition: %d\n", result);

    // More operations to demonstrate stepping
    for (int i = 0; i < 3; i++) {
        printf("Counter: %d\n", i);
    }

    return 0;
}

/*
Example GDB session:

$ gdb ./basic
(gdb) break main
(gdb) run
(gdb) next
(gdb) step
(gdb) print x
(gdb) print y
(gdb) continue
*/ 