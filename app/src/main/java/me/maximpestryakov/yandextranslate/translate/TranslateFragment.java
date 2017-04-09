package me.maximpestryakov.yandextranslate.translate;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.realm.Realm;
import me.maximpestryakov.yandextranslate.R;
import me.maximpestryakov.yandextranslate.languages.LanguagesActivity;
import me.maximpestryakov.yandextranslate.model.Language;
import me.maximpestryakov.yandextranslate.model.Translation;

import static android.app.Activity.RESULT_OK;


public class TranslateFragment extends MvpAppCompatFragment implements TranslateView {

    public static final int CHOOSE_FROM_LANG = 1;

    public static final int CHOOSE_TO_LANG = 2;

    @InjectPresenter
    TranslatePresenter translatePresenter;

    @BindView(R.id.textToTranslate)
    EditText textToTranslate;

    @BindView(R.id.doTranslate)
    Button doTranslate;

    @BindView(R.id.translatedText)
    TextView translatedText;

    @BindView(R.id.favorite)
    CheckBox favorite;

    @BindView(R.id.swapLang)
    ImageView swapLang;

    @BindView(R.id.fromLang)
    TextView fromLang;

    @BindView(R.id.toLang)
    TextView toLang;

    private Unbinder unbinder;

    private Realm realm;

    private String textToTranslateValue;

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

        doTranslate.setOnClickListener(v -> translatePresenter.onTranslate(
                textToTranslate.getText().toString(), from.getCode(), to.getCode()));

        if (textToTranslateValue != null && !textToTranslateValue.isEmpty()) {
            textToTranslate.setText(textToTranslateValue);
            translatePresenter.onTranslate(textToTranslateValue, from.getCode(), to.getCode());
        }

        fromLang.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), LanguagesActivity.class);
            intent.putExtra(LanguagesActivity.CURRENT_LANG, from.getCode());
            startActivityForResult(intent, CHOOSE_FROM_LANG);
        });

        toLang.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), LanguagesActivity.class);
            intent.putExtra(LanguagesActivity.CURRENT_LANG, to.getCode());
            startActivityForResult(intent, CHOOSE_TO_LANG);
        });

        swapLang.setOnClickListener(v -> {
            String from = this.from.getCode();
            translatePresenter.onChoseFromLang(to.getCode());
            translatePresenter.onChoseToLang(from);
            RotateAnimation animation = new RotateAnimation(0, 180, swapLang.getWidth() / 2, swapLang.getHeight() / 2);
            animation.setDuration(100);
            swapLang.startAnimation(animation);
        });

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
        textToTranslate.setText(translation.getOriginal());
        translatedText.setText(translation.getText().get(0).toString());
        favorite.setOnClickListener(v -> realm.executeTransaction(realm -> {
            translation.setFavorite(favorite.isChecked());
            realm.copyToRealmOrUpdate(translation);
        }));
        favorite.setChecked(translation.isFavorite());
    }

    @Override
    public void showLangs(List<Language> languages) {
        List<String> languageStrings = new ArrayList<>();
        for (Language language : languages) {
            languageStrings.add(language.getTitle());
        }
        Collections.sort(languageStrings);
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

    public void setTextToTranslate(String textToTranslate) {
        translatePresenter.onTranslate(textToTranslate, from.getCode(), to.getCode());
    }
}
