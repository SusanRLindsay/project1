package com.example.susan.popularmovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Susan on 8/24/2015.
 */
public class MovieAdapter extends ArrayAdapter<MovieDetails> {

    private final String LOG_TAG = MovieAdapter.class.getSimpleName();
    Context mContext;
    ViewHolder viewHolder;
    ArrayList<MovieDetails> movies = new ArrayList<>();


    // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
    // the second argument is used when the ArrayAdapter is populating a single TextView.
    // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
    // going to use this second argument, so it can be any value. Here, we used 0.
    public MovieAdapter(Context context, ArrayList<MovieDetails> movies){
        super(context, R.layout.grid_item_layout, movies);
        this.movies = movies;
        mContext = context;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        MovieDetails details = getItem(position);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.grid_item_layout, parent, false);


            // initialize the view holder
            viewHolder = new ViewHolder();
            viewHolder.moviePoster = (ImageView) convertView.findViewById(R.id.grid_image);
            convertView.setTag(viewHolder);
        }else {
            // recycle the already inflated view
            viewHolder = (ViewHolder) convertView.getTag();
        }
        String posterUrl = details.getPosterPath();
        //Picasso.with(mContext).setLoggingEnabled(true);
        Picasso.with(mContext)
                .load(MovieDetails.BASE_URL + MovieDetails.POSTER_SIZE + posterUrl)
                .into((ImageView) viewHolder.moviePoster);
        return convertView;
    }



    public void setMovies(ArrayList<MovieDetails> movies){
        if (movies != null) {
            this.clear();
            for (MovieDetails details : movies) {
                this.add(details);
            }
            notifyDataSetChanged();
        }
    }

    public MovieDetails getMovie(int index){
        return (MovieDetails)movies.get(index);
    }

    static class ViewHolder {

        ImageView moviePoster;
    }
}
