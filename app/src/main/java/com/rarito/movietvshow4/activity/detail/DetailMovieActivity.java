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
import com.rarito.movietvshow4.database.MovieFavoriteHelper;
import com.rarito.movietvshow4.model.Movie;
import com.rarito.movietvshow4.model.MovieFavorite;

public class DetailMovieActivity extends AppCompatActivity implements View.OnClickListener {

    public static int TAB_TITLE = R.string.title_movies_actionbar;
    public static final String EXTRA_MOVIE = "extra_movie";

    private TextView tvTitle, tvRelease, tvVote, tvOverview, tvPopular, tvLanguage;
    private ImageView imgPoster;
    private ProgressBar progressBar;
    private Button btnAddFavorite;

    public static final String EXTRA_MOVIE_FAV = "extra_movie_favorite";
    public static final String EXTRA_POSITION = "extra_position";
    private MovieFavorite movieFavorite;
    private int position;
    private MovieFavoriteHelper movieFavoriteHelper;
    private boolean isInsert = false;

    public static final int RESULT_ADD = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        getSupportActionBar().setTitle(TAB_TITLE);

        progressBar = findViewById(R.id.detail_probar_movie);
        tvTitle = findViewById(R.id.detail_tv_movie_title);
        tvOverview = findViewById(R.id.detail_tv_movie_overview);
        tvPopular = findViewById(R.id.detail_tv_movie_popularity);
        tvVote = findViewById(R.id.detail_tv_movie_vote_average);
        tvRelease = findViewById(R.id.detail_tv_movie_release);
        tvLanguage = findViewById(R.id.detail_tv_movie_language);
        imgPoster = findViewById(R.id.detail_image_movie);
        btnAddFavorite = findViewById(R.id.btn_movie_add_favorite);
        btnAddFavorite.setOnClickListener(this);

        Movie movieView = getIntent().getParcelableExtra(EXTRA_MOVIE);
        String title = movieView.getTitle();
        tvTitle.setText(title);
        String release = movieView.getReleaseDate();
        tvRelease.setText(release);
        String language = movieView.getLanguage();
        tvLanguage.setText(language);
        String overview = movieView.getOverview();
        tvOverview.setText(overview);
        Double popular = (Double) movieView.getPopularity();
        tvPopular.setText(Double.toString(popular));
        Double vote = (Double) movieView.getVoteAverage();
        tvVote.setText(Double.toString(vote));
        String imagePoster = movieView.getPoster();
        Glide.with(this)
                .load(imagePoster)
                .apply(new RequestOptions().override(45, 55))
                .into(imgPoster);

        showLoadingMovie(true);
        if (imgPoster != null) {
            showLoadingMovie(false);
        }

        movieFavoriteHelper = MovieFavoriteHelper.getInstance(getApplicationContext());
        movieFavoriteHelper.open();
        movieFavorite = getIntent().getParcelableExtra(EXTRA_MOVIE_FAV);
        if (movieFavorite != null) {
            position = getIntent().getIntExtra(EXTRA_POSITION, 0);
            isInsert = true;
            btnAddFavorite.setVisibility(View.GONE);
        } else {
            movieFavorite = new MovieFavorite();
        }

        if (savedInstanceState != null) {
            Movie movie = getIntent().getParcelableExtra(EXTRA_MOVIE);

            tvTitle.setText(movie.getTitle());
            tvRelease.setText(movie.getReleaseDate());
            tvLanguage.setText(movie.getLanguage());
            tvPopular.setText(Double.toString((Double) movie.getPopularity()));
            tvVote.setText(Double.toString((Double) movie.getVoteAverage()));
            Glide.with(this)
                    .load(imagePoster)
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
                            Movie movieView = getIntent().getParcelableExtra(EXTRA_MOVIE);

                            tvTitle.setText(movieView.getTitle());
                            tvRelease.setText(movieView.getReleaseDate());
                            tvLanguage.setText(movieView.getLanguage());
                            tvPopular.setText(Double.toString((Double) movieView.getPopularity()));
                            tvVote.setText(Double.toString((Double) movieView.getVoteAverage()));
                            Glide.with(DetailMovieActivity.this)
                                    .load(movieView.getPoster())
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

    private void showLoadingMovie(boolean b) {
        if (b) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_movie_add_favorite) {
            Movie movieView = getIntent().getParcelableExtra(EXTRA_MOVIE);
            String title = tvTitle.getText().toString().trim();
            String release_date = tvRelease.getText().toString().trim();
            Double vote_average = Double.valueOf(tvVote.getText().toString().trim());
            Double popular = Double.valueOf(tvPopular.getText().toString().trim());
            String language = tvLanguage.getText().toString().trim();
            String poster = movieView.getPoster();

            movieFavorite.setTitle(title);
            movieFavorite.setReleaseDate(release_date);
            movieFavorite.setVoteAverage(vote_average);
            movieFavorite.setPopularity(popular);
            movieFavorite.setLanguage(language);
            movieFavorite.setPoster(poster);

            Intent intent = new Intent();
            intent.putExtra(EXTRA_MOVIE_FAV, movieFavorite);
            intent.putExtra(EXTRA_POSITION, position);

            if (!isInsert) {

                long result = movieFavoriteHelper.insertMovie(movieFavorite);

                if (result > 0) {
                    movieFavorite.setId((int) result);
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
