package com.university.shared;

import java.util.Locale;

public enum SortDirection {
    DESC, ASC;

    private final String value;

    SortDirection() {
        this.value = this.toString().toLowerCase(Locale.ROOT);
    }

    public String getValue() {
        return value;
    }
}
