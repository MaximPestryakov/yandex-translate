package me.maximpestryakov.yandextranslate.navigation;

import android.support.v4.app.Fragment;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import me.maximpestryakov.yandextranslate.R;
import me.maximpestryakov.yandextranslate.favorites.FavoritesFragment;
import me.maximpestryakov.yandextranslate.translate.TranslateFragment;

@InjectViewState
public class NavigationPresenter extends MvpPresenter<NavigationView> {

    NavigationPresenter() {
        TranslateFragment fragment = TranslateFragment.newInstance();
        getViewState().showFragment(fragment);
    }

    boolean onNavigate(int itemId) {
        Fragment fragment;
        switch (itemId) {
            case R.id.navTranslate:
                fragment = TranslateFragment.newInstance();
                getViewState().showFragment(fragment);
                return true;

            case R.id.navFavorite:
                fragment = FavoritesFragment.newInstance();
                getViewState().showFragment(fragment);
                return true;

            case R.id.navHistory:
                return false;

            case R.id.navSettings:
                return false;
        }
        return false;
    }
}
