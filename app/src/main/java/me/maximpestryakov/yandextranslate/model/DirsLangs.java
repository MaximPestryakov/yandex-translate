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

    public void setDirections(List<Direction> directions) {
        this.directions = directions;
    }

    public List<Language> getLanguages() {
        return languages;
    }

    public void setLanguages(List<Language> languages) {
        this.languages = languages;
    }
}
