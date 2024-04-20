package com.moutamid.exercises.Adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.moutamid.exercises.R;

import java.util.List;

public class WeightPickerAdapter extends RecyclerView.Adapter<WeightPickerAdapter.TextVH> {

    private Context context;
    private List<String> dataList;
    private RecyclerView recyclerView;

    public WeightPickerAdapter(Context context, List<String> dataList, RecyclerView recyclerView) {
        this.context = context;
        this.dataList = dataList;
        this.recyclerView = recyclerView;
    }

    @Override
    public TextVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.weight_picker_item_layout, parent, false);
        return new TextVH(view);
    }

    @Override
    public void onBindViewHolder(TextVH holder, int position) {
        holder.pickerTxt.setText(dataList.get(position) );
        holder.pickerTxt.setOnClickListener(v -> recyclerView.smoothScrollToPosition(position));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void swapData(List<String> newData) {
        dataList = newData;
        this.notifyDataSetChanged();
    }

    public class TextVH extends RecyclerView.ViewHolder {
        TextView pickerTxt;

        public TextVH(View itemView) {
            super(itemView);
            pickerTxt = itemView.findViewById(R.id.weight_picker_item);
        }
    }

}
