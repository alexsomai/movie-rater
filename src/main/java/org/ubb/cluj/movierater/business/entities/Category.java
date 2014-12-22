package org.ubb.cluj.movierater.business.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "categories")
public class Category implements java.io.Serializable {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(length = 32)
    private String genre;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "categories")
    private Set<Movie> movies = new HashSet<>(0);

    public Category() {
    }

    public Category(String genre) {
        this.genre = genre;
    }

    public Category(String genre, Set<Movie> movies) {
        this.genre = genre;
        this.movies = movies;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Set<Movie> getMovies() {
        return this.movies;
    }

    public void setMovies(Set<Movie> movies) {
        this.movies = movies;
    }

}
