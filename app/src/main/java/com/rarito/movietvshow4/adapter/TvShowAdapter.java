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
import com.rarito.movietvshow4.model.TvShow;

import java.util.ArrayList;

public class TvShowAdapter extends RecyclerView.Adapter<TvShowAdapter.TvShowVH> {

    private Context tContext;
    private ArrayList<TvShow> tvShowsData = new ArrayList<>();
    private OnItemClickListener tListener;

    public TvShowAdapter(Context tContext) {
        this.tContext = tContext;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.tListener = listener;
    }

    public interface OnItemClickListener {
        void onTvItemClick(int position);
    }

    public void setTvShowsData(ArrayList<TvShow> items) {
        tvShowsData.clear();
        tvShowsData.addAll(items);
        notifyDataSetChanged();
    }

    public ArrayList<TvShow> getTvShowsData() {
        return tvShowsData;
    }

    @NonNull
    @Override
    public TvShowVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tvshow_card, parent, false);
        return new TvShowVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TvShowVH holder, int position) {
        holder.tBind(tvShowsData.get(position));
    }

    @Override
    public int getItemCount() {
        return tvShowsData.size();
    }

    public class TvShowVH extends RecyclerView.ViewHolder {
        TextView tvShowTitle, tvShowLanguage, tvShowPopularity, tvShowVote, tvShowRelease;
        ImageView imgShowPoster;

        public TvShowVH(@NonNull View itemView) {
            super(itemView);
            tvShowTitle = itemView.findViewById(R.id.tv_item_title_tvShow);
            imgShowPoster = itemView.findViewById(R.id.img_item_photo_tvShow);
            tvShowVote = itemView.findViewById(R.id.tv_item_vote_tvShow);
            tvShowRelease = itemView.findViewById(R.id.tv_item_release_tvShow);
            tvShowPopularity = itemView.findViewById(R.id.tv_item_popularity_tvShow);
            tvShowLanguage = itemView.findViewById(R.id.tv_item_language_tvShow);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (tListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            tListener.onTvItemClick(position);
                        }
                    }
                }
            });
        }

        public void tBind(TvShow tvShow) {
            Glide.with(tContext)
                    .load(tvShow.getPoster())
                    .apply(new RequestOptions().override(60, 60))
                    .into(imgShowPoster);
            tvShowTitle.setText(tvShow.getTitle());
            tvShowRelease.setText(tvShow.getRelease());
            tvShowVote.setText(String.valueOf(tvShow.getVoteAverage()));
            tvShowPopularity.setText(String.valueOf(tvShow.getPopularity()));
            tvShowLanguage.setText(tvShow.getLanguage());
        }
    }
}
