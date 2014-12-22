package org.ubb.cluj.movierater.business.entities;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by somai on 10.12.2014.
 */
@Entity
@Table(name = "movies")
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

    @NotEmpty
    private String poster;

    @NotNull
    private Date releaseDate;

    @Column(name = "no_of_ratings")
    private Integer numberOfRatings = 0;

    @Column(precision = 10, scale = 2)
    private BigDecimal rate = new BigDecimal(0);

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.movie", cascade = CascadeType.ALL)
    private Set<MovieAccount> movieAccounts = new HashSet<>(0);

//    private Set<Category> categories = new HashSet<>(0);

    public Movie() {

    }

    public Movie(String title, String description, String poster, Date releaseDate) {
        this.description = description;
        this.title = title;
        this.poster = poster;
        this.releaseDate = releaseDate;
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

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

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

//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(name = "movies_to_categories",
//            joinColumns = {@JoinColumn(name = "category_id")},
//            inverseJoinColumns = {@JoinColumn(name = "movie_id")})
//    public Set<Category> getCategories() {
//        return categories;
//    }
//
//    public void setCategories(Set<Category> categories) {
//        this.categories = categories;
//    }
}
