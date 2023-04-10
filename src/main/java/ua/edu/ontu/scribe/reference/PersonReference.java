package ua.edu.ontu.scribe.reference;

import ua.edu.ontu.model.entity.Employee;
import ua.edu.ontu.model.entity.Person;
import ua.edu.ontu.model.entity.Student;

import java.util.Map;

public class PersonReference {

    private Reference reference = new Reference();

    public Map<String, String> getPersonReferences(Person person) {
        putPerson(person);
        return reference.getMap();
    }

    private void putPerson(Person person) {
        if (person == null) {
            return;
        }
        reference.putUnique("name", person.getName());
        reference.putUnique("surname", person.getSurname());
        reference.putUnique("patronymic", person.getPatronymic());
        try {
            reference.putUnique("initial", person.getSurname() + " " +
                    person.getName().charAt(0) + ". " +
                    person.getPatronymic().charAt(0) + ".");
        } catch (StringIndexOutOfBoundsException e) {
            e.printStackTrace();
        }

        if (person instanceof Student) {
            putStudent((Student) person);
        }
        if (person instanceof Employee) {
            putEmployee((Employee) person);
        }
    }

    private void putStudent(Student student) {
        reference.putUnique("degree", student.getDegree());
        reference.putUnique("faculty", student.getFaculty());
        reference.putUnique("term", student.getYear());
        reference.putUnique("specialty", student.getSpecialty());
    }

    private void putEmployee(Employee employee) {
        reference.putUnique("degree", employee.getDegree());
        reference.putUnique("position", employee.getPosition());
    }

}
