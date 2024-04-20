package com.moutamid.exercises.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.moutamid.exercises.R;
import com.google.android.material.progressindicator.LinearProgressIndicator;

import java.util.Objects;

public class SliderAdapter extends PagerAdapter {

    // Context object
    Context context;

    // Array of images
    String[] level_no_;
    String[] level_type_;

    // Layout Inflater
    LayoutInflater mLayoutInflater;

    double progress_value;
    public TextView progress_no;
    public LinearProgressIndicator progress;
    public TextView level_no, level_type;
    public View view1, view2, view3, view4, view5;

    // Viewpager Constructor
    public SliderAdapter(Context context, String[] level_no_, String[] level_type, double progress_value) {
        this.context = context;
        this.level_no_ = level_no_;
        this.level_type_ = level_type;
        this.progress_value = progress_value;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // return the number of images
        return 5;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((LinearLayout) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        // inflating the item.xml

        View itemView = mLayoutInflater.inflate(R.layout.slider_layout, container, false);
        level_no = itemView.findViewById(R.id.level_no);
        view1 = itemView.findViewById(R.id.view1);
        view2 = itemView.findViewById(R.id.view2);
        view3 = itemView.findViewById(R.id.view3);
        view4 = itemView.findViewById(R.id.view4);
        view5 = itemView.findViewById(R.id.view5);
        level_type = itemView.findViewById(R.id.level_type);
        progress = itemView.findViewById(R.id.progress);
        progress_no = itemView.findViewById(R.id.progress_no);
        progress.setProgress((int) progress_value);
        progress_no.setText("Completed " + progress_value + "%");
        level_no.setText(level_no_[position]);
        level_type.setText(level_type_[position]);
        Objects.requireNonNull(container).addView(itemView);
        if(position==0)
        {
            view1.setBackgroundResource(R.drawable.cirlce_light);
        }
        else if(position==1)
        {
            view2.setBackgroundResource(R.drawable.cirlce_light);
        } else if(position==2)
        {
            view3.setBackgroundResource(R.drawable.cirlce_light);
        } else if(position==3)
        {
            view4.setBackgroundResource(R.drawable.cirlce_light);
        } else if(position==4)
        {
            view5.setBackgroundResource(R.drawable.cirlce_light);
        }
        return itemView;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView((LinearLayout) object);
    }
}
