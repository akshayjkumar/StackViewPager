package com.ajdev.stackviewpager;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Akshay.Jayakumar.
 */

public class PagerDataAdapter extends PagerAdapter {

    Context context;
    LayoutInflater inflater;
    int[] colorList;

    public PagerDataAdapter(Context context) {
        this.context = context;
        inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        TypedArray ta = context.getResources().obtainTypedArray(R.array.colorList);
        colorList = new int[ta.length()];
        for (int i = 0; i < ta.length(); i++) {
            colorList[i] = ta.getColor(i, 0);
        }
        ta.recycle();
    }

    @Override
    public int getCount() {
        return colorList.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = inflater.inflate(R.layout.vp_page_item, container, false);
        View contentView = view.findViewById(R.id.content);
        contentView.setBackgroundColor(colorList[position]);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
