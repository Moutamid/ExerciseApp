package com.moutamid.exercises.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.moutamid.exercises.R;

import java.util.Objects;

public class RestDurationAdapter extends PagerAdapter {

    // Context object
    Context context;

    // Array of images
    String[] level_no_;

    // Layout Inflater
    LayoutInflater mLayoutInflater;
   public TextView number;

    // Viewpager Constructor
    public RestDurationAdapter(Context context, String[] level_no_) {
        this.context = context;
        this.level_no_ = level_no_;
          mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // return the number of images
        return level_no_.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((ConstraintLayout) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        // inflating the item.xml
        View itemView = mLayoutInflater.inflate(R.layout.rest_duration_item, container, false);
        number = itemView.findViewById(R.id.number);
//        level_no.setText(level_no_[position]);
//        level_type.setText(level_type_[position]);

        // Adding the View
        Objects.requireNonNull(container).addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView((ConstraintLayout) object);
    }
}
