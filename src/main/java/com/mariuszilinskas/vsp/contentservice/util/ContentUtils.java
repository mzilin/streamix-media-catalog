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

    public static <E extends Enum<E>> E convertStringToEnum(String value, Class<E> enumClass) {
        if (value == null || enumClass == null) {
            return null;
        }
        try {
            return Enum.valueOf(enumClass, value.toUpperCase());
        } catch (IllegalArgumentException e) {
            // Log the exception or handle it as necessary
            System.out.println("There was an error converting '" + value + "' to " + enumClass.getSimpleName());
            return null;
        }
    }

}
