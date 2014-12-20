package org.ubb.cluj.movierater.business.entities;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by somai on 10.12.2014.
 */
@Entity
@Table(name = "movie")
public class Movie implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty
    @Column(unique = true)
    private String title;

    @Column(columnDefinition = "TEXT")
    @NotEmpty
    private String description;

    private String image;

    private Date releaseDate;

    @Column(name = "no_of_ratings")
    private Integer numberOfRatings = 0;

    @Column(precision = 10, scale = 2)
    private BigDecimal rate = new BigDecimal(0);


    //    cascade = CascadeType.ALL)
//    @JoinTable(name = "movie_account",
//            joinColumns = {@JoinColumn(name = "MOVIE_ID")},
//            inverseJoinColumns = {@JoinColumn(name = "ACCOUNT_ID")})
    @OneToMany(mappedBy = "pk.movie")
    private Set<MovieAccount> movieAccounts = new HashSet<>(0);

    public Movie() {

    }

    public Movie(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Set<MovieAccount> getMovieAccounts() {
        return movieAccounts;
    }

    public void setMovieAccounts(Set<MovieAccount> movieAccounts) {
        this.movieAccounts = movieAccounts;
    }

    public Integer getNumberOfRatings() {
        return numberOfRatings;
    }

    public void setNumberOfRatings(Integer numberOfRatings) {
        this.numberOfRatings = numberOfRatings;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    //    public Movie(String title, String description, String image, Date releaseDate) {
//        this(title, description);
//        this.image = image;
//        this.releaseDate = releaseDate;
//    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

//    public String getImage() {
//        return image;
//    }
//
//    public void setImage(String image) {
//        this.image = image;
//    }
//
//    public Date getReleaseDate() {
//        return releaseDate;
//    }
//
//    public void setReleaseDate(Date releaseDate) {
//        this.releaseDate = releaseDate;
//    }
}
