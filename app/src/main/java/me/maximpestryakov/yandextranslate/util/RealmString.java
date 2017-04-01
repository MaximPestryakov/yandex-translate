package me.maximpestryakov.yandextranslate.util;

import io.realm.RealmObject;

public class RealmString extends RealmObject {

    private String value;

    public RealmString() {
    }

    RealmString(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
