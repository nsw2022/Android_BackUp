package com.nsw2022.ex29viewpager;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

// ViewPager2의 페이지뷰를 만들어주는 아답터도 RecyclerView의 Adapter를 그대로 사용함
public class MyPagerAdapter extends RecyclerView.Adapter<MyPagerAdapter.VH> {

    Context context;
    ArrayList<Integer> items;

    public MyPagerAdapter(Context context, ArrayList<Integer> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater= LayoutInflater.from(context);
        View itemView= layoutInflater.inflate(R.layout.page, parent, false);

        VH holder= new VH(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        //holder.iv.setImageResource( items.get(position) );
        // 이미리 로드 외부 라이브러리 : Glide  or Picasso
        Glide.with(context).load(items.get(position)).into(holder.iv);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    //inner class ///////////////////////////////////////////
    class VH extends RecyclerView.ViewHolder{

        ImageView iv;

        public VH(@NonNull View itemView) {
            super(itemView);
            iv= itemView.findViewById(R.id.iv);
        }
    }////////////////////////////////////////////////////////
}
