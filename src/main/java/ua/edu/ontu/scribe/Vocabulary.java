package ua.edu.ontu.scribe;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import ua.edu.ontu.model.entity.Account;
import ua.edu.ontu.model.entity.Employee;
import ua.edu.ontu.model.entity.Person;
import ua.edu.ontu.model.entity.Student;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

public class Vocabulary {

    private final Map<String, String> vocabulary = new HashMap<>();

    public Vocabulary(Account account) {
        putAll(account);
    }

    public String get(String keyword) {
        return vocabulary.get(keyword);
    }

    private void putAll(@NonNull Account account) {
        putDate();
        putPerson(account.getPerson());
    }

    private void putUnique(String key, String value) {
        if (vocabulary.containsKey(key)) {
            System.err.println("Key '" + key + "' is already in the vocabulary");
            return;
        }
        vocabulary.put(key, value);
    }

    private void putDate() {
        Date date = new Date();
        putUnique("date", date.getDate());
        putUnique("full date", date.getFullDate());
        putUnique("year", date.getYear());
    }

    private void putPerson(@Nullable Person person) {
        if (person == null) {
            return;
        }
        putUnique("name", person.getName());
        putUnique("surname", person.getSurname());
        putUnique("patronymic", person.getPatronymic());
        putUnique("initial", person.getSurname() + " " +
                person.getName().charAt(0) + ". " +
                person.getPatronymic().charAt(0) + ".");
        if (person instanceof Student) {
            putStudent((Student) person);
        }
        if (person instanceof Employee) {
            putEmployee((Employee) person);
        }
    }

    private void putStudent(Student student) {
        putUnique("degree", student.getDegree());
        putUnique("faculty", student.getFaculty());
        putUnique("year", student.getYear());
        putUnique("specialty", student.getSpecialty());
    }

    private void putEmployee(Employee employee) {
        putUnique("degree", employee.getDegree());
        putUnique("position", employee.getPosition());
    }

    static class Date {

        private static final ZoneId ZONE_UA = ZoneId.of("Europe/Kiev");

        private final LocalDate date = LocalDate.now(ZONE_UA);

        public String getDate() {
            return date.getDayOfMonth() + " " + getMonthUA(date.getMonth().getValue());
        }

        public String getFullDate() {
            return date.getDayOfMonth() + "." + String.format("%02d", date.getMonth().getValue()) + "." + date.getYear();
        }

        public String getYear() {
            return String.valueOf(date.getYear());
        }

        private static String getMonthUA(int month) {
            return switch (month) {
                case 1 -> "січня";
                case 2 -> "лютого";
                case 3 -> "березня";
                case 4 -> "квітня";
                case 5 -> "травня";
                case 6 -> "червня";
                case 7 -> "липня";
                case 8 -> "серпня";
                case 9 -> "вересня";
                case 10 -> "жовтня";
                case 11 -> "листопада";
                case 12 -> "грудня";
                default -> "________";
            };
        }
    }
}
