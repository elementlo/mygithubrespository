package net.wash8.classbean;

import java.util.List;

/**
 * Created by Lawliet on 2014/12/15.
 */
public class HomeADPic {
    private List<HomeADItems> Items;
    private String TotalPages,PageNumber,PageSize;

    public List<HomeADItems> getItems() {
        return Items;
    }

    public void setItems(List<HomeADItems> items) {
        Items = items;
    }

    public String getTotalPages() {
        return TotalPages;
    }

    public void setTotalPages(String totalPages) {
        TotalPages = totalPages;
    }

    public String getPageNumber() {
        return PageNumber;
    }

    public void setPageNumber(String pageNumber) {
        PageNumber = pageNumber;
    }

    public String getPageSize() {
        return PageSize;
    }

    public void setPageSize(String pageSize) {
        PageSize = pageSize;
    }
}
