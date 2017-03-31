package me.maximpestryakov.yandextranslate.navigation;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.maximpestryakov.yandextranslate.R;

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
    public void showFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.mainFragment, fragment)
                .commit();
    }
}
