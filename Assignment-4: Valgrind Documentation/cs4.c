#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>

int shared_variable = 0;
// Missing mutex for protecting shared_variable

void *increment_thread(void *arg) {
    for (int i = 0; i < 10000; i++) {
        shared_variable++; // Data race here
    }
    return NULL;
}

int main() {
    pthread_t thread1, thread2;
    pthread_create(&thread1, NULL, increment_thread, NULL);
    pthread_create(&thread2, NULL, increment_thread, NULL);
    
    pthread_join(thread1, NULL);
    pthread_join(thread2, NULL);
    
    printf("Final value: %d\n", shared_variable);
    return 0;
}