package com.mariuszilinskas.vsp.contentservice.enums;

public enum Tag {
    FEATURED,
    NEW_RELEASE,
    TRENDING,
    CRITICALLY_ACCLAIMED,
    AWARD_WINNING,
    FAMILY_FRIENDLY,
    FOR_KIDS,
    CLASSIC,
    CULT_FAVORITE,
    MUST_WATCH,
    HIGHLY_RATED,
    TOP_CHART,
    EDITORS_CHOICE,
    HIDDEN_GEM,
    INSPIRATIONAL,
    THRILLING,
    POPULAR,
    STAFF_PICK,
    LIMITED_SERIES,
    LONG_RUNNING;

    @Override
    public String toString() {
        String[] words = name().toLowerCase().split("_");
        StringBuilder result = new StringBuilder();
        for (String word : words) {
            result.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1)).append(" ");
        }
        return result.toString().trim();
    }
}
