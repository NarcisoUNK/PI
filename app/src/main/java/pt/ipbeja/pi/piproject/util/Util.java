package pt.ipbeja.pi.piproject.util;

public final class Util {
    public static String removeSpaces(String s) {
        String descriptionNoMultipleSpaces = s.replaceAll("^\\s*", "");
        return descriptionNoMultipleSpaces.replaceAll("\\n( |\\t)*", "\n");
    }
}
