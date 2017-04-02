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
        Translation translation;

        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        translation = realm.where(Translation.class).equalTo("original", textToTranslate).findFirst();
        if (translation != null) {
            getViewState().showTranslation(translation);
        }
        realm.commitTransaction();

        api.translate(textToTranslate, "en-ru", "plain").enqueue(new Callback<>((call, response) -> {
            Translation newTranslation = response.body();
            newTranslation.setOriginal(textToTranslate);
            if (translation != null) {
                newTranslation.setFavorite(translation.isFavorite());
            }
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(newTranslation);
            realm.commitTransaction();
            getViewState().showTranslation(newTranslation);
        }, (call, t) -> {
            // show error
        }));
    }
}
