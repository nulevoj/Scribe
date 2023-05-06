package ua.edu.ontu.scribe.placeholder;

import org.springframework.stereotype.Component;

@Component
public class DefaultPlaceholder implements Placeholder {

    // [example phrase]
    private static final String REGEX = "\\[[^\\]]*\\]";

    public String parsePlaceholder(String placeholder) {
        return placeholder.substring(1, placeholder.length() - 1);
    }

    public String getRegex() {
        return REGEX;
    }

}
