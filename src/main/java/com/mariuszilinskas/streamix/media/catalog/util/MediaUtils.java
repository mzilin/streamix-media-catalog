package com.mariuszilinskas.streamix.media.catalog.util;

import com.mariuszilinskas.streamix.media.catalog.dto.MediaSearchQuery;
import com.mariuszilinskas.streamix.media.catalog.enums.Tag;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public abstract class MediaUtils {

    private MediaUtils() {
        // Private constructor to prevent instantiation
    }

//    public static String generateCacheKey(MediaSearchQuery query) {
//        StringBuilder keyBuilder = new StringBuilder();
//
//        appendToKey(keyBuilder, query.getQ() == null ? null : query.getQ().toLowerCase().trim());
//        appendToKey(keyBuilder, query.getType() == null ? null : query.getType().name());
//        appendToKey(keyBuilder, query.getDate() == null ? null : query.getDate().toString());
//        appendToKey(keyBuilder, query.getMinRating());
//        appendToKey(keyBuilder, query.getGenre() == null ? null : query.getGenre().name());
//        appendToKey(keyBuilder, query.getCountry() == null ? null : query.getCountry().toLowerCase().trim());
//
//        // Handle tags (sorted and concatenated)
//        if (query.getTags() != null && !query.getTags().isEmpty()) {
//            String tags = query.getTags().stream()
////                    .map(Tag::toString)
//                    .sorted()
//                    .reduce((tag1, tag2) -> tag1 + "," + tag2) // Comma-separated for clarity
//                    .orElse("");
//            appendToKey(keyBuilder, tags);
//        } else {
//            appendToKey(keyBuilder, null);
//        }
//
//        appendToKey(keyBuilder, query.getPage());
//        appendToKey(keyBuilder, query.getSize());
//
//        return keyBuilder.toString();
//    }
//
//    private static void appendToKey(StringBuilder keyBuilder, Object value) {
//        if (!keyBuilder.isEmpty()) {
//            keyBuilder.append("-");
//        }
//        keyBuilder.append(value == null ? "" : value.toString());
//    }


//    public static String generateHashedCacheKey(MediaSearchQuery query) {
//        String baseKey = generateCacheKey(query);
//        try {
//            MessageDigest md = MessageDigest.getInstance("MD5");
//            byte[] digest = md.digest(baseKey.getBytes());
//            StringBuilder hexString = new StringBuilder();
//            for (byte b : digest) {
//                hexString.append(Integer.toHexString(0xff & b));
//            }
//            return hexString.toString();
//        } catch (NoSuchAlgorithmException e) {
//            throw new RuntimeException("Error generating hash for cache key", e);
//        }
//    }

}
