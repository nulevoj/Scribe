package ua.edu.ontu.scribe;

import ua.edu.ontu.model.entity.Person;
import ua.edu.ontu.scribe.reference.DateReference;
import ua.edu.ontu.scribe.reference.PersonReference;
import ua.edu.ontu.scribe.reference.Reference;

public class Vocabulary {

    private Reference reference = new Reference();

    public Vocabulary(Person person) {
        putDate();
        putPerson(person);
    }

    public String get(String key) {
        return reference.get(key);
    }

    private void putDate() {
        reference.putUnique(new DateReference().getDateReferences());
    }

    private void putPerson(Person person) {
        reference.putUnique(new PersonReference().getPersonReferences(person));
    }

}
