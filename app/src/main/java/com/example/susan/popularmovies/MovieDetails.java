package com.example.susan.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Susan on 8/24/2015.
 */
public class MovieDetails implements Parcelable {
    public static final String BASE_URL = "http://image.tmdb.org/t/p/";
    public static final String THUMBNAIL_SIZE = "w92";
    public static final String POSTER_SIZE = "w500";

    private long movieId;
    private String originalTitle;
    private String overview;
    private String posterPath;
    private double userRating;
    private String releaseDate;
    int image;

    public MovieDetails(){

    }

    public MovieDetails(Long id,String title, String plot, String path, double rating, String date, int drawableRef){
        movieId = id;
        originalTitle = title;
        overview = plot;
        posterPath = path;
        userRating = rating;
        releaseDate = date;
        image = drawableRef;
    }

    public MovieDetails(Parcel in) {
        movieId = in.readLong();
        originalTitle = in.readString();
        overview = in.readString();
        posterPath = in.readString();
        userRating = in.readDouble();
        releaseDate = in.readString();
        image = in.readInt();
    }

    public static final Parcelable.Creator<MovieDetails> CREATOR = new Parcelable.Creator<MovieDetails>() {
        @Override
        public MovieDetails createFromParcel(Parcel in) {
            return new MovieDetails(in);
        }

        @Override
        public MovieDetails[] newArray(int size) {
            return new MovieDetails[size];
        }
    };

    public long getMovieId() { return movieId; }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getPlot() {
        return overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public int getImage() { return image; }

    public double getUserRating() { return userRating; }

    public void setMovieId(long movieId) { this.movieId = movieId;}

    public void setOriginalTitle(String original_title) {
        this.originalTitle = original_title;
    }

    public void setPlot(String plot) {
        overview = plot;
    }

    public void setPosterPath(String poster_path) {
        this.posterPath = poster_path;
    }

    public void setReleaseDate(String release_date) {
        this.releaseDate = release_date;
    }

    public void setUserRating(double user_rating) {
        this.userRating = user_rating;
    }

    public String getMovieDetails(){
        return  "id: " + movieId +
                " title: " + originalTitle +
                " plot: " + overview +
                " poster path: " + posterPath +
                " release date: " + releaseDate +
                " user rating: " + userRating;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(movieId);
        dest.writeString(originalTitle);
        dest.writeString(overview);
        dest.writeString(posterPath);
        dest.writeDouble(userRating);
        dest.writeString(releaseDate);
        dest.writeInt(image);
    }
}


