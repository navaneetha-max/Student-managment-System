public class Student {
    private final int id;
    private String name;
    private String department;
    private String email;

    public Student(int id, String name, String department, String email) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.email = email;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getDepartment() { return department; }
    public String getEmail() { return email; }

    public void setName(String name) { this.name = name; }
    public void setDepartment(String department) { this.department = department; }
    public void setEmail(String email) { this.email = email; }

    public String toCsv() {
        return id + "," + clean(name) + "," + clean(department) + "," + clean(email);
    }

    private String clean(String value) {
        return value.replace(",", " ").replace("\n", " ").trim();
    }

    @Override
    public String toString() {
        return String.format("%-6d %-22s %-18s %s", id, name, department, email);
    }
}
