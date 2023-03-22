package ua.edu.ontu.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileService {

    @Value("${scribe.source-path}")
    private String sourcePath;

    @Value("${scribe.output-path}")
    private String outputPath;

    public File getFromSourcePath(String fileName) {
        return new File(sourcePath + fileName);
    }

    public File createInOutputPath(String fileName) {
        return new File(outputPath + fileName);
    }

    public void saveInSourcePath(MultipartFile file) throws IOException {
        byte[] bytes = file.getBytes();
        Path path = Paths.get(sourcePath, file.getOriginalFilename());
        Files.write(path, bytes);
    }

    public void deleteFromSourcePath(String file) throws IOException {
        Path path = Paths.get(sourcePath, file);
        Files.delete(path);
    }

}
