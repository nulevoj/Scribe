package ua.edu.ontu.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileService {

    @Value("${scribe.source-path}")
    private String sourcePath;

    @Value("${scribe.output-path}")
    private String outputPath;

    public File createFromSourcePath(String fileName) {
        return new File(sourcePath + fileName);
    }

    public Path createInSourcePath(String fileName) {
        return Paths.get(sourcePath, fileName);
    }

    public File createInOutputPath(String fileName) {
        return new File(outputPath + fileName);
    }

}
