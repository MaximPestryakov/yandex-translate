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
        navigationPresenter.onNavigate(R.id.navTranslate);
    }

    @Override
    public void showTranslate() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag("fragment_translate");
        if (fragment == null) {
            fragment = TranslateFragment.newInstance();
            showFragment(fragment, "fragment_translate", true);
        } else {
            showFragment(fragment, "fragment_translate", false);
        }
    }

    @Override
    public void showFavorites() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag("fragment_favorites");
        if (fragment == null) {
            fragment = TranslationsFragment.newInstance(true);
            showFragment(fragment, "fragment_favorites", true);
        } else {
            showFragment(fragment, "fragment_favorites", false);
        }
    }

    @Override
    public void showHistory() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag("fragment_history");
        if (fragment == null) {
            fragment = TranslationsFragment.newInstance(false);
            showFragment(fragment, "fragment_history", true);
        } else {
            showFragment(fragment, "fragment_history", false);
        }
    }

    public void showFragment(Fragment fragment, String tag, boolean addToBackStack) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.mainFragment, fragment, tag);
        if (addToBackStack) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }
}
