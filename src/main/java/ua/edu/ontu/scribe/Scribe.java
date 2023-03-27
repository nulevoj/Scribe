package ua.edu.ontu.scribe;

import com.xandryex.WordReplacer;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import ua.edu.ontu.scribe.placeholder.Placeholder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Scribe {

    private XWPFDocument document;
    private WordReplacer replacer;

    public Scribe(File file) {
        try {
            document = new XWPFDocument(new FileInputStream(file));
            replacer = new WordReplacer(document);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public XWPFDocument getDocument() {
        return document;
    }

    public Set<String> getPlaceholders(Placeholder placeholder) {
        Set<String> result = new LinkedHashSet<>();
        String text = new XWPFWordExtractor(document).getText();
        Matcher matcher = Pattern.compile(placeholder.getRegex()).matcher(text);
        while (matcher.find()) {
            result.add(matcher.group());
        }
        return result;
    }

    public void replace(String placeholder, String replacement) {
        replacer.replaceWordsInText(placeholder, replacement);
        replacer.replaceWordsInTables(placeholder, replacement);
    }

    public void replaceAll(Map<String, String> map) {
        for (String placeholder : map.keySet()) {
            replace(placeholder, map.get(placeholder));
        }
    }

    public void close() {
        try {
            document.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
