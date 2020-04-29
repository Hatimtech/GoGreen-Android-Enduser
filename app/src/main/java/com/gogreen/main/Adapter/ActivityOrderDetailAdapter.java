package com.gogreen.main.Adapter;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gogreen.main.Fragment.ActiveMyOrderFragment;
import com.gogreen.main.Fragment.ActivityFragment;
import com.gogreen.main.Model.CarActivityDetail.RESPONSE.CarActivityDetailResponseResult;
import com.gogreen.main.Model.Coupon.REQUEST.CouponRequest;
import com.gogreen.main.Model.Coupon.RESPONSE.CouponResponseWrapper;
import com.gogreen.main.Model.RateCleaner.REQUEST.RateCleanerRequest;
import com.gogreen.main.Model.RateCleaner.RESPONSE.RateCleanerResponseWrapper;
import com.gogreen.main.R;
import com.gogreen.main.Screens.OrderConfirmationActivity;
import com.gogreen.main.Screens.OrderDetailActivity;
import com.gogreen.main.Utils.CommonUtils;
import com.gogreen.main.Utils.Constants;
import com.gogreen.main.Utils.Preferences.PrefEntity;
import com.gogreen.main.Utils.Preferences.Preferences;
import com.gogreen.main.Volley.APIUtility;

import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

public class ActivityOrderDetailAdapter extends RecyclerView.Adapter<ActivityOrderDetailAdapter.ViewHolder> {


    private List<CarActivityDetailResponseResult> taskOrderList;
    ActivityFragment mContext;
    APIUtility apiUtility;

    public ActivityOrderDetailAdapter(List<CarActivityDetailResponseResult> taskOrderList, ActivityFragment mContext) {
        this.mContext = mContext;
        apiUtility = new APIUtility(mContext.getActivity());
        this.taskOrderList = taskOrderList;

    }


    @Override
    public ActivityOrderDetailAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ActivityOrderDetailAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_list, parent, false));
    }


    @Override
    public void onBindViewHolder(final ActivityOrderDetailAdapter.ViewHolder holder, final int position) {
        holder.rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDailog(position);

            }
        });
        holder.date.setText(taskOrderList.get(position).getJob_done_date());

        if (taskOrderList.get(position).getAttendent().equals("1")) {
            holder.status.setText(R.string.completed);
            holder.rate.setVisibility(View.VISIBLE);
            holder.curved.setImageResource(R.mipmap.curved_edge);
            holder.staus_layout.setBackgroundResource(R.drawable.order_confirm_text_drawable);
        } else {
            holder.status.setText(R.string.pending);
            holder.rate.setVisibility(View.GONE);
            holder.curved.setImageResource(R.mipmap.red_curved_edge);
            holder.staus_layout.setBackgroundResource(R.drawable.order_pending_drawable);
        }

    }

    private void showDailog(final int position) {

        final Dialog dialog = new Dialog(mContext.getContext());
        dialog.setContentView(R.layout.rate_cleaner);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ImageView cross = dialog.findViewById(R.id.cross);
        ImageView cleanerProfile = dialog.findViewById(R.id.cl_image);
        final Button dialogButton = dialog.findViewById(R.id.dialog_ok);
        final EditText editText = dialog.findViewById(R.id.feedback);
        final RatingBar ratingBar = dialog.findViewById(R.id.rating);
        TextView userName = dialog.findViewById(R.id.user_name);

        try {
            byte[] decodedString = Base64.decode(taskOrderList.get(position).getImage_string(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            cleanerProfile.setImageBitmap(decodedByte);
        } catch (Exception e) {
            cleanerProfile.setImageResource(R.drawable.placeholder);
        }

        userName.setText(taskOrderList.get(position).getCleaner_first_name() + "  " + taskOrderList.get(position).getCleaner_lastName());
        cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                    RateCleanerRequest cleanerRequest = new RateCleanerRequest();
                    cleanerRequest.setApp_key(Constants.APP_KEY);
                    cleanerRequest.setCar_id(Preferences.getPreference(mContext.getActivity().getApplicationContext(), PrefEntity.CARID));
                    cleanerRequest.setRating(String.valueOf(ratingBar.getRating()));
                    cleanerRequest.setMethod("rate_cleaner");
                    cleanerRequest.setFeedback(editText.getText().toString());
                    cleanerRequest.setUserID(Preferences.getPreference(mContext.getActivity().getApplicationContext(), PrefEntity.USERID));
                    cleanerRequest.setOrder_id(Preferences.getPreference(mContext.getActivity().getApplicationContext(), PrefEntity.ORDERID));
                    cleanerRequest.setCleanerID(taskOrderList.get(position).getCleaner_id());
                    cleanerRequest.setActivityID(taskOrderList.get(position).getOrderID());


                    apiUtility.rateCleaner(mContext.getActivity(), cleanerRequest, true, new APIUtility.APIResponseListener<RateCleanerResponseWrapper>() {
                        @Override
                        public void onReceiveResponse(RateCleanerResponseWrapper response) {
                            dialog.dismiss();
                            if (response.getResponse().getResCode() == 0) {
                                CommonUtils.alert(mContext.getActivity(), response.getResponse().getMessage());
                            }else{
                                CommonUtils.alert(mContext.getActivity(),"Thank You for providing us feedback");
                            }
                        }

                        @Override
                        public void onResponseFailed() {
                            CommonUtils.alert(mContext.getActivity(), mContext.getString(R.string.VolleyError));

                        }

                        @Override
                        public void onStatusFalse(RateCleanerResponseWrapper response) {
                            CommonUtils.alert(mContext.getActivity(), response.getResponse().getMessage());
                        }
                    });



//                else
//                    CommonUtils.alert(mContext.getActivity(), mContext.getString(R.string.feedback));
            }
        });
        dialog.setCancelable(true);
        dialog.show();
    }

    @Override
    public int getItemCount() {
        return taskOrderList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView date, rate, status;
        LinearLayout staus_layout;
        ImageView curved;


        ViewHolder(View itemView) {
            super(itemView);
            curved = (ImageView) itemView.findViewById(R.id.curved);
            staus_layout = (LinearLayout) itemView.findViewById(R.id.status_layout);

            rate = (TextView) itemView.findViewById(R.id.rate_review);
            date = (TextView) itemView.findViewById(R.id.service_date);
            status = (TextView) itemView.findViewById(R.id.car_orderStatus);


        }
    }
}