package com.nsw2022.zzz_recylerviewtwo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.VH>{
  Context context;
  ArrayList<Item> items;

    public MyAdapter(Context context, ArrayList<Item> items) {
        this.context = context;
        this.items = items;
    }

    public MyAdapter() {
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View itemView=layoutInflater.inflate(R.layout.recyclerview_item,parent,false);

        VH holder = new VH(itemView);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {

        Item item=items.get(position);

        holder.tv_title.setText(item.tv_title);
        holder.tv_price.setText(item.tv_price);

        Glide.with(context).load(item.iv).into(holder.imgID);
        Glide.with(context).load(item.hertid).into(holder.imgheart);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class VH extends RecyclerView.ViewHolder{

        ImageView imgID,imgheart;
        TextView tv_title, tv_price;

        public VH(@NonNull View itemView) {
            super(itemView);
            imgID= itemView.findViewById(R.id.iv);
            imgheart=itemView.findViewById(R.id.herat);

            tv_title=itemView.findViewById(R.id.tv_title);
            tv_price=itemView.findViewById(R.id.price);

        }
    }


}//MyAdapter
