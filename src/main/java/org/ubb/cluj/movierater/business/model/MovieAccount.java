package org.ubb.cluj.movierater.business.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Alexandru Somai
 *         date 14.12.2014
 */
@Entity
@Table(name = "movies_to_accounts")
@AssociationOverrides({
        @AssociationOverride(name = "pk.account",
                joinColumns = @JoinColumn(name = "account_id")),
        @AssociationOverride(name = "pk.movie",
                joinColumns = @JoinColumn(name = "movie_id"))})
@NamedQuery(name = MovieAccount.GET_RATING_INFO, query = "SELECT ma FROM MovieAccount ma WHERE ma.pk.account = :account AND ma.pk.movie = :movie")
public class MovieAccount implements Serializable {

    public static final String GET_RATING_INFO = "MovieAccount.getRating";
    private MovieAccountId pk = new MovieAccountId();

    private BigDecimal stars;

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

    @Column(precision = 10, scale = 2)
    public BigDecimal getStars() {
        return stars;
    }

    public void setStars(BigDecimal stars) {
        this.stars = stars;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "rated_at", nullable = false, length = 10)
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