package me.maximpestryakov.yandextranslate.languages;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import me.maximpestryakov.yandextranslate.R;
import me.maximpestryakov.yandextranslate.model.Language;

public class LanguagesActivity extends AppCompatActivity {

    public static final String CHOSEN_LANG = "CHOSEN_LANG";

    private static final String EXTRA_CURRENT_LANG = "EXTRA_CURRENT_LANG";

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.languageList)
    RecyclerView languageList;

    private Realm realm;

    private LanguagesAdapter translationsAdapter;

    public static Intent getStartIntent(Context context, String currentLang) {
        Intent intent = new Intent(context, LanguagesActivity.class);
        intent.putExtra(EXTRA_CURRENT_LANG, currentLang);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_languages);
        ButterKnife.bind(this);

        realm = Realm.getDefaultInstance();

        String currentLang = getIntent().getStringExtra(EXTRA_CURRENT_LANG);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.select_language);
        }

        translationsAdapter = new LanguagesAdapter(realm.where(Language.class).findAllSorted("title"), currentLang);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        DividerItemDecoration decoration = new DividerItemDecoration(this, layoutManager.getOrientation());

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
