package me.maximpestryakov.yandextranslate.translations;

import com.arellomobile.mvp.MvpView;

import java.util.List;

import me.maximpestryakov.yandextranslate.model.Translation;


interface TranslationsView extends MvpView {

    void showFavorites(List<Translation> favorites);
}
