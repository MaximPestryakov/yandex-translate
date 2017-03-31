package me.maximpestryakov.yandextranslate.favorites;

import com.arellomobile.mvp.MvpView;

import java.util.List;

import me.maximpestryakov.yandextranslate.model.Translation;


interface FavoritesView extends MvpView {

    void showFavorites(List<Translation> favorites);
}
