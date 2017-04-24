package me.maximpestryakov.yandextranslate;

import android.app.Application;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

import com.facebook.stetho.Stetho;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import io.realm.Realm;
import me.maximpestryakov.yandextranslate.api.ApiManager;
import me.maximpestryakov.yandextranslate.api.YandexTranslateApi;
import me.maximpestryakov.yandextranslate.model.DirsLangs;
import me.maximpestryakov.yandextranslate.util.Callback;
import me.maximpestryakov.yandextranslate.util.ConnectivityReceiver;
import me.maximpestryakov.yandextranslate.util.DirsLangsDeserializer;
import me.maximpestryakov.yandextranslate.util.RealmString;
import me.maximpestryakov.yandextranslate.util.RealmStringDeserializer;

public class App extends Application {

    private static Gson gson;
    private YandexTranslateApi api;

    public static App from(Context context) {
        return (App) context.getApplicationContext();
    }

    public static Gson getGson() {
        return gson;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);

        gson = new GsonBuilder()
                .registerTypeAdapter(RealmString.class, new RealmStringDeserializer())
                .registerTypeAdapter(DirsLangs.class, new DirsLangsDeserializer())
                .create();

        ApiManager.init();

        if (BuildConfig.DEBUG) {
            Stetho.initialize(Stetho.newInitializerBuilder(this)
                    .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                    .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                    .build());
        }

        api = ApiManager.getApi();

        loadLangs();
    }

    void loadLangs() {
        try {
            InputStream inputStream = getAssets().open(getString(R.string.langs_json));
            Reader reader = new BufferedReader(new InputStreamReader(inputStream));
            DirsLangs dirsLangs = gson.fromJson(reader, DirsLangs.class);
            try (Realm realm = Realm.getDefaultInstance()) {
                realm.executeTransaction(r -> {
                    r.copyToRealmOrUpdate(dirsLangs.getLanguages());
                    r.copyToRealmOrUpdate(dirsLangs.getDirections());
                });
            }
        } catch (IOException e) {
            // No json file :(
        }

        api.getLangs(getString(R.string.lang_code)).enqueue(new Callback<>((call, response) -> {
            DirsLangs dirsLangs = response.body();
            try (Realm realm = Realm.getDefaultInstance()) {
                realm.executeTransaction(r -> {
                    r.copyToRealmOrUpdate(dirsLangs.getLanguages());
                    r.copyToRealmOrUpdate(dirsLangs.getDirections());
                });
            }
        }, (call, t) -> {

        }));

        registerReceiver(new ConnectivityReceiver(),
                new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }
}
