package ua.edu.ontu.scribe;

import com.xandryex.WordReplacer;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Scribe {

    private final String filename;
    private final FileInputStream fileInputStream;
    private final XWPFDocument document;
    private final WordReplacer replacer;

    public Scribe(File file) {
        try {
            filename = file.getName();
            fileInputStream = new FileInputStream(file);
            document = new XWPFDocument(fileInputStream);
            replacer = new WordReplacer(document);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getFilename() {
        return filename;
    }

    public XWPFDocument getDocument() {
        return document;
    }

    public Set<String> getPlaceholders(String regex) {
        Set<String> result = new LinkedHashSet<>();
        String text = new XWPFWordExtractor(document).getText();
        Matcher matcher = Pattern.compile(regex).matcher(text);
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
            fileInputStream.close();
            document.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
