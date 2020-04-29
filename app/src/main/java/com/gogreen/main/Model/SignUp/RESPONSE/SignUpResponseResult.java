package com.gogreen.main.Model.SignUp.RESPONSE;

import com.google.gson.annotations.SerializedName;

public class SignUpResponseResult {
    @SerializedName("id")
   String id;

    @SerializedName("name")
    String name;

    @SerializedName("email")
    String email;

    @SerializedName("phone_number")
    String phone_number;

    @SerializedName("is_phone_verified")
    String is_phone_verified;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getIs_phone_verified() {
        return is_phone_verified;
    }

    public void setIs_phone_verified(String is_phone_verified) {
        this.is_phone_verified = is_phone_verified;
    }
}
