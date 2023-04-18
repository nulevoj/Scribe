package ua.edu.ontu.scribe.vocabulary;

import ua.edu.ontu.model.entity.Account;
import ua.edu.ontu.model.entity.Person;
import ua.edu.ontu.scribe.vocabulary.references.DateReference;
import ua.edu.ontu.scribe.vocabulary.references.PersonReference;

import java.util.HashMap;
import java.util.Map;

public class Vocabulary {

    private Map<String, String> map = new HashMap<>();

    public void putAll(Account account) {
        putDate();
        putPerson(account.getPerson());
    }

    public void putDate() {
        new DateReference(map).putDateReferences();
    }

    public void putPerson(Person person) {
        if (person == null) {
            return;
        }
        new PersonReference(map).putPersonReferences(person);
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

}
