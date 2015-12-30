package org.ubb.cluj.movierater.business.model;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * @author Alexandru Somai
 *         date 15.12.2014
 */
@Embeddable
public class MovieAccountId implements Serializable {

    private Account account;
    private Movie movie;

    @ManyToOne
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @ManyToOne
    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MovieAccountId that = (MovieAccountId) o;

        if (account != null ? !account.equals(that.account) : that.account != null) return false;
        if (movie != null ? !movie.equals(that.movie) : that.movie != null)
            return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = (account != null ? account.hashCode() : 0);
        result = 31 * result + (movie != null ? movie.hashCode() : 0);
        return result;
    }

}
