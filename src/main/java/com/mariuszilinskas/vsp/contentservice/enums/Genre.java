package com.mariuszilinskas.vsp.contentservice.enums;

public enum Genre {
    DRAMA,
    ACTION,
    COMEDY,
    THRILLER,
    HORROR,
    ROMANCE,
    SCI_FI_FANTASY,
    MYSTERY,
    DOCUMENTARY,
    ANIMATION,
    ADVENTURE,
    CRIME,
    FAMILY,
    WAR,
    HISTORY,
    MUSICAL,
    WESTERN,
    BIOGRAPHY,
    SPORTS;

    @Override
    public String toString() {
        if (this == SCI_FI_FANTASY) {
            return "Sci-Fi & Fantasy";
        }
        return name().charAt(0) + name().substring(1).toLowerCase().replace('_', ' ');
    }
}
