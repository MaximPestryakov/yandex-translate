package me.maximpestryakov.yandextranslate.navigation;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import me.maximpestryakov.yandextranslate.R;
import me.maximpestryakov.yandextranslate.translate.TranslateFragment;

@InjectViewState
public class NavigationPresenter extends MvpPresenter<NavigationView> {

    NavigationPresenter() {
        TranslateFragment fragment = TranslateFragment.newInstance();
        getViewState().showTranslate();
    }

    boolean onNavigate(int itemId) {
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

            case R.id.navSettings:
                return false;
        }
        return false;
    }
}
