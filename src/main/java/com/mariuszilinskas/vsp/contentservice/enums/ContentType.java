package com.mariuszilinskas.vsp.contentservice.enums;

import com.mariuszilinskas.vsp.contentservice.util.ContentUtils;

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
        return ContentUtils.convertEnumToString(this);
    }
}
