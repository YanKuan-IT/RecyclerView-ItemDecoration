package com.example.recyclerviewtest;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private Context context;
    private List<Star> starList;

    public MyAdapter(Context context, List<Star> starList) {
        this.context = context;
        this.starList = starList;
    }

    /**
     * 是否是组的第一个item
     */
    public boolean isGroupHeader(int position){
        if (position == 0){
            return true;
        } else {
            String currentGroupName = getGroupName(position);
            String preGroupName = getGroupName(position-1);
            if (preGroupName.equals(currentGroupName)){
                return false;
            } else {
                return true;
            }
        }
    }

    // 获取position的groupName值
    public String getGroupName(int position){
        return starList.get(position).getGroupName();
    }


    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
        holder.tv.setText(starList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return starList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.star_tv);
        }
    }
}
