// Main demonstration class
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class AttendanceSystemDemo {
    private static AttendanceManager system;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            system = new AttendanceManager("Advanced Java Academy");
            System.out.println("Welcome to Advanced Attendance Management System");
            System.out.println("Demonstrating all 4 OOP pillars with enhanced features\n");
            setupDemoData();
            simulateAttendanceTracking();
            runInteractiveMenu();
        } catch (Exception e) {
            System.err.println("System error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
    }

    private static void setupDemoData() {
        System.out.println("Setting up demonstration data...\n");
        try {
            Student student1 = new Student("Alice Johnson", "S001", "Grade 10", 3.8);
            student1.addSubject("Mathematics");
            student1.addSubject("Physics");
            student1.addSubject("Chemistry");
            system.registerPerson(student1);
            Student student2 = new Student("Bob Smith", "S002", "Grade 11", 3.2);
            student2.addSubject("English");
            student2.addSubject("History");
            student2.addSubject("Biology");
            system.registerPerson(student2);
            Student student3 = new Student("Carol Davis", "S003", "Grade 9", 3.9);
            student3.addSubject("Art");
            student3.addSubject("Music");
            system.registerPerson(student3);
            Teacher teacher1 = new Teacher("Dr. Sarah Wilson", "T001", "Mathematics", 8, 75000);
            teacher1.addQualification("PhD in Mathematics");
            teacher1.addQualification("Teaching Certification");
            system.registerPerson(teacher1);
            Teacher teacher2 = new Teacher("Mr. John Davis", "T002", "Physics", 5, 58000);
            teacher2.addQualification("MSc Physics");
            teacher2.addQualification("Science Education Diploma");
            system.registerPerson(teacher2);
            Teacher teacher3 = new Teacher("Ms. Emily Brown", "T003", "English", 12, 82000);
            teacher3.addQualification("MA English Literature");
            teacher3.addQualification("TESOL Certification");
            system.registerPerson(teacher3);
        } catch (Exception e) {
            System.err.println("Error in demo setup: " + e.getMessage());
        }
        System.out.println("Demo data setup complete!\n");
    }

    private static void simulateAttendanceTracking() {
        System.out.println("Simulating attendance tracking...\n");
        List<String> allIds = Arrays.asList("S001", "S002", "S003", "T001", "T002", "T003");
        List<LocalDate> schoolDays = system.getSchoolDays();
        for (int dayIndex = 0; dayIndex < schoolDays.size(); dayIndex++) {
            LocalDate currentDay = schoolDays.get(dayIndex);
            System.out.println("Marking attendance for " + currentDay.format(DateTimeFormatter.ofPattern("EEEE, dd-MM-yyyy")));
            for (int personIndex = 0; personIndex < allIds.size(); personIndex++) {
                try {
                    String id = allIds.get(personIndex);
                    boolean present = Math.random() > 0.2;
                    if (id.equals("S002") && dayIndex > 6) present = false;
                    if (id.equals("T002") && dayIndex == 3) present = false;
                    system.markAttendanceForPerson(id, currentDay, present);
                } catch (Exception e) {
                    System.err.println("Error marking attendance: " + e.getMessage());
                }
            }
            System.out.println();
        }
        System.out.println("Attendance simulation complete!\n");
    }

    private static void runInteractiveMenu() {
        boolean running = true;
        while (running) {
            try {
                displayMenu();
                int choice = getMenuChoice();
                switch (choice) {
                    case 1:
                        system.generateComprehensiveReport();
                        break;
                    case 2:
                        system.identifyAttendanceIssues(80.0);
                        break;
                    case 3:
                        demonstratePolymorphism();
                        break;
                    case 4:
                        demonstrateErrorHandling();
                        break;
                    case 5:
                        showOOPPrinciples();
                        break;
                    case 0:
                        running = false;
                        System.out.println("Thank you for using the Attendance System!");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
                if (running) {
                    System.out.println("\nPress Enter to continue...");
                    scanner.nextLine();
                }
            } catch (Exception e) {
                System.err.println("Menu error: " + e.getMessage());
                scanner.nextLine();
            }
        }
    }

    private static void displayMenu() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("ATTENDANCE SYSTEM MENU");
        System.out.println("=".repeat(50));
        System.out.println("1. Generate Comprehensive Report");
        System.out.println("2. Identify Attendance Issues");
        System.out.println("3. Demonstrate Polymorphism");
        System.out.println("4. Demonstrate Error Handling");
        System.out.println("5. Show OOP Principles Summary");
        System.out.println("0. Exit");
        System.out.print("\nEnter your choice: ");
    }

    private static int getMenuChoice() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Please enter a valid number");
        }
    }

    private static void demonstratePolymorphism() {
        System.out.println("\nPOLYMORPHISM DEMONSTRATION");
        System.out.println("=".repeat(40));
        System.out.println("Same method calls, different behaviors:\n");
        Person[] people = {
            system.findPerson("S001"),
            system.findPerson("T001")
        };
        for (Person person : people) {
            if (person != null) {
                System.out.println("Calling handleLowAttendance() for " + person.getRole() + ":");
                person.handleLowAttendance();
                System.out.println();
            }
        }
    }

    private static void demonstrateErrorHandling() {
        System.out.println("\nERROR HANDLING DEMONSTRATION");
        System.out.println("=".repeat(40));
        try {
            System.out.println("1. Attempting to create student with invalid GPA:");
            Student invalidStudent = new Student("Test Student", "TEST", "Grade 12", 5.0);
        } catch (IllegalArgumentException e) {
            System.out.println("Caught exception: " + e.getMessage());
        }
        try {
            System.out.println("\n2. Attempting to mark attendance with null date:");
            Person person = system.findPerson("S001");
            if (person != null) {
                person.markAttendance(null, true);
            }
        } catch (Exception e) {
            System.out.println("Caught exception: " + e.getMessage());
        }
        try {
            System.out.println("\n3. Attempting to find non-existent person:");
            system.markAttendanceForPerson("INVALID_ID", LocalDate.now(), true);
        } catch (Exception e) {
            System.out.println("Error handling worked properly");
        }
    }

    private static void showOOPPrinciples() {
        System.out.println("\nOOP PRINCIPLES DEMONSTRATION SUMMARY");
        System.out.println("=".repeat(60));
        System.out.println("ENCAPSULATION:");
        System.out.println("   • Private fields with controlled access via getters/setters");
        System.out.println("   • Data validation in setter methods");
        System.out.println("   • Hidden implementation details in AttendanceManager");
        System.out.println("   • Defensive copying for sensitive data");
        System.out.println("\nINHERITANCE:");
        System.out.println("   • Student and Teacher inherit from abstract Person class");
        System.out.println("   • Shared properties and methods inherited");
        System.out.println("   • Constructor chaining with super()");
        System.out.println("   • Code reuse and hierarchical relationships");
        System.out.println("\nPOLYMORPHISM:");
        System.out.println("   • Method overriding: markAttendance(), displayInfo()");
        System.out.println("   • Runtime method resolution based on object type");
        System.out.println("   • Same interface, different behaviors");
        System.out.println("   • Dynamic binding in action");
        System.out.println("\nABSTRACTION:");
        System.out.println("   • Abstract Person class defines common contract");
        System.out.println("   • Abstract methods must be implemented by subclasses");
        System.out.println("   • Complex operations hidden behind simple interfaces");
        System.out.println("   • Implementation details abstracted away");
        System.out.println("\nADDITIONAL FEATURES:");
        System.out.println("   • Comprehensive error handling with try-catch blocks");
        System.out.println("   • Multiple types of loops (for, enhanced-for, while)");
        System.out.println("   • Collections and data structures");
        System.out.println("   • Input validation and defensive programming");
        System.out.println("\nTotal registered persons: " + Person.getTotalPersons());
    }
}
