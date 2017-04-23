package me.maximpestryakov.yandextranslate.translations;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

@InjectViewState
public class TranslationsPresenter extends MvpPresenter<TranslationsView> {

    void onEmptyList() {
        getViewState().showEmptyMessage();
    }

    void onNotEmptyList() {
        getViewState().showList();
    }
}
