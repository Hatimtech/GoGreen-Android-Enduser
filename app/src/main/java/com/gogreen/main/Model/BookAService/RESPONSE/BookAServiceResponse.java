package com.gogreen.main.Model.BookAService.RESPONSE;

import com.gogreen.main.Model.BaseResponse;
import com.google.gson.annotations.SerializedName;

import java.util.Collection;
import java.util.List;

public class BookAServiceResponse extends BaseResponse {


    @SerializedName("result")
    List<BookAServiceResponseResult> result;

    public List<BookAServiceResponseResult> getResult() {
        return result;
    }

    public void setResult(List<BookAServiceResponseResult> result) {
        this.result = result;
    }


}
