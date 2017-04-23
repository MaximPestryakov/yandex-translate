package me.maximpestryakov.yandextranslate.translations;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmRecyclerViewAdapter;
import me.maximpestryakov.yandextranslate.R;
import me.maximpestryakov.yandextranslate.model.Translation;
import me.maximpestryakov.yandextranslate.model.Translation.OnTranslationClickListener;
import me.maximpestryakov.yandextranslate.util.FavoriteView;

class TranslationsAdapter extends RealmRecyclerViewAdapter<Translation, TranslationsAdapter.TranslationViewHolder> {

    private int lastCount;

    private OnTranslationClickListener onTranslationClickListener;

    private OnItemCountChangeListener onItemCountChangeListener;


    TranslationsAdapter(@Nullable OrderedRealmCollection<Translation> data,
                        OnTranslationClickListener onTranslationClickListener) {
        super(data, true);
        if (data == null) {
            lastCount = 0;
        } else {
            lastCount = data.size();
        }
        this.onTranslationClickListener = onTranslationClickListener;
    }

    @Override
    public TranslationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_translation, parent, false);
        return new TranslationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TranslationViewHolder holder, int position) {
        Translation translation = getItem(position);
        holder.bind(onTranslationClickListener, translation);
    }

    @Override
    public int getItemCount() {
        int itemCount = super.getItemCount();
        if (itemCount != lastCount) {
            lastCount = itemCount;
            if (onItemCountChangeListener != null) {
                onItemCountChangeListener.OnItemCountChange(itemCount);
            }
        }
        return itemCount;
    }

    void setOnItemCountChangeListener(OnItemCountChangeListener onItemCountChangeListener) {
        this.onItemCountChangeListener = onItemCountChangeListener;
        onItemCountChangeListener.OnItemCountChange(lastCount);
    }

    interface OnItemCountChangeListener {

        void OnItemCountChange(int itemCount);
    }

    static class TranslationViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.itemFavorite)
        FavoriteView itemFavorite;

        @BindView(R.id.itemOriginal)
        TextView itemOriginal;

        @BindView(R.id.itemTranslation)
        TextView itemTranslation;

        @BindView(R.id.itemDirection)
        TextView itemDirection;

        TranslationViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(OnTranslationClickListener onTranslationClick, Translation translation) {
            itemView.setOnClickListener(v -> onTranslationClick.onClick(translation.getOriginal(),
                    translation.getLang()));
            itemOriginal.setText(translation.getOriginal());
            itemTranslation.setText(translation.getText().get(0).toString());
            itemFavorite.setChecked(translation.isFavorite());
            itemFavorite.setOnClickListener(v -> {
                try (Realm r = Realm.getDefaultInstance()) {
                    r.executeTransaction(realm -> translation.setFavorite(itemFavorite.isChecked()));
                }
            });
            itemDirection.setText(translation.getLang().toUpperCase());
        }
    }
}
