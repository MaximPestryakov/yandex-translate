package me.maximpestryakov.yandextranslate.model;

import android.support.annotation.NonNull;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Language extends RealmObject implements Comparable<Language> {

    @PrimaryKey
    private String code;

    private String title;

    public Language() {
    }

    public Language(String code, String title) {
        this.code = code;
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int compareTo(@NonNull Language language) {
        return getCode().compareTo(language.getCode());
    }
}
