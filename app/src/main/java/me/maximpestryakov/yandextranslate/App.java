package me.maximpestryakov.yandextranslate;

import android.app.Application;
import android.content.Context;

import me.maximpestryakov.yandextranslate.api.ApiManager;

public class App extends Application {

    public static App from(Context context) {
        return (App) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        ApiManager.init();
    }
}
