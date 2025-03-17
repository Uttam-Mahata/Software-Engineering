#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>

// Function that gradually allocates memory and keeps it
void gradual_allocation(int iterations, int chunk_size) {
    printf("Performing gradual allocation...\n");
    void **pointers = malloc(iterations * sizeof(void*));
    
    for (int i = 0; i < iterations; i++) {
        // Allocate memory and store pointer
        pointers[i] = malloc(chunk_size);
        memset(pointers[i], 1, chunk_size); // Touch memory to ensure pages are allocated
        printf("  Allocated %d bytes (chunk %d of %d)\n", chunk_size, i+1, iterations);
        usleep(100000); // Sleep to make profiling more visible
    }
    
    // Free everything
    printf("Freeing gradual allocations...\n");
    for (int i = 0; i < iterations; i++) {
        free(pointers[i]);
    }
    free(pointers);
}

// Function that demonstrates memory peaks
void allocation_peaks(int iterations, int peak_size) {
    printf("Demonstrating memory usage peaks...\n");
    
    for (int i = 0; i < iterations; i++) {
        // Create a memory peak
        void *peak = malloc(peak_size);
        memset(peak, 1, peak_size);
        printf("  Peak %d of %d: Allocated %d bytes\n", i+1, iterations, peak_size);
        usleep(200000); // Hold for a moment
        
        // Release peak
        free(peak);
        printf("  Released peak %d\n", i+1);
        usleep(100000); // Wait before next peak
    }
}

// Function that intentionally leaks memory
void memory_leak_demo(int leak_size, int count) {
    printf("Demonstrating memory leaks...\n");
    
    for (int i = 0; i < count; i++) {
        // This memory is never freed - intentional leak for demo
        void *leak = malloc(leak_size);
        memset(leak, 2, leak_size);
        printf("  Leaked %d bytes (leak %d of %d)\n", leak_size, i+1, count);
        usleep(100000);
    }
    
    printf("Memory leak demonstration complete.\n");
    // Note: No free() calls, demonstrating leaks
}

// Function that demonstrates realloc behavior
void realloc_demo(int start_size, int iterations, int growth) {
    printf("Demonstrating realloc behavior...\n");
    
    void *buffer = malloc(start_size);
    int current_size = start_size;
    
    printf("  Initial allocation: %d bytes\n", current_size);
    
    for (int i = 0; i < iterations; i++) {
        int new_size = current_size + growth;
        buffer = realloc(buffer, new_size);
        memset(buffer, 3, new_size);
        printf("  Realloc iteration %d: %d -> %d bytes\n", i+1, current_size, new_size);
        current_size = new_size;
        usleep(150000);
    }
    
    free(buffer);
    printf("Realloc demonstration complete.\n");
}

int main() {
    printf("Memory profiling demonstration program\n");
    printf("======================================\n\n");
    
    // Run various memory patterns
    gradual_allocation(10, 1024 * 1024); // 10 chunks of 1MB each
    
    allocation_peaks(5, 5 * 1024 * 1024); // 5 peaks of 5MB each
    
    realloc_demo(1024, 8, 512 * 1024); // Start with 1KB, grow 512KB each step, 8 times
    
    // This will leak memory intentionally for demonstration
    memory_leak_demo(2 * 1024 * 1024, 3); // 3 leaks of 2MB each
    
    printf("\nProgram complete. Massif should have tracked memory usage.\n");
    return 0;
}
