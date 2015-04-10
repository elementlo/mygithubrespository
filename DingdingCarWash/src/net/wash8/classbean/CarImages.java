package net.wash8.classbean;

import java.io.Serializable;

/**
 * Created by ncb-user on 2015/1/12.
 */
public class CarImages implements Serializable {
    private String OrderID,Source,Thumbnail,Before,ID;

    public String getOrderID() {
        return OrderID;
    }

    public void setOrderID(String orderID) {
        OrderID = orderID;
    }

    public String getSource() {
        return Source;
    }

    public void setSource(String source) {
        Source = source;
    }

    public String getThumbnail() {
        return Thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        Thumbnail = thumbnail;
    }

    public String getBefore() {
        return Before;
    }

    public void setBefore(String before) {
        Before = before;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
}
