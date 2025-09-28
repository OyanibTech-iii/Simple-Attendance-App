// AttendanceManager class
import java.time.LocalDate;
import java.util.*;

class AttendanceManager {
    private Map<String, Person> personRegistry;
    private List<LocalDate> schoolDays;
    private String institutionName;

    public AttendanceManager(String institutionName) {
        this.institutionName = institutionName;
        this.personRegistry = new HashMap<>();
        this.schoolDays = new ArrayList<>();
        initializeSchoolDays();
    }

    private void initializeSchoolDays() {
        LocalDate startDate = LocalDate.now().minusDays(10);
        int daysAdded = 0;
        LocalDate current = startDate;
        while (daysAdded < 10) {
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
            System.out.println(person.getRole() + " " + person.getName() + " registered successfully.");
        } catch (Exception e) {
            System.err.println("Error registering person: " + e.getMessage());
        }
    }

    public Person getPersonById(String id) {
        return personRegistry.get(id);
    }

    public List<Person> getAllPersons() {
        return new ArrayList<>(personRegistry.values());
    }

    public List<LocalDate> getSchoolDays() {
        return new ArrayList<>(schoolDays);
    }

    public String getInstitutionName() {
        return institutionName;
    }
}
