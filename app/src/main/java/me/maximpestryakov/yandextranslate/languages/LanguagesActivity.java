package me.maximpestryakov.yandextranslate.languages;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.arellomobile.mvp.MvpAppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import me.maximpestryakov.yandextranslate.App;
import me.maximpestryakov.yandextranslate.R;
import me.maximpestryakov.yandextranslate.model.Language;

public class LanguagesActivity extends MvpAppCompatActivity implements LanguagesView {

    public static final String CURRENT_LANG = "current_lang";
    public static final String CHOSEN_LANG = "chosen_lang";

    @BindView(R.id.languagesToolbar)
    Toolbar languagesToolbar;

    @BindView(R.id.languageList)
    RecyclerView languageList;

    private Context context;

    private Realm realm;

    private LanguagesAdapter translationsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_languages);
        ButterKnife.bind(this);

        context = App.from(this);
        realm = Realm.getDefaultInstance();

        String currentLang = getIntent().getStringExtra(CURRENT_LANG);

        setSupportActionBar(languagesToolbar);
        setTitle("Язык теста");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        translationsAdapter = new LanguagesAdapter(realm.where(Language.class).findAll(), currentLang);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        DividerItemDecoration decoration = new DividerItemDecoration(context, layoutManager.getOrientation());

        languageList.setHasFixedSize(true);
        languageList.setLayoutManager(layoutManager);
        languageList.addItemDecoration(decoration);
        languageList.setAdapter(translationsAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    @Override
    public void onBackPressed() {
        chooseLang();
    }

    @Override
    public boolean onSupportNavigateUp() {
        chooseLang();
        return true;
    }

    void chooseLang() {
        Intent intent = new Intent();
        intent.putExtra(CHOSEN_LANG, translationsAdapter.getCurrentLang());
        setResult(RESULT_OK, intent);
        finish();
    }
}
