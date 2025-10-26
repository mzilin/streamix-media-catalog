package com.mariuszilinskas.streamix.media.catalog.enums;

import com.mariuszilinskas.streamix.media.catalog.util.CatalogUtils;

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
        return CatalogUtils.convertEnumToString(this);
    }
}
