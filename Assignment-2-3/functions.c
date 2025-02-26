/*
 * functions.c - Demonstrates function debugging and stack trace examination
 *
 * GDB Commands demonstrated:
 * 1. Step into function: step
 * 2. Step over function: next
 * 3. Continue execution: continue
 * 4. Print backtrace: bt
 * 5. Select stack frame: frame N
 * 6. Show function arguments: info args
 * 7. Show local variables: info locals
 */

#include <stdio.h>

int factorial(int n) {
    if (n <= 1) {
        return 1;
    }
    return n * factorial(n - 1);
}

void print_info(const char *message, int value) {
    printf("%s: %d\n", message, value);
}

int calculate_sum(int a, int b, int c) {
    int temp = a + b;
    print_info("Partial sum (a+b)", temp);
    return temp + c;
}

int complex_calculation(int x) {
    int fact = factorial(x);
    print_info("Factorial", fact);
    
    int sum = calculate_sum(x, fact, 10);
    print_info("Final sum", sum);
    
    return sum;
}

int main() {
    printf("Starting function debugging demonstration\n");

    int input = 4;
    int result = complex_calculation(input);

    printf("Final result: %d\n", result);
    return 0;
}

/*
Example GDB session:

1. Basic function debugging:
(gdb) break main
(gdb) run
(gdb) step        # Steps into complex_calculation
(gdb) next        # Steps over function call
(gdb) continue    # Continues execution

2. Examining stack frames:
(gdb) break factorial
(gdb) run
(gdb) backtrace   # Shows call stack
(gdb) frame 0     # Select current frame
(gdb) frame 1     # Select caller's frame
(gdb) info args   # Show function arguments
(gdb) info locals # Show local variables

3. Examining recursive calls:
(gdb) break factorial
(gdb) run
(gdb) continue    # Watch recursive calls
(gdb) backtrace   # See recursive stack

4. Setting breakpoints with conditions:
(gdb) break factorial if n == 1
(gdb) continue

5. Finishing current function:
(gdb) finish      # Run until function returns

6. Examining variables across frames:
(gdb) break calculate_sum
(gdb) continue
(gdb) print a
(gdb) print b
(gdb) print c
(gdb) backtrace
(gdb) frame 1
(gdb) print x
*/ 