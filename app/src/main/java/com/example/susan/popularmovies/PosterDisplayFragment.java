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

    static private final String LOG_TAG = PosterDisplayFragment.class.getSimpleName();
    public static final String MOVIES_KEY = "movies";
    public static final String POSITION_KEY = "position";
    public static final String LAST_SORT_SELECTION = "last_sort";
    static private int position = 0;
    static private int lastSort = 0;
    public MovieAdapter mAdapter;
    static ArrayList<MovieDetails> movies = new ArrayList<>();
    GridView gridview;


    public static PosterDisplayFragment newInstance(int position) {
        Log.v(LOG_TAG, "in newInstance");
        PosterDisplayFragment f = new PosterDisplayFragment();
        Bundle args = new Bundle();
        args.putInt(POSITION_KEY, position);
        args.putInt(LAST_SORT_SELECTION, lastSort);
        args.putParcelableArrayList(MOVIES_KEY, movies);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        Log.v(LOG_TAG, "in onCreate");
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        Bundle args = getArguments();
        mAdapter = new MovieAdapter(getActivity(), movies);
        if (args != null) {
            this.setPosition(args.getInt(POSITION_KEY));
            this.setLastSort(args.getInt(LAST_SORT_SELECTION));
            movies = args.getParcelableArrayList(MOVIES_KEY);
            if ((movies.size() == 0)||(hasSortChanged())){
                updateMovieInfo();
                lastSort = position;
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.poster_fragment_menu, menu);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        Log.v(LOG_TAG, "In on create view");
        View rootView = inflater.inflate(R.layout.poster_display_fragment, container, false);
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

    private boolean hasSortChanged(){
        return (position != lastSort);
    }

    private void updateMovieInfo() {
        Log.v(LOG_TAG, "in update movie info");
        String sortMethod = onSelection(position);
        if (sortMethod != "other") {
            FetchMovieDetails movieTask = new FetchMovieDetails(this.getActivity(), mAdapter);
            movieTask.execute(sortMethod);
        }
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;

    }
    public static int getLastSort() {
        return lastSort;
    }

    public static void setLastSort(int lastSort) {
        PosterDisplayFragment.lastSort = lastSort;
    }

    private String onSelection(int position) {
        Log.v(LOG_TAG, "in onSelection");
        String sortOrder = null;
        switch (position){

            case 0:// sort movies by most popular (default)
                sortOrder = getString(R.string.pref_order_popular);
                break;

            case 1://sort movies by rating
                sortOrder = getString(R.string.pref_order_rating);
                break;

//            case 2:// display favorites
//                sortOrder = "other";
//                return sortOrder;

        }
        return sortOrder;
    }

}
