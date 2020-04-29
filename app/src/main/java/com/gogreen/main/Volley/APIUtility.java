package com.gogreen.main.Volley;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.gogreen.main.Model.AddCarBrand.REQUEST.AddCarBrandRequest;
import com.gogreen.main.Model.AddCarBrand.RESPONSE.AddCarBrandResponseWrapper;
import com.gogreen.main.Model.AddCarModel.REQUEST.AddCarModelRequest;
import com.gogreen.main.Model.AddCarModel.RESPONSE.AddCarModelResponseWrapper;
import com.gogreen.main.Model.AddUpcoming_Renewal_Service.REQUEST.AddUpcomingRenewalServiceRequest;
import com.gogreen.main.Model.AddUpcoming_Renewal_Service.RESPONSE.AddUpcomingRenewalServiceResponseWrapper;
import com.gogreen.main.Model.AutoRenewals.REQUEST.AutoRenewRequest;
import com.gogreen.main.Model.AutoRenewals.RESPONSE.AutoRenewResponseResult;
import com.gogreen.main.Model.AutoRenewals.RESPONSE.AutoRenewResponseWrapper;
import com.gogreen.main.Model.BookAService.REQUEST.BookAServiceRequest;
import com.gogreen.main.Model.BookAService.RESPONSE.BookAServiceResponseWrapper;
import com.gogreen.main.Model.CancleSubscription.REQUEST.CancelSubscriptionRequest;
import com.gogreen.main.Model.CancleSubscription.RESPONSE.CancelSubscriptionResponseWrapper;
import com.gogreen.main.Model.CarActivityDetail.REQUEST.CarActivityDetailRequest;
import com.gogreen.main.Model.CarActivityDetail.RESPONSE.CarActivityDetailResponseWrapper;
import com.gogreen.main.Model.CarBrand.REQUEST.CarBrandRequest;
import com.gogreen.main.Model.CarBrand.RESPONSE.CarBrandResponseWrapper;
import com.gogreen.main.Model.CarModel.REQUEST.CarModelRequest;
import com.gogreen.main.Model.CarModel.RESPONSE.CarModelResponseWrapper;
import com.gogreen.main.Model.ChangePassword.REQUEST.ChangePasswordRequest;
import com.gogreen.main.Model.ChangePassword.RESPONSE.ChangePasswordResponseWrapper;
import com.gogreen.main.Model.ChangePhoneNumber.REQUEST.ChangePhoneNumberRequest;
import com.gogreen.main.Model.ChangePhoneNumber.RESPONSE.ChangePhoneNumberResponseWrapper;
import com.gogreen.main.Model.CheckVersion.REQUEST.CheckVersionRequest;
import com.gogreen.main.Model.CheckVersion.RESPONSE.CheckVersionResponseWrapper;
import com.gogreen.main.Model.Coupon.REQUEST.CouponRequest;
import com.gogreen.main.Model.Coupon.RESPONSE.CouponResponseWrapper;
import com.gogreen.main.Model.CrewDetail.REQUEST.CrewDetailRequest;
import com.gogreen.main.Model.CrewDetail.RESPONSE.CrewDetailResponseWrapper;
import com.gogreen.main.Model.DeleteCarList.REQUEST.DeleteCarListRequest;
import com.gogreen.main.Model.DeleteCarList.RESPONSE.DeleteCarListResponseWrapper;
import com.gogreen.main.Model.EditCarDetail.REQUEST.EditCarDetailRequest;
import com.gogreen.main.Model.EditCarDetail.RESPONSE.EditCarDetailResponseWrapper;
import com.gogreen.main.Model.EnterCarDetail.REQUEST.EnterCarDetailRequest;
import com.gogreen.main.Model.EnterCarDetail.RESPONSE.EnterCarDetailResponseWrapper;
import com.gogreen.main.Model.ForgetPassword.REQUEST.ForgetPasswordRequest;
import com.gogreen.main.Model.ForgetPassword.RESPONSE.FgPasswordResponseWrapper;
import com.gogreen.main.Model.GetOTP.REQUEST.GetOTPRequest;
import com.gogreen.main.Model.GetOTP.RESPONSE.GetOTPResponseWrapper;
import com.gogreen.main.Model.GetOrderDetailFormNotification.REQUEST.GetOrderDetailFormNotificationRequest;
import com.gogreen.main.Model.GetOrderDetailFormNotification.RESPONSE.GetOrderDetailFormNotificationResponseWrapper;
import com.gogreen.main.Model.Login.REQUEST.LoginRequest;
import com.gogreen.main.Model.Login.RESPONSE.LoginResponseWrapper;
import com.gogreen.main.Model.OTP.REQUEST.OTPVerificationRequest;
import com.gogreen.main.Model.OTP.RESPONSE.OTPVerificationResponse;
import com.gogreen.main.Model.OTP.RESPONSE.OTPVerificationResponseWrapper;
import com.gogreen.main.Model.OrderPackageDetail.REQUEST.OrderPackageRequest;
import com.gogreen.main.Model.OrderPackageDetail.RESPONSE.OrderPackageResponseWrapper;
import com.gogreen.main.Model.Orders.REQUEST.OrderRequest;
import com.gogreen.main.Model.Orders.RESPONSE.OrderResponseWrapper;
import com.gogreen.main.Model.PhoneVerified.REQUEST.PhoneVerifiedRequest;
import com.gogreen.main.Model.PhoneVerified.RESPONSE.PhoneVerifiedResponseWrapper;
import com.gogreen.main.Model.RateCleaner.REQUEST.RateCleanerRequest;
import com.gogreen.main.Model.RateCleaner.RESPONSE.RateCleanerResponseWrapper;
import com.gogreen.main.Model.SelectCity.REQUEST.CityRequest;
import com.gogreen.main.Model.SelectCity.RESPONSE.CityResponseWrapper;
import com.gogreen.main.Model.SelectLocality.REQUEST.CityLocalityRequest;
import com.gogreen.main.Model.SelectLocality.RESPONSE.LocalityResponseWrapper;
import com.gogreen.main.Model.SelectPackage.REQUEST.SelectPackageRequest;
import com.gogreen.main.Model.SelectPackage.RESPONSE.SelectPackageResponseWrapper;
import com.gogreen.main.Model.SelectStreet.REQUEST.CityStreetRequest;
import com.gogreen.main.Model.SelectStreet.RESPONSE.StreetResponseWrapper;
import com.gogreen.main.Model.SendPaidCarDetail.REQUEST.SendCarPaidDetailRequest;
import com.gogreen.main.Model.SendPaidCarDetail.RESPONSE.SendPaidCarDetailResponseWrapper;
import com.gogreen.main.Model.SignUp.REQUEST.SignUpRequest;
import com.gogreen.main.Model.SignUp.RESPONSE.SignUpResponseWrapper;
import com.gogreen.main.Model.StopPackage.REQUEST.StopPackageRequest;
import com.gogreen.main.Model.UpdateProfile.REQUEST.UpdateRequest;
import com.gogreen.main.Model.UpdateProfile.RESPONSE.UpdateResponseWrapper;
import com.gogreen.main.Model.UpdateToken.REQUEST.UpdateTokenRequest;
import com.gogreen.main.Model.UpdateToken.RESPONSE.UpdateTokenResponseResult;
import com.gogreen.main.Model.UpdateToken.RESPONSE.UpdateTokenResponseWrapper;


public class APIUtility {

//    13.126.37.21 8
    //13.126.99. 14//

    public final String TAG = "VOLLEY ";
    public final String BASEURL = "http://13.126.37.218/gogreen/index.php/";



    public APIUtility(Context context) {

    }


    public void showDialog(Context context, boolean isDialog) {
        if (isDialog) {
            ProcessDialog.start(context);
        }
    }

    public void dismissDialog(boolean isDialog) {
        if (isDialog) {
            ProcessDialog.dismiss();
        }
    }

    public interface APIResponseListener<T> {
        void onReceiveResponse(T response);

        void onResponseFailed();

        void onStatusFalse(T response);
    }

    public void userLogin(Context context, LoginRequest loginRequest, final boolean showDialog, final APIResponseListener<LoginResponseWrapper> listener) {
        showDialog(context, showDialog);
        GenericRequest<LoginResponseWrapper> request = new GenericRequest<>(Request.Method.POST, BASEURL+"api_v2/", LoginResponseWrapper.class,

                loginRequest, new Response.Listener<LoginResponseWrapper>() {
            @Override
            public void onResponse(LoginResponseWrapper response) {
                if (response.getResponse().isSuccess()) {
                    listener.onReceiveResponse(response);
                    dismissDialog(showDialog);
                } else {
                    listener.onStatusFalse(response);
                    dismissDialog(showDialog);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onResponseFailed();
                dismissDialog(showDialog);
            }
        });
        AppController.getInstance().addToRequestQueue(request);
    }


    public void userSignUp(Context context, SignUpRequest signUpRequest, final boolean showDialog, final APIResponseListener<SignUpResponseWrapper> listener) {
        showDialog(context, showDialog);
        GenericRequest<SignUpResponseWrapper> request = new GenericRequest<>(Request.Method.POST, BASEURL+"api_v2/", SignUpResponseWrapper.class,
                signUpRequest, new Response.Listener<SignUpResponseWrapper>() {
            @Override
            public void onResponse(SignUpResponseWrapper response) {

                if (response.getResponse().isSuccess()) {

                    listener.onReceiveResponse(response);
                    dismissDialog(showDialog);
                } else {
                    listener.onStatusFalse(response);
                    dismissDialog(showDialog);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onResponseFailed();
                dismissDialog(showDialog);
            }
        });
        AppController.getInstance().addToRequestQueue(request);
    }


    public void getCity(Context context, CityRequest cityRequest, final boolean showDialog, final APIResponseListener<CityResponseWrapper> listener) {
        showDialog(context, showDialog);
        GenericRequest<CityResponseWrapper> request = new GenericRequest<CityResponseWrapper>(Request.Method.POST, BASEURL+"api_v2/", CityResponseWrapper.class, cityRequest, new Response.Listener<CityResponseWrapper>() {
            @Override
            public void onResponse(CityResponseWrapper response) {
                if (response.getResponse().isSuccess()) {
                    listener.onReceiveResponse(response);
                    dismissDialog(showDialog);
                } else {
                    listener.onStatusFalse(response);
                    dismissDialog(showDialog);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onResponseFailed();
                dismissDialog(showDialog);
            }
        });

        AppController.getInstance().addToRequestQueue(request);
    }


    public void getCityLocality(Context context, CityLocalityRequest cityLocalityRequest, final boolean showDialog, final APIResponseListener<LocalityResponseWrapper> listener) {
        showDialog(context, showDialog);
        GenericRequest<LocalityResponseWrapper> request = new GenericRequest<LocalityResponseWrapper>(Request.Method.POST, BASEURL+"api_v2/", LocalityResponseWrapper.class, cityLocalityRequest, new Response.Listener<LocalityResponseWrapper>() {
            @Override
            public void onResponse(LocalityResponseWrapper response) {
                if (response.getResponse().isSuccess()) {
                    listener.onReceiveResponse(response);
                    dismissDialog(showDialog);
                } else {
                    listener.onStatusFalse(response);
                    dismissDialog(showDialog);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onResponseFailed();
                dismissDialog(showDialog);
            }
        });

        AppController.getInstance().addToRequestQueue(request);
    }

    public void set_fgPassword(Context context, ForgetPasswordRequest forgetPasswordRequest, final boolean showDialog, final APIResponseListener<FgPasswordResponseWrapper> listener) {
        showDialog(context, showDialog);
        GenericRequest<FgPasswordResponseWrapper> request = new GenericRequest<FgPasswordResponseWrapper>(Request.Method.POST, BASEURL+"api_v2/", FgPasswordResponseWrapper.class, forgetPasswordRequest, new Response.Listener<FgPasswordResponseWrapper>() {
            @Override
            public void onResponse(FgPasswordResponseWrapper response) {
                if (response.getResponse().isSuccess()) {
                    listener.onReceiveResponse(response);
                    dismissDialog(showDialog);
                } else {
                    listener.onStatusFalse(response);
                    dismissDialog(showDialog);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onResponseFailed();
                dismissDialog(showDialog);
            }
        });

        AppController.getInstance().addToRequestQueue(request);
    }


    public void updateProfile(Context context, UpdateRequest updateRequest, final boolean showDialog, final APIResponseListener<UpdateResponseWrapper> listener) {
        showDialog(context, showDialog);
        GenericRequest<UpdateResponseWrapper> request = new GenericRequest<UpdateResponseWrapper>(Request.Method.POST, BASEURL+"api_v2/", UpdateResponseWrapper.class, updateRequest, new Response.Listener<UpdateResponseWrapper>() {
            @Override
            public void onResponse(UpdateResponseWrapper response) {
                if (response.getResponse().isSuccess()) {
                    listener.onReceiveResponse(response);
                    dismissDialog(showDialog);
                } else {
                    listener.onStatusFalse(response);
                    dismissDialog(showDialog);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onResponseFailed();
                dismissDialog(showDialog);
            }
        });

        AppController.getInstance().addToRequestQueue(request);
    }

    public void getCityStreet(Context context, CityStreetRequest cityStreetRequest, final boolean showDialog, final APIResponseListener<StreetResponseWrapper> listener) {
        showDialog(context, showDialog);
        GenericRequest<StreetResponseWrapper> request = new GenericRequest<StreetResponseWrapper>(Request.Method.POST, BASEURL+"api_v2/", StreetResponseWrapper.class, cityStreetRequest, new Response.Listener<StreetResponseWrapper>() {
            @Override
            public void onResponse(StreetResponseWrapper response) {
                if (response.getResponse().isSuccess()) {
                    listener.onReceiveResponse(response);
                    dismissDialog(showDialog);
                } else {
                    listener.onStatusFalse(response);
                    dismissDialog(showDialog);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onResponseFailed();
                dismissDialog(showDialog);
            }
        });

        AppController.getInstance().addToRequestQueue(request);
    }


    public void getPhoneVerified(Context context, PhoneVerifiedRequest phoneVerifiedRequest, final boolean showDialog, final APIResponseListener<PhoneVerifiedResponseWrapper> listener) {
        showDialog(context, showDialog);
        GenericRequest<PhoneVerifiedResponseWrapper> request = new GenericRequest<PhoneVerifiedResponseWrapper>(Request.Method.POST, BASEURL+"api_v2", PhoneVerifiedResponseWrapper.class, phoneVerifiedRequest, new Response.Listener<PhoneVerifiedResponseWrapper>() {
            @Override
            public void onResponse(PhoneVerifiedResponseWrapper response) {
                if (response.getResponse().isSuccess()) {
                    listener.onReceiveResponse(response);
                    dismissDialog(showDialog);
                } else {
                    listener.onStatusFalse(response);
                    dismissDialog(showDialog);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onResponseFailed();
                dismissDialog(showDialog);
            }
        });

        AppController.getInstance().addToRequestQueue(request);
    }


    public void sendOTP(Context context, GetOTPRequest phoneVerifiedRequest, final boolean showDialog, final APIResponseListener<GetOTPResponseWrapper> listener) {
        showDialog(context, showDialog);
        GenericRequest<GetOTPResponseWrapper> request = new GenericRequest<GetOTPResponseWrapper>(Request.Method.POST, BASEURL+"api_v2", GetOTPResponseWrapper.class, phoneVerifiedRequest, new Response.Listener<GetOTPResponseWrapper>() {
            @Override
            public void onResponse(GetOTPResponseWrapper response) {
                if (response.getResponse().isSuccess()) {
                    listener.onReceiveResponse(response);
                    dismissDialog(showDialog);
                } else {
                    listener.onStatusFalse(response);
                    dismissDialog(showDialog);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onResponseFailed();
                dismissDialog(showDialog);
            }
        });

        AppController.getInstance().addToRequestQueue(request);
    }


    public void changePhoneNumber(Context context, ChangePhoneNumberRequest changePhoneNumberRequest, final boolean showDialog, final APIResponseListener<ChangePhoneNumberResponseWrapper> listener) {
        showDialog(context, showDialog);
        GenericRequest<ChangePhoneNumberResponseWrapper> request = new GenericRequest<ChangePhoneNumberResponseWrapper>(Request.Method.POST, BASEURL+"api_v2/", ChangePhoneNumberResponseWrapper.class, changePhoneNumberRequest, new Response.Listener<ChangePhoneNumberResponseWrapper>() {
            @Override
            public void onResponse(ChangePhoneNumberResponseWrapper response) {
                if (response.getResponse().isSuccess()) {
                    listener.onReceiveResponse(response);
                    dismissDialog(showDialog);
                } else {
                    listener.onStatusFalse(response);
                    dismissDialog(showDialog);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onResponseFailed();
                dismissDialog(showDialog);
            }
        });

        AppController.getInstance().addToRequestQueue(request);
    }


    public void bookAService(Context context, BookAServiceRequest bookAServiceRequest, final boolean showDialog, final APIResponseListener<BookAServiceResponseWrapper> listener) {
        showDialog(context, showDialog);
        GenericRequest<BookAServiceResponseWrapper> request = new GenericRequest<BookAServiceResponseWrapper>(Request.Method.POST, BASEURL+"car_packages_v2/", BookAServiceResponseWrapper.class, bookAServiceRequest, new Response.Listener<BookAServiceResponseWrapper>() {
            @Override
            public void onResponse(BookAServiceResponseWrapper response) {
                if (response.getResponse().isSuccess()) {
                    listener.onReceiveResponse(response);
                    dismissDialog(showDialog);
                } else {
                    listener.onStatusFalse(response);
                    dismissDialog(showDialog);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onResponseFailed();
                dismissDialog(showDialog);
            }
        });

        AppController.getInstance().addToRequestQueue(request);
    }

    public void enterCarDetail(Context context, EnterCarDetailRequest enterCarDetailRequest, final boolean showDialog, final APIResponseListener<EnterCarDetailResponseWrapper> listener) {
        showDialog(context, showDialog);
        GenericRequest<EnterCarDetailResponseWrapper> request = new GenericRequest<EnterCarDetailResponseWrapper>(Request.Method.POST, BASEURL+"car_packages_v2/", EnterCarDetailResponseWrapper.class, enterCarDetailRequest, new Response.Listener<EnterCarDetailResponseWrapper>() {
            @Override
            public void onResponse(EnterCarDetailResponseWrapper response) {
                if (response.getResponse().isSuccess()) {
                    listener.onReceiveResponse(response);
                    dismissDialog(showDialog);
                } else {
                    listener.onStatusFalse(response);
                    dismissDialog(showDialog);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onResponseFailed();
                dismissDialog(showDialog);
            }
        });

        AppController.getInstance().addToRequestQueue(request);
    }

    public void getCarBrand(Context context, CarBrandRequest carBrandRequest, final boolean showDialog, final APIResponseListener<CarBrandResponseWrapper> listener) {
        showDialog(context, showDialog);
        GenericRequest<CarBrandResponseWrapper> request = new GenericRequest<CarBrandResponseWrapper>(Request.Method.POST, BASEURL+"car_packages_v2/", CarBrandResponseWrapper.class, carBrandRequest, new Response.Listener<CarBrandResponseWrapper>() {
            @Override
            public void onResponse(CarBrandResponseWrapper response) {
                if (response.getResponse().isSuccess()) {
                    listener.onReceiveResponse(response);
                    dismissDialog(showDialog);
                } else {
                    listener.onStatusFalse(response);
                    dismissDialog(showDialog);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onResponseFailed();
                dismissDialog(showDialog);
            }
        });

        AppController.getInstance().addToRequestQueue(request);
    }

    public void getCarModel(Context context, CarModelRequest carModelRequest, final boolean showDialog, final APIResponseListener<CarModelResponseWrapper> listener) {
        showDialog(context, showDialog);
        GenericRequest<CarModelResponseWrapper> request = new GenericRequest<CarModelResponseWrapper>(Request.Method.POST, BASEURL+"car_packages_v2/", CarModelResponseWrapper.class, carModelRequest, new Response.Listener<CarModelResponseWrapper>() {
            @Override
            public void onResponse(CarModelResponseWrapper response) {
                if (response.getResponse().isSuccess()) {
                    listener.onReceiveResponse(response);
                    dismissDialog(showDialog);
                } else {
                    listener.onStatusFalse(response);
                    dismissDialog(showDialog);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onResponseFailed();
                dismissDialog(showDialog);
            }
        });

        AppController.getInstance().addToRequestQueue(request);
    }

    public void addCarModel(Context context, AddCarModelRequest addCarModelRequest, final boolean showDialog, final APIResponseListener<AddCarModelResponseWrapper> listener) {
        showDialog(context, showDialog);
        GenericRequest<AddCarModelResponseWrapper> request = new GenericRequest<AddCarModelResponseWrapper>(Request.Method.POST, BASEURL+"car_packages_v2/", AddCarModelResponseWrapper.class, addCarModelRequest, new Response.Listener<AddCarModelResponseWrapper>() {
            @Override
            public void onResponse(AddCarModelResponseWrapper response) {
                if (response.getResponse().isSuccess()) {
                    listener.onReceiveResponse(response);
                    dismissDialog(showDialog);
                } else {
                    listener.onStatusFalse(response);
                    dismissDialog(showDialog);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onResponseFailed();
                dismissDialog(showDialog);
            }
        });

        AppController.getInstance().addToRequestQueue(request);
    }

    public void addCarBrand(Context context, AddCarBrandRequest addCarBrandRequest, final boolean showDialog, final APIResponseListener<AddCarBrandResponseWrapper> listener) {
        showDialog(context, showDialog);
        GenericRequest<AddCarBrandResponseWrapper> request = new GenericRequest<AddCarBrandResponseWrapper>(Request.Method.POST, BASEURL+"car_packages_v2/", AddCarBrandResponseWrapper.class, addCarBrandRequest, new Response.Listener<AddCarBrandResponseWrapper>() {
            @Override
            public void onResponse(AddCarBrandResponseWrapper response) {
                if (response.getResponse().isSuccess()) {
                    listener.onReceiveResponse(response);
                    dismissDialog(showDialog);
                } else {
                    listener.onStatusFalse(response);
                    dismissDialog(showDialog);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onResponseFailed();
                dismissDialog(showDialog);
            }
        });

        AppController.getInstance().addToRequestQueue(request);
    }


    public void select_Package(Context context, SelectPackageRequest selectPackageRequest, final boolean showDialog, final APIResponseListener<SelectPackageResponseWrapper> listener) {
        showDialog(context, showDialog);
        GenericRequest<SelectPackageResponseWrapper> request = new GenericRequest<SelectPackageResponseWrapper>(Request.Method.POST, BASEURL+"car_packages_v2/", SelectPackageResponseWrapper.class, selectPackageRequest, new Response.Listener<SelectPackageResponseWrapper>() {
            @Override
            public void onResponse(SelectPackageResponseWrapper response) {
                if (response.getResponse().isSuccess()) {
                    listener.onReceiveResponse(response);
                    dismissDialog(showDialog);
                } else {
                    listener.onStatusFalse(response);
                    dismissDialog(showDialog);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onResponseFailed();
                dismissDialog(showDialog);
            }
        });

        AppController.getInstance().addToRequestQueue(request);
    }


    public void sendPaidCarDetail(Context context, SendCarPaidDetailRequest selectPackageRequest, final boolean showDialog, final APIResponseListener<SendPaidCarDetailResponseWrapper> listener) {
        showDialog(context, showDialog);
        GenericRequest<SendPaidCarDetailResponseWrapper> request = new GenericRequest<SendPaidCarDetailResponseWrapper>(Request.Method.POST, BASEURL+"car_packages_v2/", SendPaidCarDetailResponseWrapper.class, selectPackageRequest, new Response.Listener<SendPaidCarDetailResponseWrapper>() {
            @Override
            public void onResponse(SendPaidCarDetailResponseWrapper response) {
                if (response.getResponse().isSuccess()) {
                    listener.onReceiveResponse(response);
                    dismissDialog(showDialog);
                } else {
                    listener.onStatusFalse(response);
                    dismissDialog(showDialog);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onResponseFailed();
                dismissDialog(showDialog);
            }
        });

        AppController.getInstance().addToRequestQueue(request);

    }


    public void addUpcomingServices(Context context, AddUpcomingRenewalServiceRequest addUpcomingRenewalServiceRequest, final boolean showDialog, final APIResponseListener<AddUpcomingRenewalServiceResponseWrapper> listener) {
        showDialog(context, showDialog);
        GenericRequest<AddUpcomingRenewalServiceResponseWrapper> request = new GenericRequest<AddUpcomingRenewalServiceResponseWrapper>(Request.Method.POST, BASEURL+"car_packages_v2/", AddUpcomingRenewalServiceResponseWrapper.class, addUpcomingRenewalServiceRequest, new Response.Listener<AddUpcomingRenewalServiceResponseWrapper>() {
            @Override
            public void onResponse(AddUpcomingRenewalServiceResponseWrapper response) {
                if (response.getResponse().isSuccess()) {
                    listener.onReceiveResponse(response);
                    dismissDialog(showDialog);
                } else {
                    listener.onStatusFalse(response);
                    dismissDialog(showDialog);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onResponseFailed();
                dismissDialog(showDialog);
            }
        });

        AppController.getInstance().addToRequestQueue(request);
    }


    public void changePassword(Context context, ChangePasswordRequest changePasswordRequest, final boolean showDialog, final APIResponseListener<ChangePasswordResponseWrapper> listener) {
        showDialog(context, showDialog);
        GenericRequest<ChangePasswordResponseWrapper> request = new GenericRequest<ChangePasswordResponseWrapper>(Request.Method.POST, BASEURL+"api_v2/", ChangePasswordResponseWrapper.class, changePasswordRequest, new Response.Listener<ChangePasswordResponseWrapper>() {
            @Override
            public void onResponse(ChangePasswordResponseWrapper response) {
                if (response.getResponse().isSuccess()) {
                    listener.onReceiveResponse(response);
                    dismissDialog(showDialog);
                } else {
                    listener.onStatusFalse(response);
                    dismissDialog(showDialog);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onResponseFailed();
                dismissDialog(showDialog);
            }
        });

        AppController.getInstance().addToRequestQueue(request);
    }


    public void editCarDetail(Context context, EditCarDetailRequest editCarDetailRequest, final boolean showDialog, final APIResponseListener<EditCarDetailResponseWrapper> listener) {
        showDialog(context, showDialog);
        GenericRequest<EditCarDetailResponseWrapper> request = new GenericRequest<EditCarDetailResponseWrapper>(Request.Method.POST, BASEURL+"car_packages_v2/", EditCarDetailResponseWrapper.class, editCarDetailRequest, new Response.Listener<EditCarDetailResponseWrapper>() {
            @Override
            public void onResponse(EditCarDetailResponseWrapper response) {
                if (response.getResponse().isSuccess()) {
                    listener.onReceiveResponse(response);
                    dismissDialog(showDialog);
                } else {
                    listener.onStatusFalse(response);
                    dismissDialog(showDialog);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onResponseFailed();
                dismissDialog(showDialog);
            }
        });

        AppController.getInstance().addToRequestQueue(request);
    }


    public void deleteCarDetail(Context context, DeleteCarListRequest deleteCarListRequest, final boolean showDialog, final APIResponseListener<DeleteCarListResponseWrapper> listener) {
        showDialog(context, showDialog);
        GenericRequest<DeleteCarListResponseWrapper> request = new GenericRequest<DeleteCarListResponseWrapper>(Request.Method.POST, BASEURL+"car_packages_v2/", DeleteCarListResponseWrapper.class, deleteCarListRequest, new Response.Listener<DeleteCarListResponseWrapper>() {
            @Override
            public void onResponse(DeleteCarListResponseWrapper response) {
                if (response.getResponse().isSuccess()) {
                    listener.onReceiveResponse(response);
                    dismissDialog(showDialog);
                } else {
                    listener.onStatusFalse(response);
                    dismissDialog(showDialog);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onResponseFailed();
                dismissDialog(showDialog);
            }
        });

        AppController.getInstance().addToRequestQueue(request);
    }


    public void getCoupon(Context context, CouponRequest couponRequest, final boolean showDialog, final APIResponseListener<CouponResponseWrapper> listener) {
        showDialog(context, showDialog);
        GenericRequest<CouponResponseWrapper> request = new GenericRequest<CouponResponseWrapper>(Request.Method.POST, BASEURL+"car_packages_v2/", CouponResponseWrapper.class, couponRequest, new Response.Listener<CouponResponseWrapper>() {
            @Override
            public void onResponse(CouponResponseWrapper response) {
                if (response.getResponse().isSuccess()) {
                    listener.onReceiveResponse(response);
                    dismissDialog(showDialog);
                } else {
                    listener.onStatusFalse(response);
                    dismissDialog(showDialog);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onResponseFailed();
                dismissDialog(showDialog);
            }
        });

        AppController.getInstance().addToRequestQueue(request);
    }


    public void getMyOrder(Context context, OrderRequest addUpcomingRenewalServiceRequest, final boolean showDialog, final APIResponseListener<OrderResponseWrapper> listener) {
        showDialog(context, showDialog);
        GenericRequest<OrderResponseWrapper> request = new GenericRequest<OrderResponseWrapper>(Request.Method.POST, BASEURL+"orders_api_v2/", OrderResponseWrapper.class, addUpcomingRenewalServiceRequest, new Response.Listener<OrderResponseWrapper>() {
            @Override
            public void onResponse(OrderResponseWrapper response) {
                if (response.getResponse().isSuccess()) {
                    listener.onReceiveResponse(response);
                    dismissDialog(showDialog);
                } else {
                    listener.onStatusFalse(response);
                    dismissDialog(showDialog);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onResponseFailed();
                dismissDialog(showDialog);
            }
        });

        AppController.getInstance().addToRequestQueue(request);
    }

    public void rateCleaner(Context context, RateCleanerRequest rateCleanerRequest, final boolean showDialog, final APIResponseListener<RateCleanerResponseWrapper> listener) {
        showDialog(context, showDialog);
        GenericRequest<RateCleanerResponseWrapper> request = new GenericRequest<RateCleanerResponseWrapper>(Request.Method.POST, BASEURL+"orders_api_v2/", RateCleanerResponseWrapper.class, rateCleanerRequest, new Response.Listener<RateCleanerResponseWrapper>() {
            @Override
            public void onResponse(RateCleanerResponseWrapper response) {
                if (response.getResponse().isSuccess()) {
                    listener.onReceiveResponse(response);
                    dismissDialog(showDialog);
                } else {
                    listener.onStatusFalse(response);
                    dismissDialog(showDialog);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onResponseFailed();
                dismissDialog(showDialog);
            }
        });

        AppController.getInstance().addToRequestQueue(request);
    }

    public void getOrderPackage(Context context, OrderPackageRequest orderPackageRequest, final boolean showDialog, final APIResponseListener<OrderPackageResponseWrapper> listener) {
        showDialog(context, showDialog);
        GenericRequest<OrderPackageResponseWrapper> request = new GenericRequest<OrderPackageResponseWrapper>(Request.Method.POST, BASEURL+"orders_api_v2/", OrderPackageResponseWrapper.class, orderPackageRequest, new Response.Listener<OrderPackageResponseWrapper>() {
            @Override
            public void onResponse(OrderPackageResponseWrapper response) {
                if (response.getResponse().isSuccess()) {
                    listener.onReceiveResponse(response);
                    dismissDialog(showDialog);
                } else {
                    listener.onStatusFalse(response);
                    dismissDialog(showDialog);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onResponseFailed();
                dismissDialog(showDialog);
            }
        });

        AppController.getInstance().addToRequestQueue(request);
    }

    public void getCrewDetail(Context context, CrewDetailRequest crewDetailRequest, final boolean showDialog, final APIResponseListener<CrewDetailResponseWrapper> listener) {
        showDialog(context, showDialog);
        GenericRequest<CrewDetailResponseWrapper> request = new GenericRequest<CrewDetailResponseWrapper>(Request.Method.POST, BASEURL+"orders_api_v2/", CrewDetailResponseWrapper.class, crewDetailRequest, new Response.Listener<CrewDetailResponseWrapper>() {
            @Override
            public void onResponse(CrewDetailResponseWrapper response) {
                if (response.getResponse().isSuccess()) {
                    listener.onReceiveResponse(response);
                    dismissDialog(showDialog);
                } else {
                    listener.onStatusFalse(response);
                    dismissDialog(showDialog);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onResponseFailed();
                dismissDialog(showDialog);
            }
        });

        AppController.getInstance().addToRequestQueue(request);
    }

    public void getCarActivityDetail(Context context, CarActivityDetailRequest carActivityDetailRequest, final boolean showDialog, final APIResponseListener<CarActivityDetailResponseWrapper> listener) {
        showDialog(context, showDialog);
        GenericRequest<CarActivityDetailResponseWrapper> request = new GenericRequest<CarActivityDetailResponseWrapper>(Request.Method.POST, BASEURL+"orders_api_v2/", CarActivityDetailResponseWrapper.class, carActivityDetailRequest, new Response.Listener<CarActivityDetailResponseWrapper>() {
            @Override
            public void onResponse(CarActivityDetailResponseWrapper response) {
                if (response.getResponse().isSuccess()) {
                    listener.onReceiveResponse(response);
                    dismissDialog(showDialog);
                } else {
                    listener.onStatusFalse(response);
                    dismissDialog(showDialog);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onResponseFailed();
                dismissDialog(showDialog);
            }
        });

        AppController.getInstance().addToRequestQueue(request);
    }


    public void updateToken(Context context, UpdateTokenRequest carActivityDetailRequest, final boolean showDialog, final APIResponseListener<UpdateTokenResponseWrapper> listener) {
        showDialog(context, showDialog);
        GenericRequest<UpdateTokenResponseWrapper> request = new GenericRequest<UpdateTokenResponseWrapper>(Request.Method.POST, BASEURL+"car_packages_v2", UpdateTokenResponseWrapper.class, carActivityDetailRequest, new Response.Listener<UpdateTokenResponseWrapper>() {
            @Override
            public void onResponse(UpdateTokenResponseWrapper response) {
                if (response.getResponse().isSuccess()) {
                    listener.onReceiveResponse(response);
                    dismissDialog(showDialog);
                } else {
                    listener.onStatusFalse(response);
                    dismissDialog(showDialog);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onResponseFailed();
                dismissDialog(showDialog);
            }
        });

        AppController.getInstance().addToRequestQueue(request);
    }


    public void getDataFromNotification(Context context, GetOrderDetailFormNotificationRequest carActivityDetailRequest, final boolean showDialog, final APIResponseListener<GetOrderDetailFormNotificationResponseWrapper> listener) {
        showDialog(context, showDialog);
        GenericRequest<GetOrderDetailFormNotificationResponseWrapper> request = new GenericRequest<GetOrderDetailFormNotificationResponseWrapper>(Request.Method.POST, BASEURL+"car_packages_v2", GetOrderDetailFormNotificationResponseWrapper.class, carActivityDetailRequest, new Response.Listener<GetOrderDetailFormNotificationResponseWrapper>() {
            @Override
            public void onResponse(GetOrderDetailFormNotificationResponseWrapper response) {
                if (response.getResponse().isSuccess()) {
                    listener.onReceiveResponse(response);
                    dismissDialog(showDialog);
                } else {
                    listener.onStatusFalse(response);
                    dismissDialog(showDialog);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onResponseFailed();
                dismissDialog(showDialog);
            }
        });

        AppController.getInstance().addToRequestQueue(request);
    }

    public void getAndSetAutoRenewals(Context context, AutoRenewRequest carActivityDetailRequest, final boolean showDialog, final APIResponseListener<AutoRenewResponseWrapper> listener) {
        showDialog(context, showDialog);
        GenericRequest<AutoRenewResponseWrapper> request = new GenericRequest<AutoRenewResponseWrapper>(Request.Method.POST, BASEURL+"orders_api_v2/", AutoRenewResponseWrapper.class, carActivityDetailRequest, new Response.Listener<AutoRenewResponseWrapper>() {
            @Override
            public void onResponse(AutoRenewResponseWrapper response) {
                if (response.getResponse().isSuccess()) {
                    listener.onReceiveResponse(response);
                    dismissDialog(showDialog);
                } else {
                    listener.onStatusFalse(response);
                    dismissDialog(showDialog);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onResponseFailed();
                dismissDialog(showDialog);
            }
        });

        AppController.getInstance().addToRequestQueue(request);
    }


    public void startAndStopPackage(Context context, StopPackageRequest carActivityDetailRequest, final boolean showDialog, final APIResponseListener<SelectPackageResponseWrapper> listener) {
        showDialog(context, showDialog);
        GenericRequest<SelectPackageResponseWrapper> request = new GenericRequest<SelectPackageResponseWrapper>(Request.Method.POST, BASEURL+"orders_api_v2/", SelectPackageResponseWrapper.class, carActivityDetailRequest, new Response.Listener<SelectPackageResponseWrapper>() {
            @Override
            public void onResponse(SelectPackageResponseWrapper response) {
                if (response.getResponse().isSuccess()) {
                    listener.onReceiveResponse(response);
                    dismissDialog(showDialog);
                } else {
                    listener.onStatusFalse(response);
                    dismissDialog(showDialog);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onResponseFailed();
                dismissDialog(showDialog);
            }
        });

        AppController.getInstance().addToRequestQueue(request);
    }


    public void checkVersion(Context context, CheckVersionRequest carActivityDetailRequest, final boolean showDialog, final APIResponseListener<CheckVersionResponseWrapper> listener) {
        showDialog(context, showDialog);
        GenericRequest<CheckVersionResponseWrapper> request = new GenericRequest<CheckVersionResponseWrapper>(Request.Method.POST, BASEURL+"api_v2/", CheckVersionResponseWrapper.class, carActivityDetailRequest, new Response.Listener<CheckVersionResponseWrapper>() {
            @Override
            public void onResponse(CheckVersionResponseWrapper response) {
                if (response.getResponse().isSuccess()) {
                    listener.onReceiveResponse(response);
                    dismissDialog(showDialog);
                } else {
                    listener.onStatusFalse(response);
                    dismissDialog(showDialog);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onResponseFailed();
                dismissDialog(showDialog);
            }
        });

        AppController.getInstance().addToRequestQueue(request);
    }


    public void cancleSubs(Context context, CancelSubscriptionRequest carActivityDetailRequest, final boolean showDialog, final APIResponseListener<CancelSubscriptionResponseWrapper> listener) {
        showDialog(context, showDialog);
        GenericRequest<CancelSubscriptionResponseWrapper> request = new GenericRequest<CancelSubscriptionResponseWrapper>(Request.Method.POST, BASEURL+"orders_api_v2/", CancelSubscriptionResponseWrapper.class, carActivityDetailRequest, new Response.Listener<CancelSubscriptionResponseWrapper>() {
            @Override
            public void onResponse(CancelSubscriptionResponseWrapper response) {
                if (response.getResponse().isSuccess()) {
                    listener.onReceiveResponse(response);
                    dismissDialog(showDialog);
                } else {
                    listener.onStatusFalse(response);
                    dismissDialog(showDialog);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onResponseFailed();
                dismissDialog(showDialog);
            }
        });

        AppController.getInstance().addToRequestQueue(request);
    }

}




