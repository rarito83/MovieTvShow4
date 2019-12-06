package com.rarito.movietvshow4.activity.detail;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.rarito.movietvshow4.R;
import com.rarito.movietvshow4.database.TvShowFavoriteHelper;
import com.rarito.movietvshow4.model.TvShow;
import com.rarito.movietvshow4.model.TvShowFavorite;

public class DetailTvShowActivity extends AppCompatActivity implements View.OnClickListener {

    public static int TAB_TITLE = R.string.title_tvShow_actionbar;
    public static final String EXTRA_TVSHOW = "extra_tvShow";

    private TextView tvTitle, tvRelease, tvVote, tvOverview, tvPopular, tvLanguage;
    private ImageView imgPoster;
    private ProgressBar progressBar;
    private Button btnAddFavorite;

    public static final String EXTRA_TVSHOW_FAV = "extra_tvshow_favorite";
    public static final String EXTRA_POSITION = "extra_position";
    private TvShowFavorite tvShowFavorite;
    private int position;
    private TvShowFavoriteHelper tvShowFavoriteHelper;
    private boolean isInsert = false;

    public static final int RESULT_ADD = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tv_show);

        getSupportActionBar().setTitle(TAB_TITLE);

        progressBar = findViewById(R.id.detail_probar_tvShow);
        tvTitle = findViewById(R.id.detail_tv_tvShow_title);
        tvOverview = findViewById(R.id.detail_tv_tvShow_overview);
        tvPopular = findViewById(R.id.detail_tv_tvShow_popularity);
        tvVote = findViewById(R.id.detail_tv_tvShow_vote_average);
        imgPoster = findViewById(R.id.detail_img_tvShow);
        tvRelease = findViewById(R.id.detail_tv_tvShow_release);
        tvLanguage = findViewById(R.id.detail_tv_tvShow_language);
        btnAddFavorite = findViewById(R.id.btn_tvShow_add_favorite);
        btnAddFavorite.setOnClickListener(this);

        TvShow tvShow = getIntent().getParcelableExtra(EXTRA_TVSHOW);
        String title = tvShow.getTitle();
        tvTitle.setText(title);
        String release = tvShow.getRelease();
        tvRelease.setText(release);
        String language = tvShow.getLanguage();
        tvLanguage.setText(language);
        String overview = tvShow.getOverview();
        tvOverview.setText(overview);
        Double popular = (Double) tvShow.getPopularity();
        tvPopular.setText(Double.toString(popular));
        Double vote = (Double) tvShow.getVoteAverage();
        tvVote.setText(Double.toString(vote));
        String image = tvShow.getPoster();
        Glide.with(this)
                .load(image)
                .apply(new RequestOptions().override(300, 500))
                .into(imgPoster);

        showLoadingTvShow(true);
        if (imgPoster != null) {
            showLoadingTvShow(false);
        }

        tvShowFavoriteHelper = TvShowFavoriteHelper.getInstance(getApplicationContext());
        tvShowFavoriteHelper.open();
        tvShowFavorite = getIntent().getParcelableExtra(EXTRA_TVSHOW_FAV);
        if (tvShowFavorite != null) {
            position = getIntent().getIntExtra(EXTRA_POSITION, 0);
            isInsert = true;
            btnAddFavorite.setVisibility(View.GONE);
        } else {
            tvShowFavorite = new TvShowFavorite();
        }

        if (savedInstanceState != null) {
            TvShow tvShows = getIntent().getParcelableExtra(EXTRA_TVSHOW);

            tvTitle.setText(tvShows.getTitle());
            tvRelease.setText(tvShows.getRelease());
            tvLanguage.setText(tvShows.getLanguage());
            tvPopular.setText(Double.toString((Double) tvShows.getPopularity()));
            tvVote.setText(Double.toString((Double) tvShows.getVoteAverage()));
            Glide.with(this)
                    .load(image)
                    .apply(new RequestOptions().override(45, 55))
                    .into(imgPoster);

        } else {
            final Handler handler = new Handler();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                    } catch
                    (Exception e) {
                        e.printStackTrace();
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            TvShow tvShow = getIntent().getParcelableExtra(EXTRA_TVSHOW);

                            tvTitle.setText(tvShow.getTitle());
                            tvRelease.setText(tvShow.getRelease());
                            tvLanguage.setText(tvShow.getLanguage());
                            tvPopular.setText(Double.toString((Double) tvShow.getPopularity()));
                            tvVote.setText(Double.toString((Double) tvShow.getVoteAverage()));
                            Glide.with(DetailTvShowActivity.this)
                                    .load(tvShow.getPoster())
                                    .apply(new RequestOptions().override(45, 55))
                                    .into(imgPoster);

                            if (getSupportActionBar() != null) {
                                getSupportActionBar().setTitle(TAB_TITLE);
                            }
                        }
                    });
                }
            }).start();
        }
    }

    private void showLoadingTvShow(boolean c) {
        if (c) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_tvShow_add_favorite) {
            TvShow tvShow = getIntent().getParcelableExtra(EXTRA_TVSHOW);
            String title = tvTitle.getText().toString().trim();
            String release_date = tvRelease.getText().toString().trim();
            Double vote_average = Double.valueOf(tvVote.getText().toString().trim());
            Double popular = Double.valueOf(tvPopular.getText().toString().trim());
            String language = tvLanguage.getText().toString().trim();
            String poster = tvShow.getPoster();

            tvShowFavorite.setTitle(title);
            tvShowFavorite.setReleaseDate(release_date);
            tvShowFavorite.setVoteAverage(vote_average);
            tvShowFavorite.setPopularity(popular);
            tvShowFavorite.setLanguage(language);
            tvShowFavorite.setPoster(poster);

            Intent intent = new Intent();
            intent.putExtra(EXTRA_TVSHOW_FAV, tvShowFavorite);
            intent.putExtra(EXTRA_POSITION, position);

            if (!isInsert) {

                long result = tvShowFavoriteHelper.insertTvShow(tvShowFavorite);

                if (result > 0) {
                    tvShowFavorite.setId((int) result);
                    setResult(RESULT_ADD, intent);
                    // Toast.makeText(DetailMovieActivity.this, getString(R.string.success_add), Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    // Toast.makeText(DetailMovieActivity.this, getString(R.string.failed_add), Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }
    }
}
