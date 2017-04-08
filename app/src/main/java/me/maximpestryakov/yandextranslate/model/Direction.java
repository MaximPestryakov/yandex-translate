package me.maximpestryakov.yandextranslate.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Direction extends RealmObject {

    @PrimaryKey
    private String value;

    private Language from;

    private Language to;

    public Direction() {
    }

    public Direction(Language from, Language to) {
        value = from.getCode() + '-' + to.getCode();
        this.from = from;
        this.to = to;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Language getFrom() {
        return from;
    }

    public void setFrom(Language from) {
        this.from = from;
    }

    public Language getTo() {
        return to;
    }

    public void setTo(Language to) {
        this.to = to;
    }
}
