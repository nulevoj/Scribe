package ua.edu.ontu.model.service;

import org.springframework.stereotype.Service;
import ua.edu.ontu.model.entity.Account;
import ua.edu.ontu.model.entity.Employee;
import ua.edu.ontu.model.entity.Person;
import ua.edu.ontu.model.entity.Student;

@Service
public class PersonService {

    public void update(Account account, Person fresh) {
        update(account.getPerson(), fresh);
    }

    public void update(Person old, Person fresh) {
        old.setName(fresh.getName());
        old.setSurname(fresh.getSurname());
        old.setPatronymic(fresh.getPatronymic());

        if (old instanceof Student) {
            updateStudent((Student) old, (Student) fresh);
        }
        if (old instanceof Employee) {
            updateEmployee((Employee) old, (Employee) fresh);
        }
    }

    private void updateStudent(Student old, Student fresh) {
        old.setFaculty(fresh.getFaculty());
        old.setDegree(fresh.getDegree());
        old.setSpecialty(fresh.getSpecialty());
        old.setYear(fresh.getYear());
    }

    private void updateEmployee(Employee old, Employee fresh) {
        old.setPosition(fresh.getPosition());
        old.setDegree(fresh.getDegree());
    }

}
