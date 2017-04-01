package me.maximpestryakov.yandextranslate.translations;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.maximpestryakov.yandextranslate.R;
import me.maximpestryakov.yandextranslate.model.Translation;

public class TranslationsFragment extends MvpAppCompatFragment implements TranslationsView {

    @InjectPresenter
    TranslationsPresenter translationsPresenter;

    @BindView(R.id.favoriteList)
    RecyclerView favoriteList;

    private TranslationsAdapter translationsAdapter;
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
            onlyFavorites = args.getBoolean("only_favorites");
        }
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

        translationsAdapter = new TranslationsAdapter(onlyFavorites, v -> {

        });

        favoriteList.setHasFixedSize(true);
        favoriteList.setLayoutManager(new LinearLayoutManager(getActivity()));
        favoriteList.setAdapter(translationsAdapter);
    }

    @Override
    public void showFavorites(List<Translation> favorites) {
        // translationsAdapter.setTranslations(favorites);
    }
}
