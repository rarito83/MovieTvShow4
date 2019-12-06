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
import com.rarito.movietvshow4.model.MovieFavorite;

import java.util.ArrayList;

public class MovieFavoriteAdapter extends RecyclerView.Adapter<MovieFavoriteAdapter.MovFavViewHolder> {

    private ArrayList<MovieFavorite> movieFavorites = new ArrayList<>();
    private Activity activity;
    private OnItemClickListener movFavListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.movFavListener = listener;
    }

    public interface OnItemClickListener {
        void onMovFavItemClick(int position);
    }

    public MovieFavoriteAdapter(Activity activity) {
        this.activity = activity;
    }

    public ArrayList<MovieFavorite> getMovieFavorites() {
        return movieFavorites;
    }

    public void setMovieFavorites(ArrayList<MovieFavorite> movieFavorites) {
        if (movieFavorites.size() > 0) {
            this.movieFavorites.clear();
        }
        this.movieFavorites.addAll(movieFavorites);
        notifyDataSetChanged();
    }

    public void addItem(MovieFavorite movieFavorite) {
        this.movieFavorites.add(movieFavorite);
        notifyItemInserted(movieFavorites.size() - 1);
    }

    public void updateItem(int position, MovieFavorite movieFavorite) {
        this.movieFavorites.set(position, movieFavorite);
        notifyItemChanged(position, movieFavorite);
    }

    public void removeItem(int position) {
        this.movieFavorites.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, movieFavorites.size());
    }

    @NonNull
    @Override
    public MovFavViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_favorite, parent, false);
        return new MovFavViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovFavViewHolder holder, final int i) {
        holder.tvTitle.setText(movieFavorites.get(i).getTitle());
        holder.tvRelease.setText(movieFavorites.get(i).getReleaseDate());
        holder.tvPopular.setText(String.valueOf(movieFavorites.get(i).getPopularity()));
        holder.tvVote.setText(String.valueOf(movieFavorites.get(i).getVoteAverage()));
        holder.tvLanguage.setText(movieFavorites.get(i).getLanguage());
        Glide.with(holder.imgPoster).load(movieFavorites.get(i).getPoster()).apply(new RequestOptions().override(55, 75))
                .into(holder.imgPoster);

        holder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (movFavListener != null) {
                    movFavListener.onMovFavItemClick(movieFavorites.get(i).getId());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieFavorites.size();
    }

    public class MovFavViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle, tvRelease, tvPopular, tvVote, tvLanguage;
        ImageView imgPoster;
        Button btnRemove;

        public MovFavViewHolder(@NonNull final View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_item_title_movie_fav);
            tvRelease = itemView.findViewById(R.id.tv_item_release_movie_fav);
            tvPopular = itemView.findViewById(R.id.tv_item_popularity_movie_fav);
            tvVote = itemView.findViewById(R.id.tv_item_vote_movie_fav);
            tvLanguage = itemView.findViewById(R.id.tv_item_language_movie_fav);
            imgPoster = itemView.findViewById(R.id.img_item_photo_movie_fav);
            btnRemove = itemView.findViewById(R.id.btn_movie_remove_favorite);

        }
    }
}