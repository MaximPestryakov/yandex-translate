package me.maximpestryakov.yandextranslate.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import me.maximpestryakov.yandextranslate.util.RealmString;

public class Translation extends RealmObject {

    @SerializedName("lang")
    private String lang;

    @SerializedName("text")
    private RealmList<RealmString> text;

    @PrimaryKey
    private String original;

    private boolean favorite;

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public String getLang() {
        return lang;
    }

    public List<RealmString> getText() {
        return text;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}
