package com.gogreen.main.Model.Orders.RESPONSE;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderResponseResult {
    @SerializedName("active_package")
    private List<ActiveOrder> activeOrders;

    @SerializedName("expired_package")
    private List<ExpiredOrder> expiredOrders;

    public List<ActiveOrder> getActiveOrders() {
        return activeOrders;
    }

    public void setActiveOrders(List<ActiveOrder> activeOrders) {
        this.activeOrders = activeOrders;
    }

    public List<ExpiredOrder> getExpiredOrders() {
        return expiredOrders;
    }

    public void setExpiredOrders(List<ExpiredOrder> expiredOrders) {
        this.expiredOrders = expiredOrders;
    }
}
