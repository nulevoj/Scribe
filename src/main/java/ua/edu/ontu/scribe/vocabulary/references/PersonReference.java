package ua.edu.ontu.scribe.vocabulary.references;

import ua.edu.ontu.model.entity.Employee;
import ua.edu.ontu.model.entity.Person;
import ua.edu.ontu.model.entity.Student;

import java.util.Map;

public class PersonReference {

    private final Map<String, String> map;

    private Person person;

    public PersonReference(Map<String, String> map) {
        this.map = map;
    }

    public void putPersonReferences(Person person) {
        this.person = person;
        putPerson();
        if (person instanceof Student) {
            putStudent();
        }
        if (person instanceof Employee) {
            putEmployee();
        }
    }

    private void putPerson() {
        map.put("name", person.getName());
        map.put("surname", person.getSurname());
        map.put("patronymic", person.getPatronymic());
        map.put("initial", getInitial());
        map.put("full name", getFullName());
    }

    private void putStudent() {
        Student student = (Student) person;
        map.put("degree", student.getDegree());
        map.put("faculty", student.getFaculty());
        map.put("term", student.getYear());
        map.put("specialty", student.getSpecialty());
    }

    private void putEmployee() {
        Employee employee = (Employee) person;
        map.put("degree", employee.getDegree());
        map.put("position", employee.getPosition());
    }

    private String getInitial() {
        String initial = "_______________";
        try {
            initial = person.getSurname() + " " +
                    person.getName().charAt(0) + ". " +
                    person.getPatronymic().charAt(0) + ".";
        } catch (StringIndexOutOfBoundsException | NullPointerException ignored) {
        }
        return initial;
    }

    private String getFullName() {
        String fullName = "_________________________";
        try {
            fullName = person.getSurname() + " " +
                    person.getName() + " " +
                    person.getPatronymic();
        } catch (StringIndexOutOfBoundsException | NullPointerException ignored) {
        }
        return fullName;
    }

}
