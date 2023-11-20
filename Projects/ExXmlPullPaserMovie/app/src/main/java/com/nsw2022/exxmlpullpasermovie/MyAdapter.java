package com.nsw2022.exxmlpullpasermovie;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.VH> {
    Context context;
    ArrayList<MovieItem> movieItems;

    public MyAdapter(Context context, ArrayList<MovieItem> movieItems) {
        this.context = context;
        this.movieItems = movieItems;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View itemView=layoutInflater.inflate(R.layout.recycler_item,parent,false);

        VH holder = new VH(itemView);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {

        MovieItem movieItem = movieItems.get(position);

        holder.tvRank.setText(movieItem.rank);
        holder.tvName.setText(movieItem.movieNm);
        holder.tvOpen.setText(movieItem.openDt);
        holder.tvAudi.setText(movieItem.audiAcc+"명");




    }

    @Override
    public int getItemCount() {
        return movieItems.size();
    }

    class VH extends RecyclerView.ViewHolder {///////////////////////////////

        TextView tvRank;
        TextView tvName;
        TextView tvOpen;
        TextView tvAudi;

        public VH(@NonNull View itemView) {
            super(itemView);
            tvRank=itemView.findViewById(R.id.tv_rank);
            tvName=itemView.findViewById(R.id.tv_name);
            tvOpen=itemView.findViewById(R.id.tv_open);
            tvAudi=itemView.findViewById(R.id.tv_audi);

        }
    }///////////////////////////////////////////////
}
