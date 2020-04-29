package com.gogreen.main.Model.AddUpcoming_Renewal_Service.RESPONSE;

import com.google.gson.annotations.SerializedName;

public class AddUpcomingRenewalServiceResponseWrapper {

    @SerializedName("data")
    AddUpcomingRenewalServiceResponse response;

    public AddUpcomingRenewalServiceResponse getResponse() {
        return response;
    }

    public void setResponse(AddUpcomingRenewalServiceResponse response) {
        this.response = response;
    }
}
