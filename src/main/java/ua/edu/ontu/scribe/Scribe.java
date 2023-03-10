package ua.edu.ontu.scribe;

import com.xandryex.WordReplacer;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Scribe {

    private XWPFDocument document;
    private WordReplacer replacer;

    public Scribe(File file) throws InvalidFormatException, IOException {
        document = new XWPFDocument(OPCPackage.open(file));
        replacer = new WordReplacer(document);
    }

    public Set<String> getPlaceholders() {
        Set<String> result = new LinkedHashSet<>();
        String text = new XWPFWordExtractor(document).getText();
        Matcher matcher = Pattern.compile(PlaceholderUtil.getRegex()).matcher(text);
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

    public void save(File file) throws Exception {
        document.write(new FileOutputStream(file));
    }

}
