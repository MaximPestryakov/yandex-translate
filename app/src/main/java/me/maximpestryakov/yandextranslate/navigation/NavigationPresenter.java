package me.maximpestryakov.yandextranslate.navigation;

import android.support.annotation.IdRes;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import me.maximpestryakov.yandextranslate.R;

@InjectViewState
public class NavigationPresenter extends MvpPresenter<NavigationView> {

    NavigationPresenter() {
        getViewState().showTranslate();
    }

    boolean onNavigate(@IdRes int itemId) {
        switch (itemId) {
            case R.id.navTranslate:
                getViewState().showTranslate();
                return true;

            case R.id.navFavorite:
                getViewState().showFavorites();
                return true;

            case R.id.navHistory:
                getViewState().showHistory();
                return true;
        }
        return false;
    }
}
