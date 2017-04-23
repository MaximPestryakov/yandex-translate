package me.maximpestryakov.yandextranslate.util;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class RealmString extends RealmObject {

    @PrimaryKey
    private String value;

    public RealmString() {
    }

    RealmString(String value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj || obj instanceof RealmString && value.equals(((RealmString) obj).value);
    }

    @Override
    public String toString() {
        return value;
    }
}
