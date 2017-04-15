package me.maximpestryakov.yandextranslate.translate;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.net.UnknownHostException;

import io.realm.Realm;
import me.maximpestryakov.yandextranslate.R;
import me.maximpestryakov.yandextranslate.api.ApiManager;
import me.maximpestryakov.yandextranslate.api.YandexTranslateApi;
import me.maximpestryakov.yandextranslate.model.Translation;
import me.maximpestryakov.yandextranslate.util.Callback;

@InjectViewState
public class TranslatePresenter extends MvpPresenter<TranslateView> {

    private YandexTranslateApi api;

    TranslatePresenter() {
        api = ApiManager.getApi();
        onChoseFromLang("en");
        onChoseToLang("ru");
    }

    void onTranslate(String textToTranslate, String from, String to) {
        if (textToTranslate == null || textToTranslate.isEmpty()) {
            return;
        }
        Translation translation;
        try (Realm realm = Realm.getDefaultInstance()) {
            translation = realm.where(Translation.class)
                    .equalTo("original", textToTranslate)
                    .equalTo("lang", from + "-" + to)
                    .findFirst();
            if (translation != null) {
                getViewState().showTranslation(translation);
            }
            api.translate(textToTranslate, from + "-" + to, "plain").enqueue(new Callback<>((call, response) -> {
                Translation newTranslation = response.body();

                newTranslation.setOriginal(textToTranslate);
                realm.executeTransaction(r -> {
                    if (translation != null) {
                        newTranslation.setId(translation.getId());
                        newTranslation.setFavorite(translation.isFavorite());
                        r.copyToRealmOrUpdate(newTranslation);
                    } else {
                        Number currentId = r.where(Translation.class).max("id");
                        if (currentId == null) {
                            newTranslation.setId(1);
                        } else {
                            newTranslation.setId(currentId.intValue() + 1);
                        }
                        r.copyToRealm(newTranslation);
                    }
                });
                getViewState().showTranslation(newTranslation);
            }, (call, t) -> {
                if (t instanceof UnknownHostException) {
                    getViewState().showError(R.string.no_internet);
                }
            }));
        }
    }

    void onChoseFromLang(String from) {
        getViewState().setFromLang(from);
    }

    void onChoseToLang(String to) {
        getViewState().setToLang(to);
    }

    void onSetTextToTranslate(String textToTranslate) {
        getViewState().showTextToTranslate(textToTranslate);
    }

    void onClickClear() {
        getViewState().clear();
    }
}
