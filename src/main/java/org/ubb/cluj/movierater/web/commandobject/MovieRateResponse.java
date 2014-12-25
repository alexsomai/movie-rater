package org.ubb.cluj.movierater.web.commandobject;

/**
 * Created by somai on 20.12.2014.
 */
public class MovieRateResponse {

    private String ratedAt;
    private String userRate;
    private String movieRate;
    private boolean success;
    private int ratings;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRatedAt() {
        return ratedAt;
    }

    public void setRatedAt(String ratedAt) {
        this.ratedAt = ratedAt;
    }

    public String getUserRate() {
        return userRate;
    }

    public void setUserRate(String userRate) {
        this.userRate = userRate;
    }

    public String getMovieRate() {
        return movieRate;
    }

    public void setMovieRate(String movieRate) {
        this.movieRate = movieRate;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getRatings() {
        return ratings;
    }

    public void setRatings(int ratings) {
        this.ratings = ratings;
    }
}
