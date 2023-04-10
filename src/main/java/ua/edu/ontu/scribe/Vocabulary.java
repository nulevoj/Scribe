package ua.edu.ontu.scribe;

import ua.edu.ontu.model.entity.Account;
import ua.edu.ontu.model.entity.Person;
import ua.edu.ontu.scribe.references.DateReference;
import ua.edu.ontu.scribe.references.PersonReference;

import java.util.HashMap;
import java.util.Map;

public class Vocabulary {

    private Map<String, String> map = new HashMap<>();

    public Vocabulary() {
        putDate();
    }

    public Vocabulary(Account account) {
        this();
        putPerson(account.getPerson());
    }

    public Map<String, String> getMap() {
        return map;
    }

    private void putDate() {
        new DateReference(map).putDateReferences();
    }

    private void putPerson(Person person) {
        if (person == null) {
            return;
        }
        new PersonReference(map).putPersonReferences(person);
    }

}
