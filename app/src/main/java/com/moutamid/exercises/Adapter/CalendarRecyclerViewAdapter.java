package com.moutamid.exercises.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;


import com.moutamid.exercises.Fragments.HistoryFragment;
import com.moutamid.exercises.Model.DateModelClass;
import com.moutamid.exercises.R;

import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.List;


public class CalendarRecyclerViewAdapter extends RecyclerView.Adapter<CalendarRecyclerViewAdapter.ViewHolder> {
    private List<LocalDate> mDataset;
    static Context context;
    private ArrayList<DateModelClass> dateArrayList;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView date, day, date_pre, day_pre, date_pre1;
        View view;
        RelativeLayout main_bg;


        public ViewHolder(View view) {
            super(view);
            this.view = view;

            this.date = (TextView) view
                    .findViewById(R.id.date);
            this.date_pre1 = (TextView) view
                    .findViewById(R.id.date_pre1);
            this.day = (TextView) view
                    .findViewById(R.id.day);
            this.date_pre = (TextView) view
                    .findViewById(R.id.date_pre);
            this.day_pre = (TextView) view
                    .findViewById(R.id.day_pre);
            this.main_bg = (RelativeLayout) view
                    .findViewById(R.id.main_bg);

        }

    }

    public CalendarRecyclerViewAdapter(List<LocalDate> mDataset, ArrayList<DateModelClass> dateArrayList) {
        this.mDataset = mDataset;
        this.dateArrayList = dateArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.calendar_recycler_view_list_item, parent, false);

        ViewHolder vh = new ViewHolder(v);
        context = parent.getContext();
        return vh;
    }


    // Replace the contents of a view (invoked by the layout manager)
    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.date.setText("" + mDataset.get(position).getDayOfMonth());
        holder.date_pre.setText("" + mDataset.get(position).getDayOfMonth());
        holder.date_pre1.setText("" + mDataset.get(position).getDayOfMonth());
        int day = mDataset.get(position).getDayOfWeek();
        for (int i = 0; i < dateArrayList.size(); i++) {
            if (mDataset.get(position).getDayOfMonth() == dateArrayList.get(i).getDay() && (dateArrayList.get(i).getMonth() - mDataset.get(position).getDayOfWeek()) == 1 && mDataset.get(position).getYear() == dateArrayList.get(i).getYear()) {
                holder.date_pre1.setVisibility(View.VISIBLE);
                Log.d("size", mDataset.get(position).getDayOfMonth()+"  "+dateArrayList.get(i).getDay()+"   "+dateArrayList.get(i).getMonth()+"  "+ mDataset.get(position).getDayOfWeek()+"  "+dateArrayList.get(i).getYear());
            }
        }
        if (HistoryFragment.getSelected() != null) {

            if ((HistoryFragment.getSelected().getDayOfYear() == mDataset.get(position).getDayOfYear() && HistoryFragment.getSelected().getDayOfWeek() == mDataset.get(position).getDayOfWeek() && HistoryFragment.getSelected().getYear() == mDataset.get(position).getYear())) {
                holder.main_bg.setVisibility(View.VISIBLE);
            } else {
                holder.main_bg.setVisibility(View.INVISIBLE);


            }
//
        }


        if (day == 1) {

            holder.day.setText("Mon");
            holder.day_pre.setText("Mon");

        } else if (day == 2) {

            holder.day.setText("Tue");
            holder.day_pre.setText("Tue");

        } else if (day == 3) {

            holder.day.setText("Wed");
            holder.day_pre.setText("Wed");

        } else if (day == 4) {

            holder.day.setText("Thu");
            holder.day_pre.setText("Thu");

        } else if (day == 5) {

            holder.day.setText("Fri");
            holder.day_pre.setText("Fri");

        } else if (day == 6) {

            holder.day.setText("Sat");
            holder.day_pre.setText("Sat");

        } else if (day == 7) {

            holder.day.setText("Sun");
            holder.day_pre.setText("Sun");

        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "test"+ mDataset.get(position).getDayOfMonth(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return mDataset.size();
    }


}