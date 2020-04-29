package com.gogreen.main.Model.AddUpcoming_Renewal_Service.RESPONSE;

import com.gogreen.main.Model.BaseResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AddUpcomingRenewalServiceResponse extends BaseResponse {


    @SerializedName("result")
    public AddUpcomingRenewalServiceResponseResult addUpcomingRenewalServiceResponseResults;

    public AddUpcomingRenewalServiceResponseResult getResults() {
        return addUpcomingRenewalServiceResponseResults;
    }

    public void setResults(AddUpcomingRenewalServiceResponseResult addUpcomingRenewalServiceResponseResults) {
        this.addUpcomingRenewalServiceResponseResults = addUpcomingRenewalServiceResponseResults;
    }
}
