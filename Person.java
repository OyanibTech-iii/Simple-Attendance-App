// Abstract base class defining contract for all persons
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

abstract class Person {
    private String name;
    private String id;
    private Map<LocalDate, Boolean> attendanceRecord;
    private static int totalPersons = 0;

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

    public String getName() { return name; }
    public String getId() { return id; }
    public static int getTotalPersons() { return totalPersons; }

    public abstract String getRole();
    public abstract double getMinimumAttendanceRequirement();
    public abstract void handleLowAttendance();

    public void markAttendance(LocalDate date, boolean present) {
        try {
            if (date == null) {
                throw new IllegalArgumentException("Date cannot be null");
            }
            attendanceRecord.put(date, present);
            System.out.println(getRole() + " " + name + " marked " + 
                             (present ? "PRESENT" : "ABSENT") + " on " + 
                             date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        } catch (Exception e) {
            System.err.println("Error marking attendance: " + e.getMessage());
        }
    }

    public double getAttendancePercentage() {
        if (attendanceRecord.isEmpty()) return 0.0;
        int presentDays = 0;
        for (Boolean present : attendanceRecord.values()) {
            if (present) presentDays++;
        }
        return (presentDays * 100.0) / attendanceRecord.size();
    }

    public Map<LocalDate, Boolean> getAttendanceRecord() {
        return new HashMap<>(attendanceRecord);
    }
}
