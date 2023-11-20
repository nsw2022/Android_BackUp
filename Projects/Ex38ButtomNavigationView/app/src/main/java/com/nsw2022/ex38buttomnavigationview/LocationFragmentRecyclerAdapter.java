package com.nsw2022.ex38buttomnavigationview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LocationFragmentRecyclerAdapter extends RecyclerView.Adapter<LocationFragmentRecyclerAdapter.VH> {
    Context context;
    ArrayList<LocationFragmentRecyclerItem> items;

    public LocationFragmentRecyclerAdapter(Context context, ArrayList<LocationFragmentRecyclerItem> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View itemView=layoutInflater.inflate(R.layout.locationfrgment_recycler_item,parent,false);

        VH holder = new VH(itemView);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        holder.iv.setImageResource(items.get(position).imgId);
        holder.tvTitle.setText( items.get(position).title );
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    //inner class - 아이템뷰 1개의 안에 있는 자식뷰들의 참조변수를 보관하는 클래스
    class VH extends RecyclerView.ViewHolder{

        ImageView iv;
        TextView tvTitle;

        public VH(@NonNull View itemView) {
            super(itemView);
            iv=itemView.findViewById(R.id.iv);
            tvTitle=itemView.findViewById(R.id.tv_title);

        }
    }//////////////////////////////////////////////

}

