package com.mariuszilinskas.vsp.contentservice.util;

public abstract class ContentUtils {

    private ContentUtils() {
        // Private constructor to prevent instantiation
    }

    public static final String TIMESTAMP_FORMAT = "yyyy-MM-dd hh:mm:ss";

    public static String convertEnumToString(Enum<?> enumValue) {
        String[] words = enumValue.name().toLowerCase().split("_");
        StringBuilder result = new StringBuilder();
        for (String word : words) {
            result.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1)).append(" ");
        }
        return result.toString().trim();
    }

}
