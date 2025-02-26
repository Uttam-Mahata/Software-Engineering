CC = gcc
CFLAGS = -g -Wall -pthread
TARGETS = basic breakpoints variables functions threads watchpoint core_debug attach_debug

all: $(TARGETS)

basic: basic.c
	$(CC) $(CFLAGS) -o $@ $<

breakpoints: breakpoints.c
	$(CC) $(CFLAGS) -o $@ $<

variables: variables.c
	$(CC) $(CFLAGS) -o $@ $<

functions: functions.c
	$(CC) $(CFLAGS) -o $@ $<

threads: threads.c
	$(CC) $(CFLAGS) -o $@ $<

watchpoint: watchpoint.c
	$(CC) $(CFLAGS) -o $@ $<

core_debug: core_debug.c
	$(CC) $(CFLAGS) -o $@ $<

attach_debug: attach_debug.c
	$(CC) $(CFLAGS) -o $@ $<

clean:
	rm -f $(TARGETS) *.o core 