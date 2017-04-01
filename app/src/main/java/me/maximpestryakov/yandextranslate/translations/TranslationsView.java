package me.maximpestryakov.yandextranslate.translations;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;

import me.maximpestryakov.yandextranslate.model.Translation;

interface TranslationsView extends MvpView {

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showFavorites(List<Translation> favorites);
}
