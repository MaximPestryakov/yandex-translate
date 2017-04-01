package me.maximpestryakov.yandextranslate.navigation;

import android.support.v4.app.Fragment;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import me.maximpestryakov.yandextranslate.R;
import me.maximpestryakov.yandextranslate.translate.TranslateFragment;
import me.maximpestryakov.yandextranslate.translations.TranslationsFragment;

@InjectViewState
public class NavigationPresenter extends MvpPresenter<NavigationView> {

    NavigationPresenter() {
        TranslateFragment fragment = TranslateFragment.newInstance();
        getViewState().showFragment(fragment, "fragment_translate");
    }

    boolean onNavigate(int itemId) {
        Fragment fragment;
        switch (itemId) {
            case R.id.navTranslate:
                fragment = TranslateFragment.newInstance();
                getViewState().showFragment(fragment, "fragment_translate");
                return true;

            case R.id.navFavorite:
                fragment = TranslationsFragment.newInstance(true);
                getViewState().showFragment(fragment, "fragment_favorite");
                return true;

            case R.id.navHistory:
                fragment = TranslationsFragment.newInstance(false);
                getViewState().showFragment(fragment, "fragment_history");
                return true;

            case R.id.navSettings:
                return false;
        }
        return false;
    }
}
