package com.example.susan.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

/**
 * Created by Susan on 8/24/2015.
 */
public class PosterDisplayFragment extends Fragment {

    public static final String LOG_TAG = PosterDisplayFragment.class.getSimpleName();
    private int position = 0;
    public MovieAdapter mAdapter;
    public static ArrayList<MovieDetails> movies = new ArrayList<>();
    GridView gridview;
    public static final String MOVIES_KEY = "movies";


    @Override
    public void onCreate(Bundle savedInstanceState){
//        if(savedInstanceState == null){
//
//        }else {
//            //fetch data
//            // updateMovies();
//        }
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        Bundle args = getArguments();
        if (args != null) {
            this.setPosition(args.getInt("position"));
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            // Restore last state for checked position.
            ArrayList<MovieDetails> movies_saved =  savedInstanceState.getParcelableArrayList(MOVIES_KEY);
        }
    }
    @Override
    public void onStart(){
        super.onStart();


    }

    public static PosterDisplayFragment newInstance(int position) {
        PosterDisplayFragment f = new PosterDisplayFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.poster_fragment_menu, menu);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState){
        Log.v(LOG_TAG, "In frag's on create view");
        View rootView = inflater.inflate(R.layout.poster_display_fragment, container, false);
        gridview = (GridView) rootView.findViewById(R.id.grid_view);
        mAdapter = new MovieAdapter(getActivity(), movies);
        gridview.setAdapter(mAdapter);
        if (savedInstanceState != null) {
            //get back your data and populate the adapter here
            ArrayList<MovieDetails> movies_saved =  savedInstanceState.getParcelableArrayList(MOVIES_KEY);
            //.....
        } else {
            updateMovieInfo();
        }
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                MovieDetails details = movies.get(position);
                Long movieId = details.getMovieId();
                String posterUrl = details.getPosterPath();
                String name = details.getOriginalTitle();
                String plot = details.getPlot();
                String release_date = details.getReleaseDate();
                Double rating = details.getUserRating();
                Intent i = new Intent(getActivity(), DetailActivity.class);
                i.putExtra("id", movieId);
                i.putExtra("name", name);
                i.putExtra("plot", plot);
                i.putExtra("url", posterUrl);
                i.putExtra("date", release_date);
                i.putExtra("rating", rating);
                startActivity(i);
            }
        });
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.v(LOG_TAG, "In frag's on save instance state ");
        outState.putParcelableArrayList(MOVIES_KEY, movies);
    }

    private void updateMovieInfo() {
        FetchMovieDetails movieTask = new FetchMovieDetails (this.getActivity(), mAdapter);
        movieTask.execute(onSelection(getPosition()));
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
        //onSelection(position);
    }

    private String onSelection(int position) {
        String sortOrder = null;
        switch (position){

            case 0:// sort movies by most popular (default)
                sortOrder = getString(R.string.pref_order_popular);
                break;

            case 1://sort movies by rating
                sortOrder = getString(R.string.pref_order_rating);
                break;

            //case 2:// display favorites
                //sortOrder = "other";

        }
        return sortOrder;
    }

    public static MovieDetails getMovie(int position){
        return movies.get(position);
    }
}


