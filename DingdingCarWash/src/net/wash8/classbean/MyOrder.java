package net.wash8.classbean;

import java.util.List;

/**
 * Created by ncb-user on 2015/1/12.
 */
public class MyOrder {
    private List<OrderItems> Items;

    public List<OrderItems> getItems() {
        return Items;
    }

    public void setItems(List<OrderItems> items) {
        Items = items;
    }

    @Override
    public String toString() {
        return "MyOrder{" +
                "Items=" + Items +
                '}';
    }

}
