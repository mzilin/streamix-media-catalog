package com.mariuszilinskas.vsp.contentservice.enums;

import com.mariuszilinskas.vsp.contentservice.util.ContentUtils;

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
        return ContentUtils.convertEnumToString(this);
    }
}
