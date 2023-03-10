package ua.edu.ontu.scribe;

public class PlaceholderUtil {

    // [example phrase]
    private static final String REGEX = "\\[.+]";

    public static String parsePlaceholder(String placeholder) {
        return placeholder.substring(1, placeholder.length() - 1);
    }

    public static String getRegex() {
        return REGEX;
    }

}
