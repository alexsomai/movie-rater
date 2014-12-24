package org.ubb.cluj.movierater.web.commandobject;

/**
 * Created by somai on 21.12.2014.
 */
public class SearchFilter {

    private int noPages;
    private int page;
    private String title;
    private String sort;
    private String order;
    private Long[] category = {};

    public Long[] getCategory() {
        return category;
    }

    public void setCategory(Long[] category) {
        this.category = category;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public int getNoPages() {
        return noPages;
    }

    public void setNoPages(int noPages) {
        if (this.page > noPages - 1) {
            this.page = noPages - 1;
        }
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
        this.page = page < 0 ? 0 : page;
    }

}
