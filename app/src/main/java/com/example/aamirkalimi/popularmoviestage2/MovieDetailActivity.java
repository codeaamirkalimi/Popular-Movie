package com.example.aamirkalimi.popularmoviestage2;

import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.room.Room;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.aamirkalimi.popularmoviestage2.StringUrlConstants.DB_NAME;
import static com.example.aamirkalimi.popularmoviestage2.StringUrlConstants.API_KEY;
import static com.example.aamirkalimi.popularmoviestage2.StringUrlConstants.DB_NAME;
import static com.example.aamirkalimi.popularmoviestage2.StringUrlConstants.POSTER_BASE_URL;
import static com.example.aamirkalimi.popularmoviestage2.StringUrlConstants.REVIEW_URL;
import static com.example.aamirkalimi.popularmoviestage2.StringUrlConstants.TRAILERS_MOVIES_URL;
import static com.example.aamirkalimi.popularmoviestage2.StringUrlConstants.TRAILER_URL;

public class MovieDetailActivity extends AppCompatActivity {
    private static final String LOG_TAG = "MovieDetailsActivity";
    private List<TrailerList> trailerLists;
    private TrailerAdapter adapter;
    private RecyclerView recyclerViewTr, recyclerViewRV;
    private long idVal;
    private List<ReviewList> reviewsLists;
    private ReviewAdapter adapter2;
    private TextView favTV;
    private ImageView favView;
    private boolean isFavorite = false;
    private MovieDao mMovieDao;
    private MovieList movieList;
    private ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        mMovieDao = Room.databaseBuilder(this, MovieDB.class, DB_NAME).allowMainThreadQueries().build().getMovieDao();
        createActivityUI();
    }
    public void createActivityUI() {
        ImageView posterBannerIV = findViewById(R.id.movie_poster);
        TextView titleTextView = findViewById(R.id.movie_title);
        TextView releaseTV = findViewById(R.id.release_date);
        TextView ratingTV = findViewById(R.id.rating_value);
        TextView description = findViewById(R.id.synopsis_text);
        scrollView = findViewById(R.id.scroll_view);
        favTV = findViewById(R.id.tVFav);
        recyclerViewTr = findViewById(R.id.trailerRV);
        recyclerViewTr.setHasFixedSize(false);
        recyclerViewTr.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        recyclerViewTr.setItemAnimator(new DefaultItemAnimator());
        trailerLists = new ArrayList<>();
        recyclerViewRV = findViewById(R.id.reviewsRV);
        recyclerViewRV.setHasFixedSize(false);
        recyclerViewRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerViewRV.setItemAnimator(new DefaultItemAnimator());
        reviewsLists = new ArrayList<>();
        Bundle data = getIntent().getExtras();
        assert data != null;
        movieList = data.getParcelable(StringUrlConstants.PARCEL_KEY);
        assert movieList != null;
        idVal = movieList.getId();
        final String titleText = movieList.getTitle();
        String image = POSTER_BASE_URL + movieList.getPosterUrl();
        final String descriptionText = movieList.getDescription();
        final String ratings = movieList.getVoteAverage();
        final String releaseDate = movieList.getReleaseDate();
        loadTrailers();
        loadReviews();
        Picasso.get().load(image).into(posterBannerIV);
        titleTextView.setText(titleText);
        ratingTV.setText(ratings);
        releaseTV.setText(releaseDate);
        description.setText(descriptionText);
        favView = findViewById(R.id.favIcon);
        favView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFavorite(v);
            }
        });
        if (mMovieDao.getMovieWithId(idVal)) {
            favView.setImageResource(R.drawable.fav_fill);
            favTV.setVisibility(View.GONE);
            isFavorite = true;
        }
    }
    private void loadTrailers() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET,TRAILERS_MOVIES_URL + idVal + TRAILER_URL + API_KEY , new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray array = jsonObject.getJSONArray("results");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject jo = array.getJSONObject(i);
                        TrailerList trailerListData = new TrailerList(jo.getString("id"), jo.getString("key"));
                        trailerLists.add(trailerListData);
                    }
                    adapter = new TrailerAdapter(trailerLists, getApplicationContext());
                    recyclerViewTr.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e(LOG_TAG, e.getMessage(), e);
                }
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MovieDetailActivity.this, "Error! couldn't process" + error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void loadReviews() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET,TRAILERS_MOVIES_URL + idVal + REVIEW_URL + API_KEY , new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray array = jsonObject.getJSONArray("results");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject jo = array.getJSONObject(i);
                        ReviewList reviewsListData = new ReviewList(jo.getString("author"),jo.getString("content"));
                        reviewsLists.add(reviewsListData);
                    }
                    adapter2 = new ReviewAdapter(reviewsLists, getApplicationContext());
                    recyclerViewRV.setAdapter(adapter2);
                    adapter2.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e(LOG_TAG, e.getMessage(), e);
                }
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MovieDetailActivity.this, "Error! couldn't process" + error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void toggleFavorite(final View v) {
        GlobalAppExecutor.getInstance().diskIO().execute(new Runnable() {

            @Override
            public void run() {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        if (!isFavorite) {
                            try {
                                mMovieDao.insert(movieList);
                            } catch (SQLiteConstraintException e) {
                                Snackbar.make(v.getRootView(), R.string.same_movie, Snackbar.LENGTH_SHORT).show();
                            }
                            favView.setImageResource(R.drawable.fav_fill);
                            favTV.setVisibility(View.GONE);
                            Snackbar.make(v.getRootView(), R.string.added_fav, Snackbar.LENGTH_SHORT).show();
                            isFavorite = true;
                        } else {
                            mMovieDao.delete(movieList);
                            favTV.setVisibility(View.VISIBLE);
                            favView.setImageResource(R.drawable.fav_empty);
                            setResult(RESULT_OK);
                            Snackbar.make(v.getRootView(), R.string.removed_fav, Snackbar.LENGTH_SHORT).show();
                            isFavorite = false;
                            setupViewModel();
                        }
                    }
                });
            }
        });
    }
    private void setupViewModel() {
        MovieViewModel viewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        viewModel.getTasks().observe(this, new Observer<List<MovieList>>() {

            @Override
            public void onChanged(@Nullable List<MovieList> taskEntries) {
                new MovieAdapter(taskEntries, getApplicationContext());
            }
        });
    }
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putIntArray("ARTICLE_SCROLL_POSITION", new int[]{ scrollView.getScrollX(), scrollView.getScrollY()});
    }
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        final int[] position = savedInstanceState.getIntArray("ARTICLE_SCROLL_POSITION");
        if (position != null) {
            scrollView.postDelayed(new Runnable() {
                public void run() {
                    scrollView.scrollTo(position[0], position[1]);
                }
            }, 100);
        }
    }
}
