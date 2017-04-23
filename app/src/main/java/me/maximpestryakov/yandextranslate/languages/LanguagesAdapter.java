package me.maximpestryakov.yandextranslate.languages;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;
import me.maximpestryakov.yandextranslate.R;
import me.maximpestryakov.yandextranslate.languages.LanguagesAdapter.LanguageViewHolder;
import me.maximpestryakov.yandextranslate.model.Language;

public class LanguagesAdapter extends RealmRecyclerViewAdapter<Language, LanguageViewHolder> {

    private String currentLang;

    private LanguageViewHolder currentViewHolder;

    LanguagesAdapter(@Nullable OrderedRealmCollection<Language> languages, String currentLang) {
        super(languages, true);
        this.currentLang = currentLang;
    }

    @Override
    public LanguageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_language, parent, false);
        return new LanguageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LanguageViewHolder holder, int position) {
        Language language = getItem(position);
        if (language == null) {
            return;
        }
        holder.itemView.setOnClickListener(v -> {
            currentLang = language.getCode();
            if (currentViewHolder != null) {
                currentViewHolder.uncheck();
            }
            holder.check();
            currentViewHolder = holder;
        });

        boolean isCurrent = currentLang.equals(language.getCode());
        holder.bind(language, isCurrent);
        if (isCurrent) {
            currentViewHolder = holder;
        }
    }

    String getCurrentLang() {
        return currentLang;
    }

    static class LanguageViewHolder extends ViewHolder {

        @BindView(R.id.langTitle)
        TextView langTitle;

        @BindView(R.id.langViewStub)
        ViewStub viewStub;

        LanguageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(Language language, boolean isCurrent) {
            langTitle.setText(language.getTitle());
            if (isCurrent) {
                check();
            } else {
                uncheck();
            }
        }

        void check() {
            viewStub.setVisibility(View.VISIBLE);
        }

        void uncheck() {
            viewStub.setVisibility(View.GONE);
        }
    }
}
