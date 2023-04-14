package ua.edu.ontu.util;

import ua.edu.ontu.model.entity.Employee;
import ua.edu.ontu.model.entity.Person;
import ua.edu.ontu.model.entity.Student;

public class PersonFactory {

    public static void main(String[] args) {

    }

    public static Student createStudent() {
        Student student = new Student();
        fillPerson(student);

        student.setFaculty("Комп'ютерної Інженерії, Програмування та Кіберзахисту");
        student.setDegree("Бакалавр");
        student.setSpecialty("Розробка ігор та інтерактивних медіа у віртуальній реальності");
        student.setYear("Другий скорочений");

        return student;
    }

    public static Employee createEmployee() {
        Employee employee = new Employee();
        fillPerson(employee);

        employee.setPosition("доцент");
        employee.setDegree("Доктор комп'ютерних наук");

        return employee;
    }

    private static void fillPerson(Person person) {
        person.setName("Тарас");
        person.setSurname("Шевченко");
        person.setPatronymic("Григорович");
    }

}
