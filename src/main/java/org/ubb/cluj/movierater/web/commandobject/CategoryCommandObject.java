package org.ubb.cluj.movierater.web.commandobject;

/**
 * Created by somai on 22.12.2014.
 */
public class CategoryCommandObject {

    private Long id;
    private String genre;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
