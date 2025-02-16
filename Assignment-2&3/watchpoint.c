/*
 * watchpoint.c - Demonstrates watchpoints in GDB
 *
 * GDB Commands demonstrated:
 * 1. Set watchpoint: watch variable
 * 2. Set read watchpoint: rwatch variable
 * 3. Set access watchpoint: awatch variable
 * 4. Conditional watchpoints: watch variable if condition
 * 5. List watchpoints: info watchpoints
 */

#include <stdio.h>
#include <stdlib.h>

struct Point {
    int x;
    int y;
};

void modify_point(struct Point *p) {
    p->x *= 2;  // Modify x coordinate
    p->y += 5;  // Modify y coordinate
}

void complex_operation(int *value) {
    *value += 10;
    printf("Added 10: %d\n", *value);
    
    *value *= 2;
    printf("Multiplied by 2: %d\n", *value);
    
    *value -= 5;
    printf("Subtracted 5: %d\n", *value);
}

int main() {
    printf("Starting watchpoint demonstration\n");

    // Simple variable to watch
    int number = 42;
    printf("Initial number: %d\n", number);

    // Structure to demonstrate watching members
    struct Point point = {10, 20};
    printf("Initial point: (%d, %d)\n", point.x, point.y);

    // Pointer to demonstrate watching dereferenced values
    int *ptr = malloc(sizeof(int));
    *ptr = 100;
    printf("Initial pointer value: %d\n", *ptr);

    // Modify values to trigger watchpoints
    complex_operation(&number);
    modify_point(&point);
    *ptr = 200;

    printf("\nFinal values:\n");
    printf("Number: %d\n", number);
    printf("Point: (%d, %d)\n", point.x, point.y);
    printf("Pointer value: %d\n", *ptr);

    free(ptr);
    return 0;
}

/*
Example GDB session:

1. Basic watchpoint:
(gdb) break main
(gdb) run
(gdb) watch number
(gdb) continue

2. Watch structure member:
(gdb) watch point.x
(gdb) watch point.y
(gdb) continue

3. Watch pointer value:
(gdb) watch *ptr
(gdb) continue

4. Conditional watchpoint:
(gdb) watch number if number > 50
(gdb) continue

5. Read watchpoint:
(gdb) rwatch number
(gdb) continue

6. Access watchpoint:
(gdb) awatch number
(gdb) continue

7. List and delete watchpoints:
(gdb) info watchpoints
(gdb) delete 1    # Delete watchpoint number 1

8. Watch expressions:
(gdb) watch number + 5
(gdb) continue

Note: When a watchpoint is hit, GDB will show:
- Old value
- New value
- Location where the change occurred
*/ 