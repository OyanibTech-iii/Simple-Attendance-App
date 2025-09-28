// Teacher class extends Person
import java.util.*;

class Teacher extends Person {
    private String subject;
    private int yearsExperience;
    private double salary;
    private List<String> qualifications;
    private int substituteRequests;

    public Teacher(String name, String id, String subject, int yearsExperience, double salary) 
            throws IllegalArgumentException {
        super(name, id);
        setSubject(subject);
        setYearsExperience(yearsExperience);
        setSalary(salary);
        this.qualifications = new ArrayList<>();
        this.substituteRequests = 0;
    }

    public void setSubject(String subject) throws IllegalArgumentException {
        if (subject == null || subject.trim().isEmpty()) {
            throw new IllegalArgumentException("Subject cannot be null or empty");
        }
        this.subject = subject.trim();
    }

    public void setYearsExperience(int years) throws IllegalArgumentException {
        if (years < 0) {
            throw new IllegalArgumentException("Years of experience cannot be negative");
        }
        this.yearsExperience = years;
    }

    public void setSalary(double salary) throws IllegalArgumentException {
        if (salary < 0) {
            throw new IllegalArgumentException("Salary cannot be negative");
        }
        this.salary = salary;
    }

    public String getSubject() { return subject; }
    public int getYearsExperience() { return yearsExperience; }
    public double getSalary() { return salary; }
    public List<String> getQualifications() { return new ArrayList<>(qualifications); }
    public int getSubstituteRequests() { return substituteRequests; }

    public String getRole() { return "Teacher"; }
    public double getMinimumAttendanceRequirement() { return 85.0; }
    public void handleLowAttendance() {
        substituteRequests++;
        System.out.println("Alert: Teacher " + getName() + " has low attendance! Substitute requested " + substituteRequests + " times.");
    }

    public void addQualification(String qualification) {
        if (qualification != null && !qualification.trim().isEmpty()) {
            qualifications.add(qualification.trim());
        }
    }
}
