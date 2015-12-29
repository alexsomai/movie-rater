package org.ubb.cluj.movierater.web.commandobject;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;
import org.ubb.cluj.movierater.business.model.Movie;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.util.Date;
import java.util.List;

/**
 * Created by somai on 10.12.2014.
 */
public class MovieCommandObject {

    private static final String TITLE_NOT_BLANK = "{message.movie.title.notBlank}";
    private static final String TITLE_LENGTH_TO_LONG = "{message.movie.title.toLong}";
    private static final String DESCRIPTION_NOT_BLANK = "{message.movie.description.notBlank}";
    private static final String RELEASE_DATE_NOT_BLANK = "{message.movie.releaseDate.notBlank}";
    private static final String RELEASE_DATE_NOT_PAST = "{message.movie.releaseDate.notPast}";
    private static final String GENRES_NOT_EMPTY = "{message.movie.genres.notEmpty}";

    private Long id;

    @NotBlank(message = MovieCommandObject.TITLE_NOT_BLANK)
    @Length(max = 255, message = MovieCommandObject.TITLE_LENGTH_TO_LONG)
    private String title;

    @NotBlank(message = MovieCommandObject.DESCRIPTION_NOT_BLANK)
    private String description;

    private String posterFile;

    @NotNull(message = MovieCommandObject.RELEASE_DATE_NOT_BLANK)
    @Past(message = MovieCommandObject.RELEASE_DATE_NOT_PAST)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date releaseDate;

    private Integer numberOfRatings;

    private String rate;

    @NotEmpty(message = MovieCommandObject.GENRES_NOT_EMPTY)
    private Long[] genreIds;

    private List<String> genreNames;

    public MovieCommandObject() {
        super();
    }

    public List<String> getGenreNames() {
        return genreNames;
    }

    public void setGenreNames(List<String> genreNames) {
        this.genreNames = genreNames;
    }

    public Long[] getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(Long[] genreIds) {
        this.genreIds = genreIds;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public Integer getNumberOfRatings() {
        return numberOfRatings;
    }

    public void setNumberOfRatings(Integer numberOfRatings) {
        this.numberOfRatings = numberOfRatings;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getPosterFile() {
        return posterFile;
    }

    public void setPosterFile(String posterFile) {
        this.posterFile = posterFile;
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

    public Movie createMovie() {
        return new Movie(title, description, posterFile, releaseDate);
    }
}
