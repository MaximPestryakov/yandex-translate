package me.maximpestryakov.yandextranslate;

import android.app.Application;
import android.content.Context;

import io.realm.Realm;
import me.maximpestryakov.yandextranslate.api.ApiManager;

public class App extends Application {

    public static App from(Context context) {
        return (App) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
        ApiManager.init();
    }
}
