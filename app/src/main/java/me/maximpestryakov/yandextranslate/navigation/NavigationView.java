package me.maximpestryakov.yandextranslate.navigation;

import android.support.v4.app.Fragment;

import com.arellomobile.mvp.MvpView;

public interface NavigationView extends MvpView {

    void showFragment(Fragment fragment);
}
