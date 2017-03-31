package me.maximpestryakov.yandextranslate;

import android.app.Application;
import android.content.Context;

public class App extends Application {

    public static App from(Context context) {
        return (App) context.getApplicationContext();
    }
}
