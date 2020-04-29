package com.example.redstone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context) {
        this.context = context;
    }

    //Array
    public int[] side_images = {
            R.drawable.spaceship_icon,
            R.drawable.stone_icon,
            R.drawable.products_list
    };
    public int[] slide_desc = {
            R.string.firtStep,
            R.string.secondStep,
            R.string.thirdStep
    };


    @Override
    public int getCount() {
        return slide_desc.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (RelativeLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layot, container, false);
        ImageView slideImageView = (ImageView) view.findViewById(R.id.slide_image);
        TextView slideTextView = (TextView)  view.findViewById(R.id.slide_desc);

        slideImageView.setImageResource(side_images[position]);
        slideTextView.setText(slide_desc[position]);

        container.addView(view);
        return view;
    };

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout) object);
    }
}
