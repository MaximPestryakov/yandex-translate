package me.maximpestryakov.yandextranslate.translate;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import me.maximpestryakov.yandextranslate.api.ApiManager;
import me.maximpestryakov.yandextranslate.api.YandexTranslateApi;
import me.maximpestryakov.yandextranslate.model.Translation;
import me.maximpestryakov.yandextranslate.util.Callback;

@InjectViewState
public class TranslatePresenter extends MvpPresenter<TranslateView> {

    private YandexTranslateApi api;

    public TranslatePresenter() {
        api = ApiManager.getApi();
    }

    void onTranslate(String textToTranslate) {
        api.translate(textToTranslate, "en-ru", "plain").enqueue(new Callback<>(
                (call, response) -> {
                    Translation translation = response.body();
                    getViewState().showTranslation(translation.getText().get(0));
                },
                (call, t) -> {
                    // show error
                }
        ));
    }
}
