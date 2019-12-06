package com.rarito.movietvshow4.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.rarito.movietvshow4.R;
import com.rarito.movietvshow4.model.Movie;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private Context mContext;
    private ArrayList<Movie> moviesData = new ArrayList<>();
    private OnItemClickListener mListener;

    public MovieAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    public interface OnItemClickListener {
        void onMovieItemClick(int position);
    }

    public void setMoviesData(ArrayList<Movie> items) {
        moviesData.clear();
        moviesData.addAll(items);
        notifyDataSetChanged();
    }

    public ArrayList<Movie> getMoviesData() {
        return moviesData;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_card, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        holder.mBind(moviesData.get(position));
    }

    @Override
    public int getItemCount() {
        return moviesData.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        TextView tvMovTitle, tvMovLanguage, tvMovPopularity, tvMovVote, tvMovRelease;
        ImageView imgMovPoster;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMovTitle = itemView.findViewById(R.id.tv_item_title_movie);
            imgMovPoster = itemView.findViewById(R.id.img_item_photo_movie);
            tvMovVote = itemView.findViewById(R.id.tv_item_vote_movie);
            tvMovRelease = itemView.findViewById(R.id.tv_item_release_movie);
            tvMovPopularity = itemView.findViewById(R.id.tv_item_popularity_movie);
            tvMovLanguage = itemView.findViewById(R.id.tv_item_language_movie);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onMovieItemClick(position);
                        }
                    }
                }
            });

        }

        public void mBind(Movie movie) {
            Glide.with(mContext)
                    .load(movie.getPoster())
                    .apply(new RequestOptions().override(60, 60))
                    .into(imgMovPoster);
            tvMovLanguage.setText(movie.getLanguage());
            tvMovTitle.setText(movie.getTitle());
            tvMovRelease.setText(movie.getReleaseDate());
            tvMovVote.setText(String.valueOf(movie.getVoteAverage()));
            tvMovPopularity.setText(String.valueOf(movie.getPopularity()));
        }
    }
}
