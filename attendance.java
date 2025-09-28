// ============================================================================
// ABSTRACTION: Abstract base class defining contract for all persons
// ============================================================================
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

abstract class Person {
    // ENCAPSULATION: Private fields with controlled access
    private String name;
    private String id;
    private Map<LocalDate, Boolean> attendanceRecord;
    private static int totalPersons = 0;
    
    // ENCAPSULATION: Protected constructor for inheritance only
    protected Person(String name, String id) throws IllegalArgumentException {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID cannot be null or empty");
        }
        
        this.name = name.trim();
        this.id = id.trim();
        this.attendanceRecord = new HashMap<>();
        totalPersons++;
    }
    
    // ENCAPSULATION: Controlled access through getters
    public String getName() { return name; }
    public String getId() { return id; }
    public static int getTotalPersons() { return totalPersons; }
    
    // ABSTRACTION: Abstract methods - contract for subclasses
    public abstract String getRole();
    public abstract double getMinimumAttendanceRequirement();
    public abstract void handleLowAttendance();
    
    // POLYMORPHISM: Virtual method that can be overridden
    public void markAttendance(LocalDate date, boolean present) {
        try {
            if (date == null) {
                throw new IllegalArgumentException("Date cannot be null");
            }
            
            attendanceRecord.put(date, present);
            System.out.println("✓ " + getRole() + " " + name + " marked " + 
                             (present ? "PRESENT" : "ABSENT") + " on " + 
                             date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        } catch (Exception e) {
            System.err.println("Error marking attendance: " + e.getMessage());
        }
    }
    
    // ENCAPSULATION: Complex calculation hidden behind simple method
    public double getAttendancePercentage() {
        if (attendanceRecord.isEmpty()) return 0.0;
        
        int presentDays = 0;
        // Using enhanced for loop
        for (Boolean present : attendanceRecord.values()) {
            if (present) presentDays++;
        }
        
        return (double) presentDays / attendanceRecord.size() * 100;
    }
    
    // POLYMORPHISM: Method that can be overridden
    public void displayInfo() {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("📋 " + getRole().toUpperCase() + " PROFILE");
        System.out.println("=".repeat(40));
        System.out.println("Name: " + name);
        System.out.println("ID: " + id);
        System.out.println("Attendance: " + String.format("%.1f%%", getAttendancePercentage()));
        System.out.println("Required: " + String.format("%.1f%%", getMinimumAttendanceRequirement()));
        
        if (getAttendancePercentage() < getMinimumAttendanceRequirement()) {
            System.out.println("⚠️  Status: BELOW REQUIREMENT");
            handleLowAttendance();
        } else {
            System.out.println("✅ Status: MEETS REQUIREMENT");
        }
    }
    
    public Map<LocalDate, Boolean> getAttendanceRecord() {
        // ENCAPSULATION: Return defensive copy
        return new HashMap<>(attendanceRecord);
    }
}

// ============================================================================
// INHERITANCE: Student class extends Person
// ============================================================================
class Student extends Person {
    // ENCAPSULATION: Private fields
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
    
    // ENCAPSULATION: Controlled access with validation
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
    
    // ENCAPSULATION: Getters
    public double getGpa() { return gpa; }
    public String getGrade() { return grade; }
    public List<String> getSubjects() { return new ArrayList<>(subjects); }
    public int getWarningCount() { return warningCount; }
    
    public void addSubject(String subject) {
        if (subject != null && !subject.trim().isEmpty()) {
            subjects.add(subject.trim());
        }
    }
    
    // ABSTRACTION: Implementation of abstract methods
    @Override
    public String getRole() { return "Student"; }
    
    @Override
    public double getMinimumAttendanceRequirement() { return 75.0; }
    
    @Override
    public void handleLowAttendance() {
        warningCount++;
        System.out.println("🚨 STUDENT WARNING #" + warningCount);
        System.out.println("   - Parents will be contacted");
        System.out.println("   - Academic probation risk");
        
        if (warningCount >= 3) {
            System.out.println("   - CRITICAL: Consider academic counseling");
        }
    }
    
    // POLYMORPHISM: Overriding parent method with additional behavior
    @Override
    public void markAttendance(LocalDate date, boolean present) {
        super.markAttendance(date, present);
        
        if (!present) {
            System.out.println("📧 Automated parent notification sent for " + getName());
        }
    }
    
    // POLYMORPHISM: Enhanced display method
    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Grade Level: " + grade);
        System.out.println("GPA: " + String.format("%.2f", gpa));
        System.out.println("Enrolled Subjects: " + subjects.size());
        
        // Using traditional for loop
        for (int i = 0; i < subjects.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + subjects.get(i));
        }
        
        if (warningCount > 0) {
            System.out.println("⚠️  Total Warnings: " + warningCount);
        }
    }
}

// ============================================================================
// INHERITANCE: Teacher class extends Person  
// ============================================================================
class Teacher extends Person {
    // ENCAPSULATION: Private fields
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
    
    // ENCAPSULATION: Controlled setters with validation
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
    
    // ENCAPSULATION: Getters
    public String getSubject() { return subject; }
    public int getYearsExperience() { return yearsExperience; }
    public int getSubstituteRequests() { return substituteRequests; }
    
    public void addQualification(String qualification) {
        if (qualification != null && !qualification.trim().isEmpty()) {
            qualifications.add(qualification.trim());
        }
    }
    
    // ENCAPSULATION: Sensitive salary data protected
    public String getSalaryCategory() {
        if (salary < 40000) return "Entry Level";
        else if (salary < 70000) return "Mid Level";
        else if (salary < 100000) return "Senior Level";
        else return "Executive Level";
    }
    
    // ABSTRACTION: Implementation of abstract methods
    @Override
    public String getRole() { return "Teacher"; }
    
    @Override
    public double getMinimumAttendanceRequirement() { return 90.0; }
    
    @Override
    public void handleLowAttendance() {
        substituteRequests++;
        System.out.println("🏫 TEACHER ABSENCE PROTOCOL #" + substituteRequests);
        System.out.println("   - Substitute teacher arrangement needed");
        System.out.println("   - Department head notification sent");
        System.out.println("   - Student impact assessment required");
        
        if (substituteRequests >= 5) {
            System.out.println("   - HR review recommended");
        }
    }
    
    // POLYMORPHISM: Overriding parent method
    @Override
    public void markAttendance(LocalDate date, boolean present) {
        super.markAttendance(date, present);
        
        if (!present) {
            System.out.println("📋 Substitute arrangement initiated for " + subject + " class");
        }
    }
    
    // POLYMORPHISM: Enhanced display method
    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Subject: " + subject);
        System.out.println("Experience: " + yearsExperience + " years");
        System.out.println("Salary Category: " + getSalaryCategory());
        System.out.println("Qualifications: " + qualifications.size());
        
        // Using enhanced for loop
        for (String qual : qualifications) {
            System.out.println("  • " + qual);
        }
        
        if (substituteRequests > 0) {
            System.out.println("📋 Substitute Requests: " + substituteRequests);
        }
    }
}

// ============================================================================
// ENCAPSULATION: AttendanceManager handles all attendance operations
// ============================================================================
class AttendanceManager {
    // ENCAPSULATION: Private data and methods
    private Map<String, Person> personRegistry;
    private List<LocalDate> schoolDays;
    private String institutionName;
    
    public AttendanceManager(String institutionName) {
        this.institutionName = institutionName;
        this.personRegistry = new HashMap<>();
        this.schoolDays = new ArrayList<>();
        initializeSchoolDays();
    }
    
    // ENCAPSULATION: Private helper method
    private void initializeSchoolDays() {
        LocalDate startDate = LocalDate.now().minusDays(10);
        
        // Using while loop to generate school days
        int daysAdded = 0;
        LocalDate current = startDate;
        while (daysAdded < 10) {
            // Skip weekends
            if (current.getDayOfWeek().getValue() <= 5) {
                schoolDays.add(current);
                daysAdded++;
            }
            current = current.plusDays(1);
        }
    }
    
    public void registerPerson(Person person) {
        try {
            if (person == null) {
                throw new IllegalArgumentException("Person cannot be null");
            }
            
            if (personRegistry.containsKey(person.getId())) {
                throw new IllegalArgumentException("Person with ID " + person.getId() + " already exists");
            }
            
            personRegistry.put(person.getId(), person);
            System.out.println("✅ " + person.getRole() + " " + person.getName() + 
                             " registered successfully (ID: " + person.getId() + ")");
            
        } catch (Exception e) {
            System.err.println("❌ Registration failed: " + e.getMessage());
        }
    }
    
    public Person findPerson(String id) {
        return personRegistry.get(id);
    }
    
    // POLYMORPHISM: Works with any Person subclass
    public void markAttendanceForPerson(String id, LocalDate date, boolean present) {
        try {
            Person person = findPerson(id);
            if (person == null) {
                throw new IllegalArgumentException("No person found with ID: " + id);
            }
            
            person.markAttendance(date, present);
            
        } catch (Exception e) {
            System.err.println("❌ Attendance marking failed: " + e.getMessage());
        }
    }
    
    public void bulkMarkAttendance(List<String> ids, LocalDate date, boolean present) {
        System.out.println("\n🔄 Bulk attendance marking for " + 
                         date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + "...");
        
        int successful = 0;
        int failed = 0;
        
        // Using enhanced for loop with exception handling
        for (String id : ids) {
            try {
                markAttendanceForPerson(id, date, present);
                successful++;
            } catch (Exception e) {
                System.err.println("Failed for ID " + id + ": " + e.getMessage());
                failed++;
            }
        }
        
        System.out.println("📊 Bulk operation complete: " + successful + " successful, " + failed + " failed");
    }
    
    public void generateComprehensiveReport() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("📈 COMPREHENSIVE ATTENDANCE REPORT");
        System.out.println("Institution: " + institutionName);
        System.out.println("Report Date: " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        System.out.println("=".repeat(60));
        
        if (personRegistry.isEmpty()) {
            System.out.println("No persons registered in the system.");
            return;
        }
        
        // POLYMORPHISM: displayInfo() calls appropriate method based on actual object type
        for (Person person : personRegistry.values()) {
            try {
                person.displayInfo();
            } catch (Exception e) {
                System.err.println("Error displaying info for " + person.getName() + ": " + e.getMessage());
            }
        }
        
        generateStatistics();
    }
    
    // ENCAPSULATION: Private method for internal calculations
    private void generateStatistics() {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("📊 SYSTEM STATISTICS");
        System.out.println("=".repeat(40));
        
        int studentCount = 0;
        int teacherCount = 0;
        double totalAttendance = 0;
        int belowRequirement = 0;
        
        // Using traditional for loop with collection iteration
        List<Person> allPersons = new ArrayList<>(personRegistry.values());
        for (int i = 0; i < allPersons.size(); i++) {
            Person person = allPersons.get(i);
            
            if (person instanceof Student) {
                studentCount++;
            } else if (person instanceof Teacher) {
                teacherCount++;
            }
            
            double attendance = person.getAttendancePercentage();
            totalAttendance += attendance;
            
            if (attendance < person.getMinimumAttendanceRequirement()) {
                belowRequirement++;
            }
        }
        
        System.out.println("Total Registered: " + personRegistry.size());
        System.out.println("Students: " + studentCount);
        System.out.println("Teachers: " + teacherCount);
        System.out.println("Average Attendance: " + String.format("%.1f%%", 
                          personRegistry.isEmpty() ? 0 : totalAttendance / personRegistry.size()));
        System.out.println("Below Requirements: " + belowRequirement);
        System.out.println("School Days Tracked: " + schoolDays.size());
    }
    
    public void identifyAttendanceIssues(double threshold) {
        System.out.println("\n🚨 ATTENDANCE ISSUES (Below " + threshold + "%):");
        System.out.println("-".repeat(50));
        
        boolean issuesFound = false;
        
        for (Person person : personRegistry.values()) {
            try {
                if (person.getAttendancePercentage() < threshold) {
                    System.out.println("⚠️  " + person.getRole() + ": " + person.getName() + 
                                     " (" + person.getId() + ") - " + 
                                     String.format("%.1f%%", person.getAttendancePercentage()));
                    issuesFound = true;
                }
            } catch (Exception e) {
                System.err.println("Error checking " + person.getName() + ": " + e.getMessage());
            }
        }
        
        if (!issuesFound) {
            System.out.println("✅ No attendance issues found!");
        }
    }
    
    public List<LocalDate> getSchoolDays() {
        return new ArrayList<>(schoolDays);
    }
}

