#include <stdlib.h>

void function_with_leak() {
    int *leak = (int *)malloc(sizeof(int) * 10);
    // Memory leak occurs here as the pointer is not freed
}

int main() {
    function_with_leak();
    return 0;
}