package ua.edu.ontu.scribe;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class DefaultDirectoryFileFactory {

    @Value("scribe.source-path")
    private String sourcePath;

    @Value("scribe.output-path")
    private String outputPath;

    public File createFromSourcePath(String fileName) {
        return new File(sourcePath + fileName);
    }

    public File createInOutputPath(String fileName) {
        return new File(outputPath + fileName);
    }

}
