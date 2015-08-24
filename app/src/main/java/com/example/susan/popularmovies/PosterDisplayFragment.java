package com.example.susan.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Susan on 8/24/2015.
 */
public class PosterDisplayFragment extends Fragment {

    private final String LOG_TAG = PosterDisplayFragment.class.getSimpleName();
    public MovieAdapter mAdapter;
    List<MovieDetails> movies = new ArrayList<>();
    GridView gridview;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.poster_fragment_menu, menu);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View rootView = inflater.inflate(R.layout.poster_display_fragment, container, false);
        mAdapter = new MovieAdapter(getActivity(), movies);
        gridview = (GridView) rootView.findViewById(R.id.grid_view);
        gridview.setAdapter(mAdapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                MovieDetails details = movies.get(position);
                String posterUrl = details.getPosterPath();
                String name = details.getOriginalTitle();
                String plot = details.getPlot();
                String release_date = details.getReleaseDate();
                Double rating = details.getUserRating();
                Intent i = new Intent(getActivity(), DetailActivity.class);
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
    public void onStart(){
        super.onStart();
        updateMovieInfo();
    }

    private void updateMovieInfo() {
        FetchMovieDetails movieTask = new FetchMovieDetails (this.getActivity(), mAdapter);
        movieTask.execute();
    }
}
