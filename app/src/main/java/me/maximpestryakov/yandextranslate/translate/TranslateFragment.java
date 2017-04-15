package me.maximpestryakov.yandextranslate.translate;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.realm.Realm;
import me.maximpestryakov.yandextranslate.R;
import me.maximpestryakov.yandextranslate.languages.LanguagesActivity;
import me.maximpestryakov.yandextranslate.model.Language;
import me.maximpestryakov.yandextranslate.model.Translation;
import me.maximpestryakov.yandextranslate.util.FavoriteView;

import static android.app.Activity.RESULT_OK;


public class TranslateFragment extends MvpAppCompatFragment implements TranslateView {

    public static final int CHOOSE_FROM_LANG = 1;

    public static final int CHOOSE_TO_LANG = 2;

    @InjectPresenter
    TranslatePresenter translatePresenter;

    @BindView(R.id.textToTranslate)
    EditText textToTranslate;

    @BindView(R.id.translatedText)
    TextView translatedText;

    @BindView(R.id.favorite)
    FavoriteView favorite;

    @BindView(R.id.translateToolbar)
    Toolbar translateToolbar;

    @BindView(R.id.swapLang)
    ImageView swapLang;

    @BindView(R.id.fromLang)
    TextView fromLang;

    @BindView(R.id.toLang)
    TextView toLang;

    @BindView(R.id.clearText)
    ImageView clearText;

    private Unbinder unbinder;

    private Realm realm;

    private Language from;

    private Language to;

    public static TranslateFragment newInstance() {
        return new TranslateFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realm = Realm.getDefaultInstance();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_translate, container, false);
        unbinder = ButterKnife.bind(this, view);

        RxTextView.textChanges(textToTranslate)
                .map(CharSequence::toString)
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .debounce(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> translatePresenter.onTranslate(s, from.getCode(), to.getCode()));

        fromLang.setOnClickListener(v -> {
            Intent intent = LanguagesActivity.getStartIntent(getActivity(), from.getCode());
            startActivityForResult(intent, CHOOSE_FROM_LANG);
        });

        toLang.setOnClickListener(v -> {
            Intent intent = LanguagesActivity.getStartIntent(getActivity(), to.getCode());
            startActivityForResult(intent, CHOOSE_TO_LANG);
        });

        swapLang.setOnClickListener(v -> {
            String from = this.from.getCode();

            float fromLangX = fromLang.getX();
            float toLangX = toLang.getX();
            float newToLangX = fromLangX + fromLang.getWidth() - toLang.getWidth();

            fromLang.animate().x(toLangX).setDuration(250).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    fromLang.setClickable(false);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    fromLang.setX(fromLangX);
                    translatePresenter.onChoseFromLang(to.getCode());
                    fromLang.setClickable(true);
                }
            });

            toLang.animate().x(newToLangX).setDuration(250).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    toLang.setClickable(false);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    toLang.setX(toLangX);
                    translatePresenter.onChoseToLang(from);
                    toLang.setClickable(true);
                }
            });

            swapLang.animate().rotationBy(180).setDuration(250).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    swapLang.setClickable(false);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    swapLang.setClickable(true);
                }
            });
        });

        RxTextView.textChanges(textToTranslate)
                .map(CharSequence::toString)
                .map(String::trim)
                .subscribe(text -> {
                    if (text.isEmpty()) {
                        clearText.setVisibility(View.GONE);
                        favorite.setVisibility(View.GONE);
                    } else {
                        clearText.setVisibility(View.VISIBLE);
                        favorite.setVisibility(View.VISIBLE);
                    }
                });

        clearText.setOnClickListener(v -> translatePresenter.onClickClear());

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == CHOOSE_FROM_LANG) {
            String chosenLang = data.getStringExtra(LanguagesActivity.CHOSEN_LANG);
            translatePresenter.onChoseFromLang(chosenLang);
        } else if (requestCode == CHOOSE_TO_LANG) {
            String chosenLang = data.getStringExtra(LanguagesActivity.CHOSEN_LANG);
            translatePresenter.onChoseToLang(chosenLang);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    @Override
    public void showTranslation(Translation translation) {
        translatedText.setText(translation.getText().get(0).toString());
        favorite.setOnClickListener(v -> realm.executeTransaction(realm -> {
            translation.setFavorite(favorite.isChecked());
            realm.copyToRealmOrUpdate(translation);
        }));
        favorite.setChecked(translation.isFavorite());
    }

    @Override
    public void setFromLang(String from) {
        this.from = realm.where(Language.class).equalTo("code", from).findFirst();
        fromLang.setText(this.from.getTitle());
    }

    @Override
    public void setToLang(String to) {
        this.to = realm.where(Language.class).equalTo("code", to).findFirst();
        toLang.setText(this.to.getTitle());
    }

    @Override
    public void showTextToTranslate(String text) {
        textToTranslate.setText(text);
        textToTranslate.setSelection(textToTranslate.length());
    }

    @Override
    public void clear() {
        textToTranslate.setText("");
        translatedText.setText("");
        favorite.setChecked(false);
    }

    @Override
    public void showError(@StringRes int resId) {
    }

    public void setTextToTranslate(String textToTranslate, String langs) {
        int del = langs.indexOf('-');
        String from = langs.substring(0, del);
        String to = langs.substring(del + 1);
        translatePresenter.onChoseFromLang(from);
        translatePresenter.onChoseToLang(to);
        translatePresenter.onTranslate(textToTranslate, from, to);
        translatePresenter.onSetTextToTranslate(textToTranslate);
    }
}
