package com.mariuszilinskas.streamix.media.catalog.enums;

import com.mariuszilinskas.streamix.media.catalog.util.CatalogUtils;

public enum ContentType {
    MOVIE,
    SERIES,
    DOCUMENTARY,
    SHORT_FILM,
    TV_SHOW,
    LIVE_STREAM,
    SPORTS,
    REALITY_TV,
    GAME_SHOW,
    TALK_SHOW;

    @Override
    public String toString() {
        return CatalogUtils.convertEnumToString(this);
    }
}
