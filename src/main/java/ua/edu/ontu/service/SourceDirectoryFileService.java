package ua.edu.ontu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class SourceDirectoryFileService {

    @Value("${scribe.source-directory}")
    private String sourceDirectory;

    @Autowired
    private DownloadService downloadService;

    public File getFile(String filename) {
        return new File(sourceDirectory + filename);
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

    public ResponseEntity<ByteArrayResource> download(String filename) {
        ByteArrayResource resource;
        try {
            Path path = Paths.get(sourceDirectory, filename);
            byte[] data = Files.readAllBytes(path);
            resource = new ByteArrayResource(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String encodedName = downloadService.encodeString(filename);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + encodedName);

        return downloadService.createResponse(resource, headers);
    }

}
