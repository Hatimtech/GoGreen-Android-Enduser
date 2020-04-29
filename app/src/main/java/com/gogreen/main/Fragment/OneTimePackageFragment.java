package com.gogreen.main.Fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.gogreen.main.Interfaces.CarDataRepository;
import com.gogreen.main.R;
import com.gogreen.main.Screens.BookServiceFormActivity;
import com.gogreen.main.Utils.CommonUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class OneTimePackageFragment extends Fragment implements View.OnClickListener {

    private EditText calendar;
    private DatePickerDialog.OnDateSetListener date;
    Calendar myCalendar = Calendar.getInstance();
    OneTimeCallback callback;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_one_time_package, container, false);
        calendar = (EditText) view.findViewById(R.id.calendr1);
        calendar.setOnClickListener(this);
        InitView();
        return view;
    }

    private void InitView() {


        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                if(myCalendar.get(Calendar.DAY_OF_WEEK)==6){
                    CommonUtils.alert(getActivity(),"You could not select the friday");
//                    Toast.makeText(getActivity(),"You could not select the friday",Toast.LENGTH_SHORT).show();
                    calendar.setText("/    /");
                }else{
                    String myFormat = "yyyy-MM-dd";
                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);


                    calendar.setText(sdf.format(myCalendar.getTime()));
                    CarDataRepository.getInstance().getArrayList().get(BookServiceFormActivity.selectedCarPosition).setDate(sdf.format(myCalendar.getTime()));

                }


            }

        };


        calendar.setText(CarDataRepository.getInstance().getArrayList().get(BookServiceFormActivity.selectedCarPosition).getDate());
    }


    @Override
    public void onClick(View v) {
        Calendar myCalendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH));

        long hour=System.currentTimeMillis();
        if(hour>1000*3600*18){
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() + 1000*6*3600);

        }else{
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

       }

        myCalendar.add(Calendar.MONTH,2);
        datePickerDialog.getDatePicker().setMaxDate(myCalendar.getTimeInMillis());
        datePickerDialog.show();

    }

    public interface OneTimeCallback {
        void onFragmentItemClick();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }


}
