/*
 * threads.c - Demonstrates debugging multi-threaded programs
 *
 * GDB Commands demonstrated:
 * 1. Show threads: info threads
 * 2. Switch threads: thread N
 * 3. Set thread-specific breakpoints: break file:line thread N
 * 4. Apply command to all threads: thread apply all command
 * 5. Show thread backtrace: thread apply all bt
 */

#include <stdio.h>
#include <pthread.h>
#include <unistd.h>

#define NUM_THREADS 3
#define ITERATIONS 5

// Shared resource
int shared_counter = 0;
pthread_mutex_t mutex = PTHREAD_MUTEX_INITIALIZER;

void* worker_function(void* arg) {
    int thread_id = *(int*)arg;
    
    for (int i = 0; i < ITERATIONS; i++) {
        // Lock the mutex
        pthread_mutex_lock(&mutex);
        
        // Critical section
        shared_counter++;
        printf("Thread %d: counter = %d\n", thread_id, shared_counter);
        
        // Simulate some work
        usleep(100000);  // 100ms
        
        // Unlock the mutex
        pthread_mutex_unlock(&mutex);
        
        // Non-critical section
        printf("Thread %d: iteration %d completed\n", thread_id, i);
        usleep(50000);  // 50ms
    }
    
    printf("Thread %d: finished\n", thread_id);
    return NULL;
}

int main() {
    pthread_t threads[NUM_THREADS];
    int thread_ids[NUM_THREADS];
    
    printf("Starting multi-threaded debugging demonstration\n");
    
    // Create threads
    for (int i = 0; i < NUM_THREADS; i++) {
        thread_ids[i] = i + 1;
        pthread_create(&threads[i], NULL, worker_function, &thread_ids[i]);
        printf("Created thread %d\n", i + 1);
    }
    
    // Wait for all threads to complete
    for (int i = 0; i < NUM_THREADS; i++) {
        pthread_join(threads[i], NULL);
    }
    
    printf("All threads completed. Final counter value: %d\n", shared_counter);
    pthread_mutex_destroy(&mutex);
    
    return 0;
}

/*
Example GDB session:

1. Basic thread inspection:
(gdb) break main
(gdb) run
(gdb) break worker_function
(gdb) continue
(gdb) info threads    # List all threads
(gdb) thread 2        # Switch to thread 2

2. Thread-specific breakpoints:
(gdb) break threads.c:30 thread 2
(gdb) break threads.c:30 thread 3

3. Examining all threads:
(gdb) thread apply all bt    # Backtrace for all threads
(gdb) thread apply all print shared_counter

4. Following thread execution:
(gdb) set scheduler-locking on    # Prevent other threads from running
(gdb) next
(gdb) set scheduler-locking off   # Allow other threads to run

5. Thread synchronization debugging:
(gdb) break pthread_mutex_lock
(gdb) break pthread_mutex_unlock
(gdb) continue

6. Watching shared variables:
(gdb) watch shared_counter
(gdb) continue

7. Thread-specific information:
(gdb) info threads
(gdb) thread 2
(gdb) bt             # Backtrace for current thread
(gdb) info locals    # Local variables in current thread
*/ 