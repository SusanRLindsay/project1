package com.example.susan.popularmovies;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Susan on 8/24/2015.
 */
public class FetchMovieDetails extends AsyncTask<String, Void, ArrayList<MovieDetails>> {
    private final String LOG_TAG = FetchMovieDetails.class.getSimpleName();
    private boolean DEBUG = true;
    public static final String BASE_URL = "http://api.themoviedb.org/3/discover/movie?";
    public static final String SORT_POPULAR_PARAM = "sort_by=popularity.desc";
    public static final String SORT_RATING_PARAM = "sort_by=vote_average.desc";
    public static final String API_KEY = "&api_key=f0d4a47494b3cf5f3e48649bc40b99c5";
    ArrayList<MovieDetails> results = new ArrayList<>();
    private final Context mContext;
    private MovieAdapter mAdapter;

    public FetchMovieDetails(Context context, MovieAdapter adapter){
        mContext = context;
        mAdapter = adapter;
    }



    @Override
    protected ArrayList<MovieDetails> doInBackground(String... params) {

        String sortParameter = SORT_POPULAR_PARAM;
        String sortOrder = params[0];
        Log.v(LOG_TAG, "starting to aquire movie info");
//        SharedPreferences sharedPrefs =
//                PreferenceManager.getDefaultSharedPreferences(mContext);
//        String sortOrder = sharedPrefs.getString(
//                mContext.getString(R.string.pref_order_key),
//                mContext.getString(R.string.pref_order_popular));

        if (sortOrder.equals(mContext.getString(R.string.pref_order_rating))) {
            sortParameter = SORT_RATING_PARAM;
        } else if (!sortOrder.equals(mContext.getString(R.string.pref_order_popular))) {
            Log.d(LOG_TAG, "Sort order not found: " + sortOrder);
        }
        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        //Will contain the raw JSON response as a string.
        String jsonStr;
        try {
            // Construct the URL for the  themoviedb.org query
            //                Uri builtUri = Uri.parse(BASE_URL).buildUpon()
            //                        .appendEncodedPath(sortParameter)
            //                        .appendEncodedPath(API_KEY).build();
            String urlString = BASE_URL + sortParameter + API_KEY;

            URL url = new URL(urlString);

            // Create the request to The Movie Database, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            jsonStr = buffer.toString();
            try {
                Log.v(LOG_TAG, "sending movie info to parser");
                return getMovieDataFromJson(jsonStr);
            } catch (JSONException e) {
                Log.e(LOG_TAG, "Error ", e);
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            // If the code didn't successfully get the weather data, there's no point in attemping
            // to parse it.
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }
        return null;
    }

    private ArrayList<MovieDetails> getMovieDataFromJson(String jsonStr) throws JSONException {
        //     These are the names of the JSON objects that need to be extracted.
        final String POSTER_BASE_URL = " http://image.tmdb.org/t/p/w185";
        final String MDB_RESULTS = "results";
        final String MDB_TITLE = "original_title";
        final String MDB_PLOT = "overview";
        final String MDB_POSTER_PATH = "poster_path";
        final String MDB_USER_RATING = "vote_average";
        final String MDB_RELEASE_DATE = "release_date";
        final String MDB_MOVIE_ID = "id";

        Log.v(LOG_TAG, "beginning movie info parse");

        JSONObject movieDetailJson = new JSONObject(jsonStr);
        JSONArray movieDetailJsonJSONArray = movieDetailJson.getJSONArray(MDB_RESULTS);


        for (int i = 0; i < movieDetailJsonJSONArray.length(); i++) {
            long id;
            String title;
            String plot;
            String poster_path;
            double user_rating;
            String release_date;

            // Get the JSON object representing the movie
            JSONObject movieJSONObj = movieDetailJsonJSONArray.getJSONObject(i);
            id = movieJSONObj.getLong(MDB_MOVIE_ID);
            title = movieJSONObj.getString(MDB_TITLE);
            plot = movieJSONObj.getString(MDB_PLOT);
            poster_path = movieJSONObj.getString(MDB_POSTER_PATH);
            user_rating = movieJSONObj.getDouble(MDB_USER_RATING);
            release_date = movieJSONObj.getString(MDB_RELEASE_DATE);
            results.add(new MovieDetails(id,title,plot, poster_path,user_rating,release_date,i));

            Log.v(LOG_TAG, "JSON parse: title " + title + " url: " + poster_path);
        }
        Log.v(LOG_TAG, "returning movie info parse");
        return results;
    }

    @Override
    protected void onPostExecute(ArrayList<MovieDetails> result) {

        Log.v(LOG_TAG, "on postexecute of movie info");
        mAdapter.setMovies(result);
    }
}
