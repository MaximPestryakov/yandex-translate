package me.maximpestryakov.yandextranslate.navigation;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(SingleStateStrategy.class)
interface NavigationView extends MvpView {

    void showTranslate();

    void showFavorites();

    void showHistory();
}
