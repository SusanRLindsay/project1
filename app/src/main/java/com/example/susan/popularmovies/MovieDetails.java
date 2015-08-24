package com.example.susan.popularmovies;

/**
 * Created by Susan on 8/24/2015.
 */
public class MovieDetails {
    public static final String BASE_URL = "http://image.tmdb.org/t/p/";
    public static final String THUMBNAIL_SIZE = "w92";
    public static final String POSTER_SIZE = "w500";
    private String originalTitle;
    private String overview;
    private String posterPath;
    private double userRating;
    private String releaseDate;
    int image;

    public MovieDetails(){

    }

    public MovieDetails(String title, String plot, String path, double rating, String date, int drawableRef){

        originalTitle = title;
        overview = plot;
        posterPath = path;
        userRating = rating;
        releaseDate = date;
        image = drawableRef;
    }

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

    public double getUserRating() {
        return userRating;
    }

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
        return  " title: " + originalTitle +
                " plot: " + overview +
                " poster path: " + posterPath +
                " release date: " + releaseDate +
                " user rating: " + userRating;
    }

}


