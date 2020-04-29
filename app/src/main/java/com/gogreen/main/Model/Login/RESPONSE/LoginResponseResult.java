package com.gogreen.main.Model.Login.RESPONSE;

import com.google.gson.annotations.SerializedName;

public class LoginResponseResult {
    @SerializedName("id")
    String id;

    @SerializedName("name")
    String name;

    @SerializedName("email")
    String email;

    @SerializedName("phone_number")
    String phone_number;

    @SerializedName("is_payment")
    String is_payment;

   /* @SerializedName("device_token")
    String device_token;
*/
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

   /* public String getDevice_token() {
        return device_token;
    }

    public void setDevice_token(String device_token) {
        this.device_token = device_token;
    }*/

    public String getIs_phone_verified() {
        return is_phone_verified;
    }

    public String getIs_payment() {
        return is_payment;
    }

    public void setIs_payment(String is_payment) {
        this.is_payment = is_payment;
    }

    public void setIs_phone_verified(String is_phone_verified) {
        this.is_phone_verified = is_phone_verified;


    }
}
