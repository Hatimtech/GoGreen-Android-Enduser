package com.gogreen.main.Model.BookAService.RESPONSE;

import com.google.gson.annotations.SerializedName;

public class BookAServiceResponseWrapper {

    @SerializedName("data")
    BookAServiceResponse response;

    public BookAServiceResponse getResponse() {
        return response;
    }

    public void setResponse(BookAServiceResponse response) {
        this.response = response;
    }


}
