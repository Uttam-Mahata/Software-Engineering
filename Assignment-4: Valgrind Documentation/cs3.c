#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <string.h>

#define ARRAY_SIZE 10000
#define NUM_RUNS 5

// Sorting algorithms implementations
void bubbleSort(int arr[], int n) {
    for (int i = 0; i < n-1; i++) {
        for (int j = 0; j < n-i-1; j++) {
            if (arr[j] > arr[j+1]) {
                int temp = arr[j];
                arr[j] = arr[j+1];
                arr[j+1] = temp;
            }
        }
    }
}

void insertionSort(int arr[], int n) {
    int i, key, j;
    for (i = 1; i < n; i++) {
        key = arr[i];
        j = i - 1;
        
        while (j >= 0 && arr[j] > key) {
            arr[j + 1] = arr[j];
            j = j - 1;
        }
        arr[j + 1] = key;
    }
}

// Quick sort implementation
void swap(int* a, int* b) {
    int t = *a;
    *a = *b;
    *b = t;
}

int partition(int arr[], int low, int high) {
    int pivot = arr[high];
    int i = (low - 1);
  
    for (int j = low; j <= high - 1; j++) {
        if (arr[j] < pivot) {
            i++;
            swap(&arr[i], &arr[j]);
        }
    }
    swap(&arr[i + 1], &arr[high]);
    return (i + 1);
}
  
void quickSortRecursive(int arr[], int low, int high) {
    if (low < high) {
        int pi = partition(arr, low, high);
        quickSortRecursive(arr, low, pi - 1);
        quickSortRecursive(arr, pi + 1, high);
    }
}

void quickSort(int arr[], int n) {
    quickSortRecursive(arr, 0, n - 1);
}

// Utility functions
void generateRandomArray(int arr[], int size) {
    for (int i = 0; i < size; i++) {
        arr[i] = rand() % 10000;
    }
}

int isSorted(int arr[], int size) {
    for (int i = 0; i < size - 1; i++) {
        if (arr[i] > arr[i+1]) {
            return 0;
        }
    }
    return 1;
}

void printArray(int arr[], int size) {
    printf("Array: ");
    for (int i = 0; i < (size < 20 ? size : 20); i++) {
        printf("%d ", arr[i]);
    }
    if (size > 20) {
        printf("... (showing first 20 elements only)");
    }
    printf("\n");
}

// Function to copy array
void copyArray(int dest[], int src[], int size) {
    memcpy(dest, src, sizeof(int) * size);
}

// Test each sorting algorithm
void testSortingAlgorithm(const char* name, void (*sortFunc)(int[], int), int original[], int size) {
    int* testArray = malloc(sizeof(int) * size);
    if (!testArray) {
        printf("Memory allocation failed\n");
        return;
    }
    
    copyArray(testArray, original, size);
    
    clock_t start = clock();
    sortFunc(testArray, size);
    clock_t end = clock();
    
    double cpu_time = ((double) (end - start)) / CLOCKS_PER_SEC;
    
    printf("%s: %f seconds, Sorted correctly: %s\n", 
           name, 
           cpu_time, 
           isSorted(testArray, size) ? "Yes" : "No");
           
    free(testArray);
}

int main() {
    // Seed random number generator
    srand(time(NULL));
    
    printf("Sorting Algorithm Performance Comparison\n");
    printf("======================================\n");
    printf("Running each algorithm %d times on arrays of size %d\n\n", NUM_RUNS, ARRAY_SIZE);
    
    for (int run = 0; run < NUM_RUNS; run++) {
        printf("Run %d:\n", run + 1);
        
        // Generate test data
        int* originalArray = malloc(sizeof(int) * ARRAY_SIZE);
        if (!originalArray) {
            printf("Memory allocation failed\n");
            return 1;
        }
        
        generateRandomArray(originalArray, ARRAY_SIZE);
        
        // Test each algorithm
        testSortingAlgorithm("Bubble Sort", bubbleSort, originalArray, ARRAY_SIZE);
        testSortingAlgorithm("Insertion Sort", insertionSort, originalArray, ARRAY_SIZE);
        testSortingAlgorithm("Quick Sort", quickSort, originalArray, ARRAY_SIZE);
        
        free(originalArray);
        printf("\n");
    }
    
    printf("Complete! This program can be profiled using Callgrind:\n");
    printf("1. Compile: gcc -g -O0 cs2.c -o sorting_algorithms\n");
    printf("2. Profile: valgrind --tool=callgrind --cache-sim=yes ./sorting_algorithms\n");
    printf("3. Analyze: callgrind_annotate callgrind.out.<pid>\n");
    
    return 0;
}