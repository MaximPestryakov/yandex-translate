package me.maximpestryakov.yandextranslate.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Translation {

    @SerializedName("code")
    private int code;

    @SerializedName("lang")
    private String lang;

    private String original;

    private boolean favorite;

    @SerializedName("text")
    private List<String> text;

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public int getCode() {
        return code;
    }

    public String getLang() {
        return lang;
    }

    public List<String> getText() {
        return text;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}
