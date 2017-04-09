package me.maximpestryakov.yandextranslate.translate;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;

import me.maximpestryakov.yandextranslate.model.Language;
import me.maximpestryakov.yandextranslate.model.Translation;

interface TranslateView extends MvpView {

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showTranslation(Translation translation);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showLangs(List<Language> languages);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void setFromLang(String from);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void setToLang(String to);
}
