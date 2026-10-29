import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StudentManagementSystem {
    private static final Path DATA_FILE = Path.of("students.csv");
    private static final List<Student> students = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        loadStudents();

        System.out.println("==================================");
        System.out.println("     STUDENT MANAGEMENT SYSTEM");
        System.out.println("==================================");

        boolean running = true;
        while (running) {
            showMenu();
            String choice = prompt("Choose an option: ");

            switch (choice) {
                case "1" -> addStudent();
                case "2" -> viewStudents();
                case "3" -> searchStudent();
                case "4" -> updateStudent();
                case "5" -> deleteStudent();
                case "0" -> {
                    saveStudents();
                    System.out.println("Data saved. Goodbye!");
                    running = false;
                }
                default -> System.out.println("Invalid option. Please choose 0-5.");
            }
        }
        scanner.close();
    }

    private static void showMenu() {
        System.out.println("\\n----------- MENU -----------");
        System.out.println("1. Add student");
        System.out.println("2. View all students");
        System.out.println("3. Search student by ID");
        System.out.println("4. Update student");
        System.out.println("5. Delete student");
        System.out.println("0. Save and exit");
    }

    private static void addStudent() {
        int id = readPositiveInt("Student ID: ");
        if (findById(id) != null) {
            System.out.println("That ID already exists.");
            return;
        }

        String name = prompt("Name: ");
        String department = prompt("Department: ");
        String email = prompt("Email: ");

        if (name.isBlank() || department.isBlank() || email.isBlank()) {
            System.out.println("Fields cannot be empty. Student was not added.");
            return;
        }

        students.add(new Student(id, name, department, email));
        saveStudents();
        System.out.println("Student added successfully.");
    }

    private static void viewStudents() {
        if (students.isEmpty()) {
            System.out.println("No student records found.");
            return;
        }

        printHeader();
        for (Student student : students) {
            System.out.println(student);
        }
    }

    private static void searchStudent() {
        int id = readPositiveInt("Enter student ID to search: ");
        Student student = findById(id);
        if (student == null) {
            System.out.println("Student not found.");
        } else {
            printHeader();
            System.out.println(student);
        }
    }

    private static void updateStudent() {
        int id = readPositiveInt("Enter student ID to update: ");
        Student student = findById(id);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.println("Press Enter to keep the current value.");
        String name = prompt("Name [" + student.getName() + "]: ");
        String department = prompt("Department [" + student.getDepartment() + "]: ");
        String email = prompt("Email [" + student.getEmail() + "]: ");

        if (!name.isBlank()) student.setName(name);
        if (!department.isBlank()) student.setDepartment(department);
        if (!email.isBlank()) student.setEmail(email);

        saveStudents();
        System.out.println("Student updated successfully.");
    }

    private static void deleteStudent() {
        int id = readPositiveInt("Enter student ID to delete: ");
        Student student = findById(id);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        String confirmation = prompt("Delete " + student.getName() + "? (y/n): ");
        if (confirmation.equalsIgnoreCase("y")) {
            students.remove(student);
            saveStudents();
            System.out.println("Student deleted.");
        } else {
            System.out.println("Delete cancelled.");
        }
    }

    private static Student findById(int id) {
        for (Student student : students) {
            if (student.getId() == id) return student;
        }
        return null;
    }

    private static int readPositiveInt(String message) {
        while (true) {
            String value = prompt(message);
            try {
                int number = Integer.parseInt(value);
                if (number > 0) return number;
            } catch (NumberFormatException ignored) {
                // Show the message below and ask again.
            }
            System.out.println("Please enter a positive whole number.");
        }
    }

    private static String prompt(String message) {
        System.out.print(message);
        return scanner.nextLine().trim();
    }

    private static void printHeader() {
        System.out.printf("%-6s %-22s %-18s %s%n", "ID", "NAME", "DEPARTMENT", "EMAIL");
        System.out.println("---------------------------------------------------------------------");
    }

    private static void loadStudents() {
        if (!Files.exists(DATA_FILE)) return;

        try {
            for (String line : Files.readAllLines(DATA_FILE)) {
                if (line.isBlank()) continue;
                String[] parts = line.split(",", 4);
                if (parts.length == 4) {
                    try {
                        students.add(new Student(
                            Integer.parseInt(parts[0]), parts[1], parts[2], parts[3]
                        ));
                    } catch (NumberFormatException ignored) {
                        System.out.println("Skipping an invalid record in students.csv.");
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Could not load saved data: " + e.getMessage());
        }
    }

    private static void saveStudents() {
        List<String> lines = new ArrayList<>();
        for (Student student : students) lines.add(student.toCsv());

        try {
            Files.write(DATA_FILE, lines,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING,
                StandardOpenOption.WRITE);
        } catch (IOException e) {
            System.out.println("Could not save data: " + e.getMessage());
        }
    }
}
