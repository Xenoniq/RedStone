package com.example.redstone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class IntroActivity extends AppCompatActivity {
    private ViewPager slidePage;
    private LinearLayout dotLayout;
    private TextView[] dots;
    private SliderAdapter sliderAdapter;

    private Button nextBut;
    private Button backBut;
    private int curPage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        slidePage = (ViewPager) findViewById(R.id.slidePage);
        dotLayout = (LinearLayout) findViewById(R.id.dotLayout);

        nextBut =(Button)findViewById(R.id.nextBut);
        backBut =(Button)findViewById(R.id.backBut);

        sliderAdapter = new SliderAdapter(this);

        slidePage.setAdapter(sliderAdapter);
        addDotsIndicator(0);
        slidePage.addOnPageChangeListener(listener);

        nextBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(curPage == dots.length-1){
                    try {
                        Intent intent = new Intent(IntroActivity.this, EnterWeight.class);
                        startActivity(intent);
                    }catch (Exception e){

                    }
                } else
                slidePage.setCurrentItem(curPage+1);
            }
        });
        backBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slidePage.setCurrentItem(curPage-1);
            }
        });

    }

    public void addDotsIndicator(int position) {
        dots = new TextView[3];
        dotLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.orangeMain));
            dotLayout.addView(dots[i]);
        }

        if (dots.length > 0) {
            dots[position].setTextColor(getResources().getColor(R.color.redMain));
        }
    }

    ViewPager.OnPageChangeListener listener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
            addDotsIndicator(i);
            curPage = i;
            if( i ==0){
                nextBut.setEnabled(true);
                backBut.setEnabled(false);
                backBut.setVisibility(View.INVISIBLE);
                nextBut.setText(R.string.next);
                backBut.setText("");
            } else if ( i == dots.length-1){
                nextBut.setEnabled(true);
                backBut.setEnabled(true);
                backBut.setVisibility(View.VISIBLE);
                nextBut.setText(R.string.start);
                backBut.setText(R.string.back);
            } else {
                nextBut.setEnabled(true);
                backBut.setEnabled(true);
                backBut.setVisibility(View.VISIBLE);
                nextBut.setText(R.string.next);
                backBut.setText(R.string.back);
            }
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };
}
