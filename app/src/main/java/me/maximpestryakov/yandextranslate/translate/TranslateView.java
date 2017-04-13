package me.maximpestryakov.yandextranslate.translate;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import me.maximpestryakov.yandextranslate.model.Translation;
import me.maximpestryakov.yandextranslate.util.AddToEndSingleTagStrategy;

interface TranslateView extends MvpView {

    String TAG_UPDATE_TEXT = "TAG_UPDATE_TEXT";

    @StateStrategyType(value = AddToEndSingleTagStrategy.class, tag = TAG_UPDATE_TEXT)
    void showTranslation(Translation translation);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void setFromLang(String from);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void setToLang(String to);

    @StateStrategyType(OneExecutionStateStrategy.class)
    void showTextToTranslate(String text);

    @StateStrategyType(value = AddToEndSingleTagStrategy.class, tag = TAG_UPDATE_TEXT)
    void clear();
}
