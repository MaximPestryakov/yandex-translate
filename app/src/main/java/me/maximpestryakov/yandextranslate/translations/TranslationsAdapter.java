package me.maximpestryakov.yandextranslate.translations;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import me.maximpestryakov.yandextranslate.R;
import me.maximpestryakov.yandextranslate.model.Translation;


class TranslationsAdapter extends RecyclerView.Adapter<TranslationsAdapter.FavoriteViewHolder> {

    private boolean onlyFavorites;
    private OnClickListener onClickListener;
    private List<Translation> translations;

    TranslationsAdapter(boolean onlyFavorites, OnClickListener onClickListener) {
        this.onlyFavorites = onlyFavorites;
        this.onClickListener = onClickListener;
        updateData();
    }

    @Override
    public FavoriteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite, parent, false);
        return new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FavoriteViewHolder holder, int position) {
        holder.bind(onClickListener, translations.get(position));
    }

    @Override
    public int getItemCount() {
        if (translations == null) {
            return 0;
        }
        return translations.size();
    }

    private void updateData() {
        Realm.getDefaultInstance().executeTransaction(realm ->
                translations = realm.where(Translation.class)
                        .equalTo("favorite", onlyFavorites)
                        .findAll());
        notifyDataSetChanged();
    }

    static class FavoriteViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.itemOriginal)
        TextView itemOriginal;

        @BindView(R.id.itemTranslation)
        TextView itemTranslation;

        FavoriteViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(OnClickListener onClickListener, Translation translation) {
            itemView.setOnClickListener(onClickListener);
            itemOriginal.setText(translation.getOriginal());
            itemTranslation.setText(translation.getText().get(0).getValue());
        }
    }
}
