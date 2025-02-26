/*
 * breakpoints.c - Demonstrates various breakpoint features in GDB
 *
 * GDB Commands demonstrated:
 * 1. Setting breakpoints: break function_name
 * 2. Setting breakpoints at line: break line_number
 * 3. Listing breakpoints: info breakpoints
 * 4. Enabling/disabling: enable/disable <breakpoint_num>
 * 5. Conditional breaks: break line_number if condition
 * 6. Ignoring N times: ignore <breakpoint_num> <N>
 */

#include <stdio.h>

void process_number(int num) {
    int square = num * num;
    printf("Processing number: %d, square: %d\n", num, square);
}

void process_range(int start, int end) {
    for (int i = start; i <= end; i++) {
        if (i % 2 == 0) {
            printf("Even number found: %d\n", i);
        }
        process_number(i);
    }
}

int main() {
    printf("Starting breakpoint demonstration program\n");

    // This loop helps demonstrate ignore count
    for (int i = 0; i < 5; i++) {
        printf("Iteration %d\n", i);
    }

    // This call helps demonstrate conditional breakpoints
    process_range(1, 5);

    printf("Program finished\n");
    return 0;
}

/*
Example GDB session:

1. Basic breakpoint:
(gdb) break main
(gdb) run

2. Function breakpoint:
(gdb) break process_number
(gdb) continue

3. Line number breakpoint:
(gdb) break breakpoints.c:23
(gdb) continue

4. List all breakpoints:
(gdb) info breakpoints

5. Disable/Enable breakpoint:
(gdb) disable 2
(gdb) enable 2

6. Conditional breakpoint:
(gdb) break process_number if num > 3
(gdb) continue

7. Ignore breakpoint N times:
(gdb) ignore 1 3
(gdb) continue

8. Delete breakpoint:
(gdb) delete 1
*/ 