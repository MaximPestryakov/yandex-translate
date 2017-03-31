package me.maximpestryakov.yandextranslate.translate;

import com.arellomobile.mvp.MvpView;

public interface TranslateView extends MvpView {

    void showTranslation(String translation);
}
