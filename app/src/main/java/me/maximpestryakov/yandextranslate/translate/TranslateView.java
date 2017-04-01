package me.maximpestryakov.yandextranslate.translate;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import me.maximpestryakov.yandextranslate.model.Translation;

interface TranslateView extends MvpView {

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showTranslation(Translation translation);
}
