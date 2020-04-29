package com.gogreen.main.Adapter;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gogreen.main.Fragment.MainFragment;
import com.gogreen.main.Interfaces.CarDataRepository;
import com.gogreen.main.Model.AddUpcoming_Renewal_Service.RESPONSE.UpcomingRenewals;
import com.gogreen.main.R;
import com.gogreen.main.Screens.BookServiceFormActivity;
import com.gogreen.main.Screens.CityActivity;
import com.gogreen.main.Screens.PaymentActivity2;
import com.gogreen.main.Utils.CommonUtils;
import com.gogreen.main.Utils.Constants;
import com.gogreen.main.Utils.Preferences.PrefEntity;
import com.gogreen.main.Utils.Preferences.Preferences;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class PendingListAdapter extends RecyclerView.Adapter<PendingListAdapter.ViewHolder> {

    private MainFragment mContext;
    private List<UpcomingRenewals> upcoming_pending_list;


    public PendingListAdapter(List<UpcomingRenewals> upcoming_pending_list, MainFragment mContext) {
        this.mContext = mContext;
        this.upcoming_pending_list = upcoming_pending_list;

    }

    @Override
    public PendingListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PendingListAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.upcoming_pending_list, parent, false));
    }


    @Override
    public void onBindViewHolder(final PendingListAdapter.ViewHolder holder, final int position) {
        holder.carType.setText(upcoming_pending_list.get(position).getCarType());
        holder.payment.setText("AED  " + upcoming_pending_list.get(position).getAmount());
        holder.expired.setText("Expired on  " + upcoming_pending_list.get(position).getExpiry_date());

        holder.parkingNumber.setText(upcoming_pending_list.get(position).getReg_no());
        holder.carModel.setText(upcoming_pending_list.get(position).getBrand() + ", " + upcoming_pending_list.get(position).getModel());
        if(upcoming_pending_list.size()>1)
            holder.totalNumber.setText(String.valueOf(position+1)+" / "+ String.valueOf(upcoming_pending_list.size()));
        holder.renew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (Preferences.getPreference(mContext.getActivity().getApplicationContext(), PrefEntity.USER_STREET_ID).equals("")) {
                    Intent intent2 = new Intent(mContext.getActivity(), CityActivity.class);
                    intent2.putExtra("hi", "hi");
                    mContext.startActivity(intent2);

                } else {

                    if (upcoming_pending_list.get(position).getPackage_type().equals("once")) {

                        showDialog(position);

                    } else {

                        Intent intent = new Intent(mContext.getActivity(), PaymentActivity2.class);
                        intent.putExtra("onceDate", upcoming_pending_list.get(position).getOne_time_service_date());
                        intent.putExtra("services", upcoming_pending_list.get(position).getServices());
                        intent.putExtra("frequency", upcoming_pending_list.get(position).getFrequency());
                        intent.putExtra("expiry_date", upcoming_pending_list.get(position).getExpiry_date());
                        intent.putExtra("amount", upcoming_pending_list.get(position).getAmount());
                        intent.putExtra("days", upcoming_pending_list.get(position).getDays());
                        intent.putExtra("package_type", upcoming_pending_list.get(position).getPackage_type());
                        intent.putExtra("parking_number", upcoming_pending_list.get(position).getParking_number());
                        intent.putExtra("car_id", upcoming_pending_list.get(position).getCar_id());
                        intent.putExtra("package_name", upcoming_pending_list.get(position).getPackage_name());
                        intent.putExtra("no_of_month", upcoming_pending_list.get(position).getNo_of_months());

                        mContext.startActivity(intent);

                    }
                }
            }

        });

    }

    private void showDialog(final int position) {

        final Dialog dialog = new Dialog(mContext.getContext());
        dialog.setContentView(R.layout.date_dailoge);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final Button dialogButton = dialog.findViewById(R.id.dialog_ok);
        final Calendar myCalendar = Calendar.getInstance();
        final EditText editText = dialog.findViewById(R.id.calendr1);

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String myFormat = "yyyy-MM-dd";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                editText.setText(sdf.format(myCalendar.getTime()));

            }

        };

        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(mContext.getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));

                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                myCalendar.add(Calendar.MONTH, 2);
                datePickerDialog.getDatePicker().setMaxDate(myCalendar.getTimeInMillis());
                datePickerDialog.show();

            }
        });

        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext.getActivity(), PaymentActivity2.class);
                intent.putExtra("onceDate", editText.getText().toString());
                intent.putExtra("services", upcoming_pending_list.get(position).getServices());
                intent.putExtra("frequency", upcoming_pending_list.get(position).getFrequency());
                intent.putExtra("expiry_date", upcoming_pending_list.get(position).getExpiry_date());
                intent.putExtra("amount", upcoming_pending_list.get(position).getAmount());
                intent.putExtra("days", upcoming_pending_list.get(position).getDays());
                intent.putExtra("package_type", upcoming_pending_list.get(position).getPackage_type());
                intent.putExtra("parking_number", upcoming_pending_list.get(position).getParking_number());
                intent.putExtra("car_id", upcoming_pending_list.get(position).getCar_id());
                intent.putExtra("package_name", upcoming_pending_list.get(position).getPackage_name());
                intent.putExtra("no_of_month", upcoming_pending_list.get(position).getNo_of_months());

                mContext.startActivity(intent);
                dialog.dismiss();

            }
        });


        dialog.setCancelable(true);
        dialog.show();
    }

    @Override
    public int getItemCount() {
        return upcoming_pending_list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView carModel, parkingNumber, carType, payment,totalNumber,expired;
        Button renew;


        ViewHolder(View itemView) {
            super(itemView);
            totalNumber=(TextView)itemView.findViewById(R.id.totalNumber);
            renew = (Button) itemView.findViewById(R.id.renew);
            carModel = (TextView) itemView.findViewById(R.id.car_model_brand);
            carType = (TextView) itemView.findViewById(R.id.CAR_type);
            payment = (TextView) itemView.findViewById(R.id.payment);
            parkingNumber = (TextView) itemView.findViewById(R.id.parking);
            expired = (TextView) itemView.findViewById(R.id.expired);



        }


    }


}
