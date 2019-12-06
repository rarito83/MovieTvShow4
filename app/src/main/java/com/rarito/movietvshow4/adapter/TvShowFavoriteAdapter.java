package com.rarito.movietvshow4.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.rarito.movietvshow4.R;
import com.rarito.movietvshow4.model.TvShowFavorite;

import java.util.ArrayList;

public class TvShowFavoriteAdapter extends RecyclerView.Adapter<TvShowFavoriteAdapter.TvShowFavViewHolder> {

    private ArrayList<TvShowFavorite> tvShowFavorites = new ArrayList<>();
    private OnItemClickListener tvShowFavListener;
    private Activity activity;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.tvShowFavListener = listener;
    }

    public TvShowFavoriteAdapter(Activity activity) {
        this.activity = activity;
    }

    public ArrayList<TvShowFavorite> getTvShowFavorites() {
        return tvShowFavorites;
    }

    public interface OnItemClickListener {
        void onTvShowFavItemClick(int position);
    }

    public void setTvShowFavorites(ArrayList<TvShowFavorite> tvShowFavorites) {
        this.tvShowFavorites.clear();
        this.tvShowFavorites.addAll(tvShowFavorites);
        notifyDataSetChanged();
    }

    public void addItem(TvShowFavorite tvShowFav) {
        this.tvShowFavorites.add(tvShowFav);
        notifyItemInserted(tvShowFavorites.size() - 1);
    }

    public void updateItem(int position, TvShowFavorite tvShowFav) {
        this.tvShowFavorites.set(position, tvShowFav);
        notifyItemChanged(position, tvShowFav);
    }

    public void removeItem(int position) {
        this.tvShowFavorites.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, tvShowFavorites.size());
    }

    @NonNull
    @Override
    public TvShowFavViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tvshow_favorite, parent, false);
        return new TvShowFavViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TvShowFavViewHolder holder, final int i) {
        holder.tvTitle.setText(tvShowFavorites.get(i).getTitle());
        holder.tvRelease.setText(tvShowFavorites.get(i).getReleaseDate());
        holder.tvPopular.setText(String.valueOf(tvShowFavorites.get(i).getPopularity()));
        holder.tvVote.setText(String.valueOf(tvShowFavorites.get(i).getVoteAverage()));
        holder.tvLanguage.setText(tvShowFavorites.get(i).getLanguage());
        Glide.with(holder.imgPoster).load(tvShowFavorites.get(i).getPoster()).apply(new RequestOptions().override(55, 75))
                .into(holder.imgPoster);
        holder.btnRemoveTvFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvShowFavListener != null) {
                    tvShowFavListener.onTvShowFavItemClick(tvShowFavorites.get(i).getId());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return tvShowFavorites.size();
    }

    public class TvShowFavViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle, tvRelease, tvPopular, tvVote, tvLanguage;
        ImageView imgPoster;
        Button btnRemoveTvFav;

        public TvShowFavViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_item_title_tvShow_fav);
            tvRelease = itemView.findViewById(R.id.tv_item_release_tvShow_fav);
            tvPopular = itemView.findViewById(R.id.tv_item_popularity_tvShow_fav);
            tvVote = itemView.findViewById(R.id.tv_item_vote_tvShow_fav);
            tvLanguage = itemView.findViewById(R.id.tv_item_language_tvShow_fav);
            imgPoster = itemView.findViewById(R.id.img_item_photo_tvShow_fav);
            btnRemoveTvFav = itemView.findViewById(R.id.btn_tvShow_remove_favorite);
        }
    }
}
