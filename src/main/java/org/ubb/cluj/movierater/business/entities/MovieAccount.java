package org.ubb.cluj.movierater.business.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by somai on 14.12.2014.
 */
@Entity
@Table(name = "movie_account")
@AssociationOverrides({
        @AssociationOverride(name = "pk.accounts",
                joinColumns = @JoinColumn(name = "ACCOUNT_ID")),
        @AssociationOverride(name = "pk.movies",
                joinColumns = @JoinColumn(name = "MOVIE_ID"))})
@NamedQuery(name = MovieAccount.GET_RATING_INFO, query = "SELECT ma FROM MovieAccount ma WHERE ma.pk.account = :account AND ma.pk.movie = :movie")
public class MovieAccount implements Serializable {

    public static final String GET_RATING_INFO = "MovieAccount.getRating";
    private MovieAccountId pk = new MovieAccountId();

    @Column(name = "stars", precision = 1, scale = 1)
    private Double stars;

    @Temporal(TemporalType.DATE)
    @Column(name = "RATED_AT", nullable = false, length = 10)
    private Date ratedAt;

    @EmbeddedId
    public MovieAccountId getPk() {
        return pk;
    }

    public void setPk(MovieAccountId pk) {
        this.pk = pk;
    }

    @Transient
    public Account getAccount() {
        return getPk().getAccount();
    }

    public void setAccount(Account account) {
        getPk().setAccount(account);
    }

    @Transient
    public Movie getMovie() {
        return getPk().getMovie();
    }

    public void setMovie(Movie movie) {
        getPk().setMovie(movie);
    }

    public Double getStars() {
        return stars;
    }

    public void setStars(Double stars) {
        this.stars = stars;
    }

    public Date getRatedAt() {
        return ratedAt;
    }

    public void setRatedAt(Date ratedAt) {
        this.ratedAt = ratedAt;
    }

    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        MovieAccount that = (MovieAccount) o;

        if (getPk() != null ? !getPk().equals(that.getPk())
                : that.getPk() != null)
            return false;

        return true;
    }

    public int hashCode() {
        return (getPk() != null ? getPk().hashCode() : 0);
    }
}
