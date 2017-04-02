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
import io.realm.Realm;
import io.realm.RealmQuery;
import me.maximpestryakov.yandextranslate.App;
import me.maximpestryakov.yandextranslate.R;
import me.maximpestryakov.yandextranslate.model.Translation;

public class TranslationsFragment extends MvpAppCompatFragment implements TranslationsView {

    @InjectPresenter
    TranslationsPresenter translationsPresenter;

    @BindView(R.id.translationList)
    RecyclerView translationList;

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
        context = App.from(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        RealmQuery<Translation> query = realm.where(Translation.class);
        if (onlyFavorites) {
            query.equalTo("favorite", true);
        }
        TranslationsAdapter translationsAdapter = new TranslationsAdapter(query.findAll(), v -> {

        });
        realm.commitTransaction();

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        DividerItemDecoration decoration = new DividerItemDecoration(context, layoutManager.getOrientation());

        translationList.setHasFixedSize(true);
        translationList.setLayoutManager(layoutManager);
        translationList.addItemDecoration(decoration);
        translationList.setAdapter(translationsAdapter);
    }
}
