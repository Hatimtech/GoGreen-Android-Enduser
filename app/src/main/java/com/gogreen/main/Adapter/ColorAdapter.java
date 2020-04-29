package com.gogreen.main.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gogreen.main.R;


public class ColorAdapter extends BaseAdapter {


    Context context;
    LayoutInflater inflater;
    String[] colorList;


    public ColorAdapter(Context context, String[] colorList) {

        this.context = context;
        this.colorList = colorList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }


    @Override
    public int getCount() {
        return colorList.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {


        view = inflater.inflate(R.layout.spinner_rows, null);
        TextView color_name = (TextView) view.findViewById(R.id.color);
        color_name.setText(colorList[i]);
        return view;
    }


}


