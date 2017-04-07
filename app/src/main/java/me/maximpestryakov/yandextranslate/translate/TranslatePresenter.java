package me.maximpestryakov.yandextranslate.translate;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import io.realm.Realm;
import me.maximpestryakov.yandextranslate.api.ApiManager;
import me.maximpestryakov.yandextranslate.api.YandexTranslateApi;
import me.maximpestryakov.yandextranslate.model.Translation;
import me.maximpestryakov.yandextranslate.util.Callback;

@InjectViewState
public class TranslatePresenter extends MvpPresenter<TranslateView> {

    private YandexTranslateApi api;

    TranslatePresenter() {
        api = ApiManager.getApi();
    }

    void onTranslate(String textToTranslate) {
        try (Realm r = Realm.getDefaultInstance()) {
            r.executeTransaction(realm -> {
                Translation translation = realm.where(Translation.class)
                        .equalTo("original", textToTranslate).findFirst();
                if (translation != null) {
                    getViewState().showTranslation(translation);
                }
                api.translate(textToTranslate, "en-ru", "plain").enqueue(new Callback<>((call, response) -> {
                    Translation newTranslation = response.body();
                    if (translation != null) {
                        newTranslation.setFavorite(translation.isFavorite());
                        realm.copyToRealmOrUpdate(newTranslation);
                        getViewState().showTranslation(newTranslation);
                    }
                }, (call, t) -> {

                }));
            });
        }
    }
}
