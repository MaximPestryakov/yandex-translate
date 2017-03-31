package me.maximpestryakov.yandextranslate.favorites;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.maximpestryakov.yandextranslate.R;
import me.maximpestryakov.yandextranslate.model.Translation;

public class FavoritesFragment extends MvpAppCompatFragment implements FavoritesView {

    @BindView(R.id.favoriteList)
    RecyclerView favoriteList;

    FavoritesAdapter favoritesAdapter;

    public static FavoritesFragment newInstance() {
        return new FavoritesFragment();
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

        favoritesAdapter = new FavoritesAdapter(v -> {

        });

        favoriteList.setHasFixedSize(true);
        favoriteList.setLayoutManager(new LinearLayoutManager(getActivity()));
        favoriteList.setAdapter(favoritesAdapter);
    }

    @Override
    public void showFavorites(List<Translation> favorites) {
        favoritesAdapter.setFavorites(favorites);
    }
}
