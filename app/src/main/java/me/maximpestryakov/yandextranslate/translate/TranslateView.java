package me.maximpestryakov.yandextranslate.translate;

import com.arellomobile.mvp.MvpView;

import me.maximpestryakov.yandextranslate.model.Translation;

public interface TranslateView extends MvpView {

    void showTranslation(Translation translation);
}
