package me.maximpestryakov.yandextranslate.navigation;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import me.maximpestryakov.yandextranslate.R;
import me.maximpestryakov.yandextranslate.translate.TranslateFragment;

@InjectViewState
public class NavigationPresenter extends MvpPresenter<NavigationView> {

    NavigationPresenter() {
        TranslateFragment fragment = TranslateFragment.newInstance();
        getViewState().showFragment(fragment);
    }

    void onNavigate(int itemId) {
        switch (itemId) {
            case R.id.navTranslate:
                TranslateFragment fragment = TranslateFragment.newInstance();
                getViewState().showFragment(fragment);
                break;

            case R.id.navFavorite:
                break;

            case R.id.navHistory:
                break;

            case R.id.navSettings:
                break;
        }
    }
}
