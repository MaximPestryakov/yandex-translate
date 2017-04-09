package me.maximpestryakov.yandextranslate.languages;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;
import me.maximpestryakov.yandextranslate.R;
import me.maximpestryakov.yandextranslate.model.Language;

public class LanguagesAdapter extends RealmRecyclerViewAdapter<Language, LanguagesAdapter.LanguageViewHolder> {

    private String currentLang;

    public LanguagesAdapter(@Nullable OrderedRealmCollection<Language> languages, String currentLang) {
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
        holder.itemView.setOnClickListener(v -> {
            currentLang = language.getCode();
            notifyDataSetChanged();
        });

        holder.bind(language, currentLang.equals(language.getCode()));
    }

    String getCurrentLang() {
        return currentLang;
    }

    interface OnChoseLang {
        void onChose(String lang);
    }

    static class LanguageViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.langTitle)
        TextView langTitle;

        @BindView(R.id.langCheck)
        ImageView langCheck;

        LanguageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(Language language, boolean isCurrent) {
            langTitle.setText(language.getTitle());
            if (isCurrent) {
                langCheck.setVisibility(View.VISIBLE);
            } else {
                langCheck.setVisibility(View.GONE);
            }
        }
    }
}
