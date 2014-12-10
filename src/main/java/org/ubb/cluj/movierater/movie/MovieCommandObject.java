package org.ubb.cluj.movierater.movie;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by somai on 10.12.2014.
 */
public class MovieCommandObject {

    private static final String TITLE_NOT_BLANK = "{message.movie.title.not.blank}";
    private static final String DESCRIPTION_NOT_BLANK = "{message.movie.description.not.blank}";

    @NotBlank(message = MovieCommandObject.TITLE_NOT_BLANK)
    private String title;

    @NotBlank(message = MovieCommandObject.DESCRIPTION_NOT_BLANK)
    private String description;

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