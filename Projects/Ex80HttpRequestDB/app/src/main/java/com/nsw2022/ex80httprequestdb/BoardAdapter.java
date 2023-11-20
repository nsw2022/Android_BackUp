package com.nsw2022.ex80httprequestdb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nsw2022.ex80httprequestdb.databinding.RecyclerItemBinding;

import java.util.ArrayList;

public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.VH> {
    Context context;
    ArrayList<BoardItem> boardItems;

    public BoardAdapter(Context context, ArrayList<BoardItem> boardItems) {
        this.context = context;
        this.boardItems = boardItems;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView=LayoutInflater.from(context).inflate(R.layout.recycler_item,parent,false);
        return new VH(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        BoardItem item = boardItems.get(position);


        holder.binding.tvTitle.setText(item.title);
        holder.binding.tvMsg.setText(item.msg);
        holder.binding.tvDate.setText(item.date);
    }

    @Override
    public int getItemCount() {
        return boardItems.size();
    }

    ////////////////////////////////////////////////////////////////////////
    class VH extends RecyclerView.ViewHolder {

        RecyclerItemBinding binding;

        public VH(@NonNull View itemView) {
            super(itemView);
            binding=RecyclerItemBinding.bind(itemView);
        }
    }///////////////////////////////////////////////////////////////VH
}
