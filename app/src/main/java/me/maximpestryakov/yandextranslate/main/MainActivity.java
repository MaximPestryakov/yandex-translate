package me.maximpestryakov.yandextranslate.main;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.maximpestryakov.yandextranslate.R;

public class MainActivity extends MvpAppCompatActivity implements MainView {

    @InjectPresenter
    MainPresenter mainPresenter;

    @BindView(R.id.textToTranslate)
    EditText textToTranslate;

    @BindView(R.id.doTranslate)
    Button doTranslate;

    @BindView(R.id.translatedText)
    TextView translatedText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        doTranslate.setOnClickListener(v -> mainPresenter.onTranslate(textToTranslate.getText().toString()));
    }

    @Override
    public void showTranslation(String translation) {
        translatedText.setText(translation);
    }
}
