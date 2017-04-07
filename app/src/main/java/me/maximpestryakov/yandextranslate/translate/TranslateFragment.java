package me.maximpestryakov.yandextranslate.translate;

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

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import me.maximpestryakov.yandextranslate.R;
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

    private Realm realm;

    private String textToTranslateValue;

    public static TranslateFragment newInstance() {
        return new TranslateFragment();
    }

    public static TranslateFragment newInstance(String text) {
        Bundle args = new Bundle();
        args.putString("text", text);

        TranslateFragment fragment = new TranslateFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            textToTranslateValue = args.getString("text", null);
        }
        realm = Realm.getDefaultInstance();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_translate, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

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
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    @Override
    public void showTranslation(Translation translation) {
        translatedText.setText(translation.getText().get(0).getValue());
        favorite.setOnClickListener(v -> realm.executeTransaction(realm -> {
            translation.setFavorite(favorite.isChecked());
            realm.copyToRealmOrUpdate(translation);
        }));
        favorite.setChecked(translation.isFavorite());
    }
}
