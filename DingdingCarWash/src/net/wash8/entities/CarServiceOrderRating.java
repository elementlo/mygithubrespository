package net.wash8.entities;

/**
 * Created by lijie on 2014/12/19.
 */
public class CarServiceOrderRating {
    private int ID;
    private int orderID;
    private int punctualityRating;
    private int mannersRating;
    private int productivityRating;
    private int cleaningRating;
    private String comment;
    private long createdDateTime;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getPunctualityRating() {
        return punctualityRating;
    }

    public void setPunctualityRating(int punctualityRating) {
        this.punctualityRating = punctualityRating;
    }

    public int getMannersRating() {
        return mannersRating;
    }

    public void setMannersRating(int mannersRating) {
        this.mannersRating = mannersRating;
    }

    public int getProductivityRating() {
        return productivityRating;
    }

    public void setProductivityRating(int productivityRating) {
        this.productivityRating = productivityRating;
    }

    public int getCleaningRating() {
        return cleaningRating;
    }

    public void setCleaningRating(int cleaningRating) {
        this.cleaningRating = cleaningRating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public long getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(long createdDateTime) {
        this.createdDateTime = createdDateTime;
    }
}
