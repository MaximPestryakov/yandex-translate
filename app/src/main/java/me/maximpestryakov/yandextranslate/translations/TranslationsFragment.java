package me.maximpestryakov.yandextranslate.translations;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmQuery;
import me.maximpestryakov.yandextranslate.App;
import me.maximpestryakov.yandextranslate.R;
import me.maximpestryakov.yandextranslate.model.Translation;
import me.maximpestryakov.yandextranslate.navigation.NavigationActivity;

public class TranslationsFragment extends MvpAppCompatFragment implements TranslationsView {

    @InjectPresenter
    TranslationsPresenter translationsPresenter;

    @BindView(R.id.translationList)
    RecyclerView translationList;

    private Unbinder unbinder;

    private Realm realm;

    private Context context;

    private Boolean onlyFavorites;

    public static TranslationsFragment newInstance(boolean onlyFavorites) {
        Bundle args = new Bundle();
        args.putBoolean("only_favorites", onlyFavorites);
        TranslationsFragment fragment = new TranslationsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            onlyFavorites = args.getBoolean("only_favorites", false);
        }
        realm = Realm.getDefaultInstance();
        context = App.from(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);
        unbinder = ButterKnife.bind(this, view);


        OrderedRealmCollection<Translation> translations;
        RealmQuery<Translation> query = realm.where(Translation.class);
        if (onlyFavorites) {
            query.equalTo("favorite", true);
        }
        translations = query.findAll();


        TranslationsAdapter translationsAdapter = new TranslationsAdapter(translations, this::showTranslation);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        DividerItemDecoration decoration = new DividerItemDecoration(context, layoutManager.getOrientation());

        translationList.setHasFixedSize(true);
        translationList.setLayoutManager(layoutManager);
        translationList.addItemDecoration(decoration);
        translationList.setAdapter(translationsAdapter);

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

    public void showTranslation(String textToTranslate, String lang) {
        if (getActivity() instanceof NavigationActivity) {
            ((NavigationActivity) getActivity()).navigateToTranslate(textToTranslate, lang);
        }
    }
}
