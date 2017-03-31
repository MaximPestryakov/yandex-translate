package me.maximpestryakov.yandextranslate.translate;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.maximpestryakov.yandextranslate.R;


public class TranslateFragment extends MvpAppCompatFragment implements TranslateView {

    @InjectPresenter
    TranslatePresenter translatePresenter;

    @BindView(R.id.textToTranslate)
    EditText textToTranslate;

    @BindView(R.id.doTranslate)
    Button doTranslate;

    @BindView(R.id.translatedText)
    TextView translatedText;

    private String textToTranslateValue;

    public static TranslateFragment newInstance() {
        Bundle args = new Bundle();
        args.putString("text", "");

        TranslateFragment fragment = new TranslateFragment();
        fragment.setArguments(args);
        return fragment;
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
            textToTranslateValue = args.getString("text");
        }
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
    }

    @Override
    public void showTranslation(String translation) {
        translatedText.setText(translation);
    }
}
