package me.maximpestryakov.yandextranslate.model;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import me.maximpestryakov.yandextranslate.util.RealmString;

public class Translation extends RealmObject {

    @PrimaryKey
    private int id;

    private String original;

    private boolean favorite;

    @SerializedName("lang")
    private String lang;

    @SerializedName("text")
    private RealmList<RealmString> text;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public RealmList<RealmString> getText() {
        return text;
    }

    public void setText(RealmList<RealmString> text) {
        this.text = text;
    }
}
