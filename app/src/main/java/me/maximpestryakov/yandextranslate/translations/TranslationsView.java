package me.maximpestryakov.yandextranslate.translations;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

interface TranslationsView extends MvpView {

    @StateStrategyType(SkipStrategy.class)
    void showTranslation(String textToTranslate);
}
