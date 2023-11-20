package com.nsw2022.zzz_recylerviewulex;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdpter extends RecyclerView.Adapter {

    Context context;
    ArrayList<Item> items;

    public MyAdpter(Context context, ArrayList<Item> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater=LayoutInflater.from(context);

        View itemView=layoutInflater.inflate(R.layout.recyclerview_item,parent,false);

        VH holer = new VH(itemView);

        return holer;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Item item = items.get(position);

        VH vh = (VH)holder;
        vh.ivImg.setImageResource(item.imgId);
        vh.ratingBar.setRating(item.RantingBar);
        vh.tv_scoreint.setText(item.scoreInt);
        vh.tv_score.setText(item.score);
        vh.tv_price.setText(item.price);



    }

    @Override
    public int getItemCount() {
        return items.size();
    }



    //inner class
    class VH extends RecyclerView.ViewHolder{

        ImageView ivImg;
        RatingBar ratingBar;
        TextView tv_scoreint;
        TextView tv_score;
        TextView tv_price;


        public VH(@NonNull View itemView) {
            super(itemView);
            ivImg=itemView.findViewById(R.id.iv_img);
            ratingBar=itemView.findViewById(R.id.ratingbar);

            tv_scoreint=itemView.findViewById(R.id.tv_scoreint);
            tv_score=itemView.findViewById(R.id.tv_score);
            tv_price=itemView.findViewById(R.id.price);



        }
    }//innerclass

}//MyAdpter class
