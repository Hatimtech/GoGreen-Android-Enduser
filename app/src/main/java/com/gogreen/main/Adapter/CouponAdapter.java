package com.gogreen.main.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.gogreen.main.Model.AddUpcoming_Renewal_Service.RESPONSE.Coupons;
import com.gogreen.main.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CouponAdapter extends PagerAdapter {
    private LayoutInflater layoutInflater;
    private Context mContext;
    private List<Coupons> list = new ArrayList<>();


    public CouponAdapter(FragmentActivity context, List<Coupons> response) {
        this.mContext = context;
        list = response;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.coupon, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
        try {
            Picasso.with(mContext).load(list.get(position).getImgPath()).placeholder(R.drawable.coupon_placeholder)
                    .into(imageView);
        } catch (Exception e) {
            imageView.setImageResource(R.drawable.placeholder);
        }
        ViewPager vp = (ViewPager) container;
        vp.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}




