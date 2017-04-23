package me.maximpestryakov.yandextranslate.translations;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmQuery;
import me.maximpestryakov.yandextranslate.R;
import me.maximpestryakov.yandextranslate.model.Translation;
import me.maximpestryakov.yandextranslate.navigation.NavigationActivity;

public class TranslationsFragment extends MvpAppCompatFragment implements TranslationsView {

    private static final String ARGUMENT_ONLY_FAVORITES = "ARGUMENT_ONLY_FAVORITES";

    @InjectPresenter
    TranslationsPresenter translationsPresenter;

    @BindView(R.id.translationList)
    RecyclerView translationList;

    @BindView(R.id.emptyImage)
    ImageView emptyImage;

    @BindView(R.id.emptyText)
    TextView emptyText;

    private Unbinder unbinder;

    private Realm realm;

    private Boolean onlyFavorites;

    public static TranslationsFragment newInstance(boolean onlyFavorites) {
        Bundle args = new Bundle();
        args.putBoolean(ARGUMENT_ONLY_FAVORITES, onlyFavorites);
        TranslationsFragment fragment = new TranslationsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            onlyFavorites = args.getBoolean(ARGUMENT_ONLY_FAVORITES, false);
        }
        realm = Realm.getDefaultInstance();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_translations, container, false);
        unbinder = ButterKnife.bind(this, view);


        OrderedRealmCollection<Translation> translations;
        RealmQuery<Translation> query = realm.where(Translation.class);
        if (onlyFavorites) {
            query.equalTo("favorite", true);
        }
        translations = query.findAll();


        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        DividerItemDecoration decoration = new DividerItemDecoration(getActivity(), layoutManager.getOrientation());
        TranslationsAdapter translationsAdapter = new TranslationsAdapter(translations, this::showTranslation);
        translationsAdapter.setOnItemCountChangeListener(itemCount -> {
            if (itemCount == 0) {
                translationsPresenter.onEmptyList();
            } else {
                translationsPresenter.onNotEmptyList();
            }
        });

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

    @Override
    public void showList() {
        emptyImage.setVisibility(View.GONE);
        emptyText.setVisibility(View.GONE);
        translationList.setVisibility(View.VISIBLE);
    }

    @Override
    public void showEmptyMessage() {
        if (onlyFavorites) {
            emptyImage.setImageResource(R.drawable.ic_bookmark_border_black_24dp);
            emptyText.setText(R.string.empty_favorites);
        } else {
            emptyImage.setImageResource(R.drawable.ic_history_black_24dp);
            emptyText.setText(R.string.empty_history);
        }
        translationList.setVisibility(View.INVISIBLE);
        emptyImage.setVisibility(View.VISIBLE);
        emptyText.setVisibility(View.VISIBLE);
    }

    public void showTranslation(String textToTranslate, String lang) {
        if (getActivity() instanceof NavigationActivity) {
            ((NavigationActivity) getActivity()).navigateToTranslate(textToTranslate, lang);
        }
    }
}
