package com.example.susan.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by Susan on 8/24/2015.
 */
public class DetailActivity extends FragmentActivity {
    public DetailActivity() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container2, new DetailFragment())
                    .commit();
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class DetailFragment extends Fragment {

        public static final String LOG_TAG = DetailFragment.class.getSimpleName();
        public MovieDetails details;
        public DetailFragment() {
            setHasOptionsMenu(true);
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Get the message from the intent
            Intent intent = getActivity().getIntent();
            View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
            if (intent != null) {
                String url = intent.getExtras().getString("url");
                ImageView imageView = (ImageView) rootView.findViewById(R.id.thumbnail_view);
                Picasso.with(getActivity().getBaseContext()).load(MovieDetails.BASE_URL + MovieDetails.THUMBNAIL_SIZE + url).into(imageView);
                String name = intent.getExtras().getString("name");
                ((TextView) rootView.findViewById(R.id.movie_name))
                        .setText(name);
                String plot = intent.getExtras().getString("plot");
                ((TextView) rootView.findViewById(R.id.movie_plot))
                        .setText(plot);
                String date = intent.getExtras().getString("date");
                ((TextView) rootView.findViewById(R.id.release_date))
                        .setText(date);
                Double user_rating = intent.getExtras().getDouble("rating");
                ((TextView) rootView.findViewById(R.id.rating))
                        .setText(user_rating.toString());
            }
            return rootView;
        }
    }
}
