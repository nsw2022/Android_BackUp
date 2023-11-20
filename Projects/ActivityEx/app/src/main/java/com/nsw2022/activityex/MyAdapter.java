package com.nsw2022.activityex;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.VH> {
    Context context;
    ArrayList<Item> items;



    public MyAdapter(Context context, ArrayList<Item> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View itemView = layoutInflater.inflate(R.layout.recylerview_item,parent,false);

        VH holder = new VH(itemView);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        Item item=items.get(position);

        holder.tv_name.setText(item.name);
        holder.tv_title.setText(item.title);
        holder.tv_nickname.setText(item.nickname);
        holder.tv_content.setText(item.content);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }



    class VH extends RecyclerView.ViewHolder {
        TextView tv_name;
        TextView tv_title;
        TextView tv_nickname;
        TextView tv_content;


        public VH(@NonNull View itemView) {
            super(itemView);
           tv_name=itemView.findViewById(R.id.tv_name);
           tv_title=itemView.findViewById(R.id.tv_title);
           tv_nickname=itemView.findViewById(R.id.tv_nickname);
           tv_content=itemView.findViewById(R.id.tv_letter);

        }
    }
}
