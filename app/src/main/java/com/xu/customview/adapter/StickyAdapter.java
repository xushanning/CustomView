package com.xu.customview.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xu.customview.R;

/**
 * @author 言吾許
 */

public class StickyAdapter extends RecyclerView.Adapter<StickyAdapter.MyViewHolder> {


    @Override
    public StickyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.textView.setText("这是第" + position + "条");
    }


    @Override
    public int getItemCount() {
        return 100;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        MyViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_position);
        }
    }

}
