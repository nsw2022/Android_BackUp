package com.nsw2022.pullpaserex;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.VH> {

    ArrayList<MovieItem> movieItems;
    Context context;

    public MyAdapter(ArrayList<MovieItem> movieItems, Context context) {
        this.movieItems = movieItems;
        this.context = context;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View viewItem=layoutInflater.inflate(R.layout.recycler_item,parent,false);

        VH holder = new VH(viewItem);



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

    class VH extends RecyclerView.ViewHolder{

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


    }
}
