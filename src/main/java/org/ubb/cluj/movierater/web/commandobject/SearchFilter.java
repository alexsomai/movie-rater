package org.ubb.cluj.movierater.web.commandobject;

/**
 * Created by somai on 21.12.2014.
 */
public class SearchFilter {

    private int noPages;
    private int page;
    private String title;

    public int getNoPages() {
        return noPages;
    }

    public void setNoPages(int noPages) {
        this.noPages = noPages;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

}
