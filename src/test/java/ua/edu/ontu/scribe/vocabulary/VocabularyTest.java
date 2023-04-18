package ua.edu.ontu.scribe.vocabulary;

import org.junit.jupiter.api.Test;
import ua.edu.ontu.model.entity.Account;
import ua.edu.ontu.model.entity.Employee;
import ua.edu.ontu.model.entity.Person;
import ua.edu.ontu.model.entity.Student;

import java.util.HashMap;
import java.util.Map;

public class VocabularyTest {

    private static Account account = new Account();

    @Test
    public void uniqueKeysStudent() {
        uniqueKeysPerson(new Student());
    }

    @Test
    public void uniqueKeysEmployee() {
        uniqueKeysPerson(new Employee());
    }

    private void uniqueKeysPerson(Person person) {
        account.setPerson(person);
        Map<String, String> map = new HashMap<>() {
            @Override
            public String put(String key, String value) {
                if (containsKey(key)) {
                    System.err.printf("""
                            Key '%s' repeats:
                            Old value: %s
                            New value: %s
                            """, key, get(key), value);
                    assert false;
                }
                return super.put(key, value);
            }
        };
        Vocabulary vocabulary = new Vocabulary();
        vocabulary.setMap(map);
        vocabulary.putAll(account);
    }

}
