package org.ubb.cluj.movierater.business.entities;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by somai on 10.12.2014.
 */
@Entity
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty
    @Column(unique = true)
    private String title;

    @NotEmpty
    private String description;

//    private String image;
//
//    private Date releaseDate;

    public Movie(String title, String description) {
        this.title = title;
        this.description = description;
    }

//    public Movie(String title, String description, String image, Date releaseDate) {
//        this(title, description);
//        this.image = image;
//        this.releaseDate = releaseDate;
//    }

    public Long getId() {
        return id;
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
