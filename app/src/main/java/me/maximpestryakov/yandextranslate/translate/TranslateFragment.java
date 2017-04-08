package me.maximpestryakov.yandextranslate.translate;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.realm.Realm;
import me.maximpestryakov.yandextranslate.R;
import me.maximpestryakov.yandextranslate.model.Language;
import me.maximpestryakov.yandextranslate.model.Translation;


public class TranslateFragment extends MvpAppCompatFragment implements TranslateView {

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

    @BindView(R.id.langFrom)
    Spinner langFrom;

    @BindView(R.id.langTo)
    Spinner langTo;

    private Unbinder unbinder;

    private Realm realm;

    private String textToTranslateValue;

    private ArrayAdapter<String> languagesAdapter;

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

        doTranslate.setOnClickListener(v -> translatePresenter.onTranslate(textToTranslate.getText().toString()));
        if (textToTranslateValue != null && !textToTranslateValue.isEmpty()) {
            textToTranslate.setText(textToTranslateValue);
            translatePresenter.onTranslate(textToTranslateValue);
        }

        swapLang.setOnClickListener(v -> {
            RotateAnimation animation = new RotateAnimation(0, 180, swapLang.getWidth() / 2, swapLang.getHeight() / 2);
            animation.setDuration(100);
            swapLang.startAnimation(animation);
        });
        languagesAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_dropdown_item);
        langFrom.setAdapter(languagesAdapter);
        langTo.setAdapter(languagesAdapter);

        return view;
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
        languagesAdapter.clear();
        languagesAdapter.addAll(languageStrings);
    }

    public void setTextToTranslate(String textToTranslate) {
        translatePresenter.onTranslate(textToTranslate);
    }
}
