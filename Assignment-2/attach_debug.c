/*
 * attach_debug.c - Demonstrates attaching GDB to a running process
 *
 * GDB Commands demonstrated:
 * 1. Attach to process: gdb -p PID
 * 2. Detach from process: detach
 * 3. Continue running: continue
 * 4. Kill process: kill
 */

#include <stdio.h>
#include <unistd.h>
#include <signal.h>
#include <stdlib.h>
#include <time.h>

volatile sig_atomic_t keep_running = 1;

void signal_handler(int signum) {
    if (signum == SIGINT) {
        printf("\nReceived SIGINT. Use SIGTERM to stop the program.\n");
    } else if (signum == SIGTERM) {
        printf("\nReceived SIGTERM. Stopping...\n");
        keep_running = 0;
    }
}

void process_data(int iteration) {
    int data[5] = {0};
    
    // Fill array with some values
    for (int i = 0; i < 5; i++) {
        data[i] = rand() % 100;
    }
    
    // Process the data
    int sum = 0;
    for (int i = 0; i < 5; i++) {
        sum += data[i];
    }
    
    printf("Iteration %d: Sum = %d\n", iteration, sum);
}

int main() {
    // Set up signal handlers
    signal(SIGINT, signal_handler);
    signal(SIGTERM, signal_handler);
    
    // Initialize random seed
    srand(time(NULL));
    
    // Print process ID for easy attachment
    pid_t pid = getpid();
    printf("Process started. PID: %d\n", pid);
    printf("Use the following command to attach GDB:\n");
    printf("    gdb -p %d\n\n", pid);
    
    int iteration = 0;
    
    while (keep_running) {
        printf("\nProcessing iteration %d...\n", iteration);
        process_data(iteration);
        
        // Sleep to allow time for attachment
        printf("Sleeping for 2 seconds...\n");
        sleep(2);
        
        iteration++;
    }
    
    printf("Process terminating normally.\n");
    return 0;
}

/*
Example GDB attach debugging session:

1. Compile and run the program:
$ gcc -g attach_debug.c -o attach_debug
$ ./attach_debug &
[1] 12345    # Note the PID

2. Attach GDB to the running process:
$ gdb -p 12345

3. Debug the running process:
(gdb) break process_data
(gdb) continue
(gdb) print iteration
(gdb) print data
(gdb) continue

4. Detach from the process:
(gdb) detach
(gdb) quit

5. Reattach if needed:
$ gdb -p 12345

6. Kill the process from GDB:
(gdb) kill
(gdb) quit

Alternative ways to attach:
1. Start GDB first:
$ gdb
(gdb) attach 12345

2. Load symbols then attach:
$ gdb ./attach_debug
(gdb) attach 12345

Note: You may need appropriate permissions to attach to a process:
- Run as root
- Set /proc/sys/kernel/yama/ptrace_scope to 0
- Use sudo gdb
*/ 