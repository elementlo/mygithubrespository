package net.wash8.classbean;

import java.util.List;

/**
 * Created by ncb-user on 2015/1/14.
 */
public class Coupons {
    private List<CouponItems> Items;
    private String TotalPages,PageNumber,PageSize;

    public Coupons() {
        super();
    }

    public List<CouponItems> getItems() {
        return Items;
    }

    public void setItems(List<CouponItems> items) {
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
