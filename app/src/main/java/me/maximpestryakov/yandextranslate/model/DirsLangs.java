package me.maximpestryakov.yandextranslate.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DirsLangs {

    @SerializedName("dirs")
    private List<Direction> directions;

    @SerializedName("langs")
    private List<Language> languages;

    public List<Direction> getDirections() {
        return directions;
    }

    public List<Language> getLanguages() {
        return languages;
    }
}
