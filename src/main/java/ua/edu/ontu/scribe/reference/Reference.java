package ua.edu.ontu.scribe.reference;

import java.util.HashMap;
import java.util.Map;

public class Reference {

    private final Map<String, String> reference = new HashMap<>();

    public Map<String, String> getMap() {
        return reference;
    }

    public String get(String key) {
        return reference.get(key);
    }

    public void putUnique(String key, String value) {
        if (reference.containsKey(key)) {
            System.err.println("Key '" + key + "' is already in the vocabulary");
            return;
        }
        reference.put(key, value);
    }

    public void putUnique(Map<String, String> map) {
        for (String key : map.keySet()) {
            putUnique(key, map.get(key));
        }
    }

}
