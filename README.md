# Student Management System (Java)

A beginner-friendly, menu-driven Java console application for managing student records. It demonstrates object-oriented programming, collections, input validation, and file handling.

## Features

- Add a student with a unique ID
- View all student records
- Search by student ID
- Update student details
- Delete a student record with confirmation
- Save records in `students.csv` so they remain available after restarting the program

## Requirements

- JDK 17 or later
- Terminal / Command Prompt, or an IDE such as IntelliJ IDEA, Eclipse, or VS Code with Java support

## Run the project

From the project folder:

```bash
javac -d out src/Student.java src/StudentManagementSystem.java
java -cp out StudentManagementSystem
```

The program creates `students.csv` in the current working directory when records are saved.

## Example workflow

1. Choose `1` to add a student.
2. Enter a unique ID, name, department, and email.
3. Choose `2` to view records.
4. Use options `3`, `4`, and `5` to search, update, or delete.
5. Choose `0` to save and exit.

## Concepts used

- Classes and objects
- Encapsulation
- `ArrayList`
- Loops and switch expressions
- Exception handling
- File I/O using `java.nio.file`

## Future improvements

- Add a graphical user interface
- Validate email format
- Use a database such as MySQL or SQLite
- Add sorting and department-wise filtering

## Author

Navaneetha M
