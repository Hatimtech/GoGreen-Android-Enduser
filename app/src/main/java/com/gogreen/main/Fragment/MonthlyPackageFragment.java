package com.gogreen.main.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.gogreen.main.Adapter.DaylistAdapter;
import com.gogreen.main.Adapter.FrequencyListAdapter;
import com.gogreen.main.Adapter.MonthListFrequency;
import com.gogreen.main.Interfaces.CarDataRepository;
import com.gogreen.main.Model.FrequencyList;
import com.gogreen.main.R;
import com.gogreen.main.Screens.BookServiceFormActivity;

import java.util.ArrayList;
import java.util.List;

public class MonthlyPackageFragment extends Fragment implements AdapterView.OnItemClickListener {

    private RecyclerView dayListView;
    private List<String> daylist;
    public DaylistAdapter daylistAdapter;
    private Spinner dateTypeSpinner,monthTypeSpinner;
    public MonthListFrequency monthListFrequency;
    boolean first = true;

    Context context;

    String dayType[] = {"1 day", "3 Day", "5 Day"};
    List<FrequencyList> frequencyLists;
    List<String> monthList = new ArrayList<>();

    MonthlyCallback callback;
    FrequencyListAdapter frequencyListAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmnet_montly_package, container, false);
        dayListView = (RecyclerView) view.findViewById(R.id.dayslist);
        dateTypeSpinner = (Spinner) view.findViewById(R.id.no_of_day);
        monthTypeSpinner = (Spinner) view.findViewById(R.id.no_of_month);
        context = getActivity();
        monthList.add("1 Month ");
        monthList.add("2 Month ");
        monthList.add("3 Month ");
        monthList.add("4 Month ");
        monthList.add("5 Month ");
        monthList.add("6 Month ");
        monthList.add("7 Month ");
        monthList.add("8 Month ");
        monthList.add("9 Month ");
        monthList.add("10 Month ");
        monthList.add("11 Month ");
        monthList.add("12 Month ");

        InitViews();

        return view;
    }

    public void InitViews() {

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        dayListView.setLayoutManager(linearLayoutManager);
        daylistAdapter = new DaylistAdapter(getActivity(), CarDataRepository.getInstance().getArrayList().get(BookServiceFormActivity.selectedCarPosition).getFrequency().get(CarDataRepository.getInstance().getArrayList().get(BookServiceFormActivity.selectedCarPosition).getSelectedFrequency()).getValue());
        dayListView.setAdapter(daylistAdapter);
        monthListFrequency = new MonthListFrequency(getActivity(),monthList);
        monthTypeSpinner.setAdapter(monthListFrequency);
        frequencyListAdapter = new FrequencyListAdapter(getActivity());
        dateTypeSpinner.setAdapter(frequencyListAdapter);


        dateTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CarDataRepository.getInstance().getArrayList().get(BookServiceFormActivity.selectedCarPosition).setSelectedFrequency(position);
                if (!first)
                    CarDataRepository.getInstance().getArrayList().get(BookServiceFormActivity.selectedCarPosition).resetDays();
                daylistAdapter = new DaylistAdapter(getActivity(), CarDataRepository.getInstance().getArrayList().get(BookServiceFormActivity.selectedCarPosition).getFrequency().get(CarDataRepository.getInstance().getArrayList().get(BookServiceFormActivity.selectedCarPosition).getSelectedFrequency()).getValue());
                dayListView.setAdapter(daylistAdapter);
                first = false;
                Log.d("ALIEN COUNT", String.valueOf(CarDataRepository.getInstance().getArrayList().get(BookServiceFormActivity.selectedCarPosition).getFrequency().get(CarDataRepository.getInstance().getArrayList().get(BookServiceFormActivity.selectedCarPosition).getSelectedFrequency()).getValue()));
                CarDataRepository.getInstance().refreshCarSelection();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        dateTypeSpinner.setSelection(CarDataRepository.getInstance().getArrayList().get(BookServiceFormActivity.selectedCarPosition).getSelectedFrequency());


        monthTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CarDataRepository.getInstance().getArrayList().get(BookServiceFormActivity.selectedCarPosition).setSelectedMonthlyFrequency(position);
                CarDataRepository.getInstance().getArrayList().get(BookServiceFormActivity.selectedCarPosition).setNo_of_months(String.valueOf(position+1));
                CarDataRepository.getInstance().refreshCarSelection();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    public interface MonthlyCallback {
        void onFragmentItemClick();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        callback = (MonthlyCallback) (context);
    }
}
