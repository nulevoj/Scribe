package ua.edu.ontu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.ontu.model.entity.Account;
import ua.edu.ontu.scribe.Scribe;
import ua.edu.ontu.scribe.Vocabulary;
import ua.edu.ontu.scribe.placeholder.Placeholder;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

@Service
public class ScribeService {

    @Autowired
    private FileService fileService;

    @Autowired
    private Placeholder placeholder;

    public Map<String, String> getPreliminaryReplacementMap(String fileName, Account account) {
        Scribe scribe = new Scribe(fileService.getFromSourcePath(fileName));
        Set<String> placeholders = scribe.getPlaceholders(placeholder);
        Map<String, String> map = new LinkedHashMap<>();
        Vocabulary vocabulary = new Vocabulary(account);
        for (String placeholder : placeholders) {
            map.put(placeholder, vocabulary.get(this.placeholder.parsePlaceholder(placeholder)));
        }
        scribe.close();
        return map;
    }

    public Scribe replaceAll(String fileName, Map<String, String> map) {
        Scribe scribe = new Scribe(fileService.getFromSourcePath(fileName));
        scribe.replaceAll(map);
        scribe.close();
        return scribe;
    }

}
