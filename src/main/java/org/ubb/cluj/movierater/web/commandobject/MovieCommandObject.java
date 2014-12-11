package org.ubb.cluj.movierater.web.commandobject;

import org.hibernate.validator.constraints.NotBlank;
import org.ubb.cluj.movierater.business.entities.Movie;

/**
 * Created by somai on 10.12.2014.
 */
public class MovieCommandObject {

    private static final String TITLE_NOT_BLANK = "{message.movie.title.not.blank}";
    private static final String DESCRIPTION_NOT_BLANK = "{message.movie.description.not.blank}";

    private Long id;

    @NotBlank(message = MovieCommandObject.TITLE_NOT_BLANK)
    private String title;

    @NotBlank(message = MovieCommandObject.DESCRIPTION_NOT_BLANK)
    private String description;

    private String image;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public Movie createMovie() {
        return new Movie(getTitle(), getDescription());
    }
}
