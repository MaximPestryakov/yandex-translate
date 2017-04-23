package me.maximpestryakov.yandextranslate.translations;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

interface TranslationsView extends MvpView {

    @StateStrategyType(SingleStateStrategy.class)
    void showList();

    @StateStrategyType(SingleStateStrategy.class)
    void showEmptyMessage();
}
