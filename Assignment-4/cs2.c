#include <stdio.h>

int main() {
    int x;
    int y = 10;
    int z;
    
    z = x + y;  // x is uninitialized
    if (z > 20) {
        printf("z is greater than 20\n");
    }
    
    return 0;
}