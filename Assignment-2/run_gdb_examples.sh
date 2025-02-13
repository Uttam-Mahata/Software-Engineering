#!/bin/bash

# Function to run GDB commands and save output
run_gdb_session() {
    local program=$1
    local commands=$2
    local log_file="logs/${program}_gdb_session.log"
    
    echo "Running GDB session for ${program}..."
    echo "GDB Session Log for ${program}" > "$log_file"
    echo "================================" >> "$log_file"
    echo "Date: $(date)" >> "$log_file"
    echo "================================" >> "$log_file"
    
    # Run GDB with commands and save output
    gdb -q "./${program}" > >(tee -a "$log_file") 2>&1 << EOF
$commands
quit
EOF
    
    echo "GDB session log saved to $log_file"
    echo
}

# Make sure all programs are compiled
make clean
make all

# Create logs directory if it doesn't exist
mkdir -p logs

# Basic program
run_gdb_session "basic" "
break main
run
next
step
print x
print y
continue
"

# Breakpoints program
run_gdb_session "breakpoints" "
break main
run
break process_number
continue
info breakpoints
disable 2
enable 2
ignore 1 3
continue
"

# Variables program
run_gdb_session "variables" "
break main
run
next
print x
print pi
print letter
print numbers
print *numbers@5
print string
x/d &x
x/5d numbers
x/s string
continue
"

# Functions program
run_gdb_session "functions" "
break main
run
break factorial
continue
backtrace
frame 0
info args
info locals
continue
"

# Threads program
run_gdb_session "threads" "
break main
run
break worker_function
continue
info threads
thread 2
thread apply all bt
set scheduler-locking on
next
set scheduler-locking off
continue
"

# Watchpoint program
run_gdb_session "watchpoint" "
break main
run
next
watch number
continue
watch point.x
continue
info watchpoints
delete 1
continue
"

# Core debug program (with core dump generation)
ulimit -c unlimited
run_gdb_session "core_debug" "
break main
run 1
where
info registers
x/i \$pc
continue
"

# Attach debug program (demonstration only, as attaching requires running process)
run_gdb_session "attach_debug" "
break main
run
break process_data
continue
info locals
continue
"

echo "All GDB sessions completed. Logs are saved in the logs directory." 