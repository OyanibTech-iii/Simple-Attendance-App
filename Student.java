// Student class extends Person
import java.util.*;

class Student extends Person {
    private String grade;
    private double gpa;
    private List<String> subjects;
    private int warningCount;

    public Student(String name, String id, String grade, double gpa) throws IllegalArgumentException {
        super(name, id);
        setGrade(grade);
        setGpa(gpa);
        this.subjects = new ArrayList<>();
        this.warningCount = 0;
    }

    public void setGpa(double gpa) throws IllegalArgumentException {
        if (gpa < 0.0 || gpa > 4.0) {
            throw new IllegalArgumentException("GPA must be between 0.0 and 4.0, got: " + gpa);
        }
        this.gpa = gpa;
    }

    public void setGrade(String grade) throws IllegalArgumentException {
        if (grade == null || grade.trim().isEmpty()) {
            throw new IllegalArgumentException("Grade cannot be null or empty");
        }
        this.grade = grade.trim();
    }

    public double getGpa() { return gpa; }
    public String getGrade() { return grade; }
    public List<String> getSubjects() { return new ArrayList<>(subjects); }
    public int getWarningCount() { return warningCount; }

    public String getRole() { return "Student"; }
    public double getMinimumAttendanceRequirement() { return 75.0; }
    public void handleLowAttendance() {
        warningCount++;
        System.out.println("Warning: Student " + getName() + " has low attendance! Total warnings: " + warningCount);
    }

    public void addSubject(String subject) {
        if (subject != null && !subject.trim().isEmpty()) {
            subjects.add(subject.trim());
        }
    }
}
