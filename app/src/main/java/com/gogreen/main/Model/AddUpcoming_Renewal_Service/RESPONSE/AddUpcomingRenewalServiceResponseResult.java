package com.gogreen.main.Model.AddUpcoming_Renewal_Service.RESPONSE;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AddUpcomingRenewalServiceResponseResult {

    @SerializedName("services")
    List<UpcomingServices> upcomingServices;
    @SerializedName("upcoming_renewals")
    List<UpcomingRenewals> upcomingRenewals;
    @SerializedName("coupans")
    List<Coupons> coupons;

    public List<Coupons> getCoupons() {
        return coupons;
    }

    public void setCoupons(List<Coupons> coupons) {
        this.coupons = coupons;
    }

    public List<UpcomingServices> getUpcomingServices() {
        return upcomingServices;
    }

    public void setUpcomingServices(List<UpcomingServices> upcomingServices) {
        this.upcomingServices = upcomingServices;
    }

    public List<UpcomingRenewals> getUpcomingRenewals() {
        return upcomingRenewals;
    }

    public void setUpcomingRenewals(List<UpcomingRenewals> upcomingRenewals) {
        this.upcomingRenewals = upcomingRenewals;
    }
}
