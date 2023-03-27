package ua.edu.ontu.service;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class SourceDirectoryFileService {

    @Value("${scribe.source-directory}")
    private String sourceDirectory;

    public File getFile(String fileName) {
        return new File(sourceDirectory + fileName);
    }

    public void save(MultipartFile file) throws IOException {
        byte[] bytes = file.getBytes();
        Path path = Paths.get(sourceDirectory, file.getOriginalFilename());
        Files.write(path, bytes);
    }

    public void delete(String file) throws IOException {
        Path path = Paths.get(sourceDirectory, file);
        Files.delete(path);
    }

    public ResponseEntity<ByteArrayResource> download(String fileName) {
        ByteArrayResource resource;

        try {
            Path path = Paths.get(sourceDirectory, fileName);
            byte[] data = Files.readAllBytes(path);
            resource = new ByteArrayResource(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String encodedName = UriUtils.encode(fileName, StandardCharsets.UTF_8);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + encodedName)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(resource.contentLength())
                .body(resource);
    }

    public ResponseEntity<ByteArrayResource> download(XWPFDocument document, String fileName) {
        ByteArrayResource resource;

        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            document.write(outputStream);
            resource = new ByteArrayResource(outputStream.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String encodedName = UriUtils.encode(fileName, StandardCharsets.UTF_8);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + encodedName);
        headers.add(HttpHeaders.CONTENT_TYPE,
                "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(resource.contentLength())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

}
