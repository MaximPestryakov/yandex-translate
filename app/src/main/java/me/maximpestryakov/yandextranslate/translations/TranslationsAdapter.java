package me.maximpestryakov.yandextranslate.translations;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmRecyclerViewAdapter;
import me.maximpestryakov.yandextranslate.R;
import me.maximpestryakov.yandextranslate.model.Translation;

class TranslationsAdapter extends RealmRecyclerViewAdapter<Translation, TranslationsAdapter.TranslationViewHolder> {

    private OnTranslationClickListener onTranslationClick;

    TranslationsAdapter(@Nullable OrderedRealmCollection<Translation> data, OnTranslationClickListener onTranslationClick) {
        super(data, true);
        this.onTranslationClick = onTranslationClick;
    }

    @Override
    public TranslationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_translation, parent, false);
        return new TranslationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TranslationViewHolder holder, int position) {
        Translation translation = getItem(position);
        holder.bind(onTranslationClick, translation);
    }

    interface OnTranslationClickListener {

        void onClick(String textToTranslate);
    }

    static class TranslationViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.itemFavorite)
        CheckBox itemFavorite;

        @BindView(R.id.itemOriginal)
        TextView itemOriginal;

        @BindView(R.id.itemTranslation)
        TextView itemTranslation;

        TranslationViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(OnTranslationClickListener onTranslationClick, Translation translation) {
            itemView.setOnClickListener(v -> onTranslationClick.onClick(translation.getOriginal()));
            itemOriginal.setText(translation.getOriginal());
            itemTranslation.setText(translation.getText().get(0).toString());
            itemFavorite.setChecked(translation.isFavorite());
            itemFavorite.setOnClickListener(v -> {
                try (Realm r = Realm.getDefaultInstance()) {
                    r.executeTransaction(realm -> translation.setFavorite(itemFavorite.isChecked()));
                }
            });
        }
    }
}
