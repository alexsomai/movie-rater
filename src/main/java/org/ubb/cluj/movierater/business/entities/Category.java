package org.ubb.cluj.movierater.business.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "categories")
public class Category implements Serializable, Comparable {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 32)
    private String genre;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "categories")
    private Set<Movie> movies = new HashSet<>(0);

    public Category() {
    }

    public Category(String genre) {
        this.genre = genre;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Long getId() {
        return id;
    }

    public Set<Movie> getMovies() {
        return this.movies;
    }

    public void setMovies(Set<Movie> movies) {
        this.movies = movies;
    }

    @Override
    public int compareTo(Object o) {
        Category category1 = this;
        Category category2 = (Category) o;
        if (category1.getGenre().equals(category2.getGenre())) {
            return 0;
        }
        if (category1.getGenre() == null) {
            return -1;
        }
        if (category2.getGenre() == null) {
            return 1;
        }

        return category1.getGenre().compareTo(category2.getGenre());
    }

}
