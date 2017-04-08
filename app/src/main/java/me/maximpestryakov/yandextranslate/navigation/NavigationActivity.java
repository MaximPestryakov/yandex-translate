package me.maximpestryakov.yandextranslate.navigation;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.maximpestryakov.yandextranslate.R;
import me.maximpestryakov.yandextranslate.translate.TranslateFragment;
import me.maximpestryakov.yandextranslate.translations.TranslationsFragment;

public class NavigationActivity extends MvpAppCompatActivity implements NavigationView {

    private static final String TRANSLATE_TAG = "translate";
    private static final String HISTORY_TAG = "history";
    private static final String FAVORITES_TAG = "favorites";

    @InjectPresenter
    NavigationPresenter navigationPresenter;

    @BindView(R.id.bottomNav)
    BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        ButterKnife.bind(this);

        bottomNav.setOnNavigationItemSelectedListener(item -> navigationPresenter.onNavigate(item.getItemId()));
    }

    @Override
    public void onBackPressed() {
        if (bottomNav.getSelectedItemId() == R.id.navTranslate) {
            finish();
        } else {
            bottomNav.setSelectedItemId(R.id.navTranslate);
        }
    }

    @Override
    public void showTranslate() {
        bottomNav.getMenu().findItem(R.id.navTranslate).setChecked(true);
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(TRANSLATE_TAG);
        if (fragment == null) {
            fragment = TranslateFragment.newInstance();
            showFragment(fragment, TRANSLATE_TAG, true);
        } else {
            showFragment(fragment, TRANSLATE_TAG, false);
        }
    }

    @Override
    public void showFavorites() {
        bottomNav.getMenu().findItem(R.id.navFavorite).setChecked(true);
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(FAVORITES_TAG);
        if (fragment == null) {
            fragment = TranslationsFragment.newInstance(true);
            showFragment(fragment, FAVORITES_TAG, true);
        } else {
            showFragment(fragment, FAVORITES_TAG, false);
        }
    }

    @Override
    public void showHistory() {
        bottomNav.getMenu().findItem(R.id.navHistory).setChecked(true);
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(HISTORY_TAG);
        if (fragment == null) {
            fragment = TranslationsFragment.newInstance(false);
            showFragment(fragment, HISTORY_TAG, true);
        } else {
            showFragment(fragment, HISTORY_TAG, false);
        }
    }

    public void showFragment(Fragment fragment, String tag, boolean addToBackStack) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.mainFragment, fragment, tag);
        if (addToBackStack) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }

    public void navigateToTranslate(String textToTranslate) {
        navigationPresenter.onNavigate(R.id.navTranslate);
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(TRANSLATE_TAG);
        ((TranslateFragment) fragment).setTextToTranslate(textToTranslate);
    }
}
