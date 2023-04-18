package ua.edu.ontu.service;

import fr.opensagres.poi.xwpf.converter.pdf.PdfConverter;
import fr.opensagres.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ua.edu.ontu.model.entity.Account;
import ua.edu.ontu.scribe.Scribe;
import ua.edu.ontu.scribe.vocabulary.Vocabulary;
import ua.edu.ontu.scribe.placeholder.Placeholder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

@Service
public class ScribeService {

    @Autowired
    private SourceDirectoryFileService fileService;

    @Autowired
    private Placeholder placeholder;

    @Autowired
    private DownloadService downloadService;

    public Map<String, String> getPreliminaryReplacementMap(String filename, Account account) {
        Set<String> placeholders = getPlaceholders(filename);
        Map<String, String> vocabularyMap = getVocabularyMap(account);

        Map<String, String> result = new LinkedHashMap<>();
        for (String placeholder : placeholders) {
            String key = this.placeholder.parsePlaceholder(placeholder);
            result.put(placeholder, vocabularyMap.get(key));
        }
        return result;
    }

    public Set<String> getPlaceholders(String filename) {
        Scribe scribe = new Scribe(fileService.getFile(filename));
        Set<String> placeholders = scribe.getPlaceholders(placeholder.getRegex());
        scribe.close();
        return placeholders;
    }

    public Map<String, String> getVocabularyMap(Account account) {
        Vocabulary vocabulary = new Vocabulary();
        vocabulary.putAll(account);
        return vocabulary.getMap();
    }

    public ResponseEntity<ByteArrayResource> downloadDocx(Scribe scribe) {
        ByteArrayResource resource;
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            scribe.getDocument().write(outputStream);
            resource = new ByteArrayResource(outputStream.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String encodedName = downloadService.encodeString(scribe.getFilename());
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + encodedName);
        headers.add(HttpHeaders.CONTENT_TYPE,
                "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        return downloadService.createResponse(resource, headers);
    }

    public ResponseEntity<ByteArrayResource> downloadPdf(Scribe scribe) {
        ByteArrayResource resource;
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PdfOptions options = PdfOptions.create();
            PdfConverter.getInstance().convert(scribe.getDocument(), outputStream, options);
            resource = new ByteArrayResource(outputStream.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String filename = FilenameUtils.removeExtension(scribe.getFilename()) + ".pdf";
        String encodedName = downloadService.encodeString(filename);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + encodedName);
        headers.add(HttpHeaders.CONTENT_TYPE, "application/pdf");
        return downloadService.createResponse(resource, headers);
    }

}
