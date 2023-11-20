package com.nsw2022.pr3retrofit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nsw2022.pr3retrofit.databinding.RecyclerviewItemBinding;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.VH> {

    Context context;
    ArrayList<Movie> movies;

    public MyAdapter(Context context, ArrayList<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View itemView = LayoutInflater.from(context).inflate(R.layout.recyclerview_item, parent, false);

        return new VH(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        Movie movie = movies.get(position);
        holder.binding.tvTitle.setText(movie.movieNm);
        holder.binding.tvDate.setText(movie.openDt);
        holder.binding.tvRank.setText(movie.rank);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    class VH extends RecyclerView.ViewHolder{
        RecyclerviewItemBinding binding;

        public VH(@NonNull View itemView) {
            super(itemView);
            binding = RecyclerviewItemBinding.bind(itemView);
        }
    }
}


