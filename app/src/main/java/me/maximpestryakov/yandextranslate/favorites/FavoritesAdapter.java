package me.maximpestryakov.yandextranslate.favorites;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.maximpestryakov.yandextranslate.R;
import me.maximpestryakov.yandextranslate.model.Translation;


class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavoriteViewHolder> {

    private OnClickListener onClickListener;
    private List<Translation> favorites;

    FavoritesAdapter(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public FavoriteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite, parent, false);
        return new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FavoriteViewHolder holder, int position) {
        holder.bind(onClickListener, favorites.get(position));
    }

    @Override
    public int getItemCount() {
        if (favorites == null) {
            return 0;
        }
        return favorites.size();
    }

    void setFavorites(List<Translation> favorites) {
        this.favorites = favorites;
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
            itemTranslation.setText(translation.getText().get(0));
        }
    }
}
