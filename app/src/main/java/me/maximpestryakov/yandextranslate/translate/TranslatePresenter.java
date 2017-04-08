package me.maximpestryakov.yandextranslate.translate;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import io.realm.Realm;
import me.maximpestryakov.yandextranslate.api.ApiManager;
import me.maximpestryakov.yandextranslate.api.YandexTranslateApi;
import me.maximpestryakov.yandextranslate.model.DirsLangs;
import me.maximpestryakov.yandextranslate.model.Translation;
import me.maximpestryakov.yandextranslate.util.Callback;

@InjectViewState
public class TranslatePresenter extends MvpPresenter<TranslateView> {

    private YandexTranslateApi api;

    TranslatePresenter() {
        api = ApiManager.getApi();
    }

    void onTranslate(String textToTranslate) {
        if (textToTranslate == null || textToTranslate.isEmpty()) {
            return;
        }
        Translation translation;
        try (Realm realm = Realm.getDefaultInstance()) {
            translation = realm.where(Translation.class)
                    .equalTo("original", textToTranslate).findFirst();
            if (translation != null) {
                getViewState().showTranslation(translation);
            }
            api.translate(textToTranslate, "en-ru", "plain").enqueue(new Callback<>((call, response) -> {
                Translation newTranslation = response.body();
                newTranslation.setOriginal(textToTranslate);
                if (translation != null) {
                    newTranslation.setFavorite(translation.isFavorite());
                }
                realm.executeTransaction(r -> r.copyToRealmOrUpdate(newTranslation));
                getViewState().showTranslation(newTranslation);
            }, (call, t) -> {

            }));
        }
    }

    void loadLangs() {
        api.getLangs("ru").enqueue(new Callback<>((call, response) -> {
            DirsLangs dirsLangs = response.body();

        }, (call, t) -> {

        }));
    }
}
