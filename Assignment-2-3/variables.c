/*
 * variables.c - Demonstrates examining variables and memory in GDB
 *
 * GDB Commands demonstrated:
 * 1. Print variables: print var_name
 * 2. Print arrays: print array or print *array@length
 * 3. Examine memory: x/nfu <address>
 * 4. Print structures: print struct_var
 * 5. Watch variables: watch var_name
 */

#include <stdio.h>
#include <string.h>

struct Person {
    char name[50];
    int age;
    float height;
};

void modify_array(int arr[], int size) {
    for (int i = 0; i < size; i++) {
        arr[i] *= 2;
    }
}

void initialize_person(struct Person *person, const char *name, int age, float height) {
    strncpy(person->name, name, 49);
    person->name[49] = '\0';
    person->age = age;
    person->height = height;
}

int main() {
    // Simple variables
    int x = 42;
    float pi = 3.14159;
    char letter = 'A';

    // Array demonstration
    int numbers[5] = {1, 2, 3, 4, 5};
    char string[] = "Hello, GDB!";

    // Pointer demonstration
    int *ptr = &x;

    // Structure demonstration
    struct Person person;
    initialize_person(&person, "John Doe", 30, 1.75);

    printf("Initial values:\n");
    printf("x = %d, pi = %f, letter = %c\n", x, pi, letter);
    printf("First number = %d\n", numbers[0]);
    printf("String = %s\n", string);
    printf("Person: %s, age: %d, height: %.2f\n", 
           person.name, person.age, person.height);

    // Modify some values
    modify_array(numbers, 5);
    *ptr = 100;

    printf("\nModified values:\n");
    printf("x = %d\n", x);
    printf("First number = %d\n", numbers[0]);

    return 0;
}

/*
Example GDB session:

1. Examine simple variables:
(gdb) print x
(gdb) print pi
(gdb) print letter

2. Examine arrays:
(gdb) print numbers
(gdb) print *numbers@5
(gdb) print string

3. Examine memory:
(gdb) x/d &x
(gdb) x/5d numbers
(gdb) x/s string

4. Examine pointers:
(gdb) print ptr
(gdb) print *ptr

5. Examine structure:
(gdb) print person
(gdb) print person.name
(gdb) print person.age

6. Watch variable:
(gdb) watch x
(gdb) continue

7. Memory format examples:
(gdb) x/x &x      # hex
(gdb) x/d &x      # decimal
(gdb) x/c string  # characters
(gdb) x/s string  # string
*/ 