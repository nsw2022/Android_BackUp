package com.nsw2022.testmygallery;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nsw2022.testmygallery.databinding.RecyclerItemBinding;

import java.util.ArrayList;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.VH> {

    Context context;
    ArrayList<Uri> uriArrayList;

    public MyAdapter(Context context, ArrayList<Uri> uriArrayList) {
        this.context = context;
        this.uriArrayList = uriArrayList;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.recycler_item,parent,false);

        return new VH(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        Uri uri = uriArrayList.get(position);

        Glide.with(context).load(uri).into(holder.binding.ivImg);
    }

    @Override
    public int getItemCount() {
        return uriArrayList.size();
    }

    class VH extends RecyclerView.ViewHolder{

        RecyclerItemBinding binding;

        public VH(@NonNull View itemView) {
            super(itemView);
            binding=RecyclerItemBinding.bind(itemView);
        }
    }

}
