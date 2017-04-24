package me.maximpestryakov.yandextranslate.translations;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import me.maximpestryakov.yandextranslate.model.Translation;

@InjectViewState
public class TranslationsPresenter extends MvpPresenter<TranslationsView> {

    void onEmptyList() {
        getViewState().showEmptyMessage();
    }

    void onNotEmptyList() {
        getViewState().showList();
    }

    void onDeleteAll(boolean onlyFavorites) {
        try (Realm realm = Realm.getDefaultInstance()) {
            RealmQuery<Translation> query = realm.where(Translation.class);
            if (onlyFavorites) {
                query.equalTo("favorite", true);
            } else {
                query.equalTo("cached", true);
            }
            List<Translation> translations = query.findAll();
            realm.executeTransaction(r -> {
                for (Translation translation : translations) {
                    if (onlyFavorites) {
                        translation.setFavorite(false);
                    } else {
                        translation.setCached(false);
                    }
                }
            });
        }
        getViewState().showEmptyMessage();
    }
}
