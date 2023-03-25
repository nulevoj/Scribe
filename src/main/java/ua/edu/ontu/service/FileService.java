package ua.edu.ontu.service;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileService {

    @Value("${scribe.source-path}")
    private String sourcePath;

    public File getFromSourcePath(String fileName) {
        return new File(sourcePath + fileName);
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

    public ByteArrayResource getFileForDownloading(String fileName) throws IOException {
        Path path = Paths.get(sourcePath, fileName);
        byte[] data = Files.readAllBytes(path);
        return new ByteArrayResource(data);
    }

    public ByteArrayResource prepareForDownloading(XWPFDocument document) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        document.write(outputStream);
        return new ByteArrayResource(outputStream.toByteArray());
    }

}
