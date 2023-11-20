package com.nsw2022.recyclerpractice;

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

import de.hdodenhof.circleimageview.CircleImageView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.VH> {
    Context context;
    ArrayList <Item>items;

    public MyAdapter(Context context, ArrayList items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View itemView=layoutInflater.inflate(R.layout.relativelayoutview_item,parent,false);

        VH holder = new VH(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        Item item = items.get(position);

        holder.tvName.setText(item.name);
        holder.tvMsg.setText(item.message);


    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    class VH extends RecyclerView.ViewHolder {
        CircleImageView ivProfile;
        ImageView ivImg;
        ImageView btnMore;

        TextView tvName;
        TextView tvMsg;
        public VH(@NonNull View itemView) {
            super(itemView);

            ivImg=itemView.findViewById(R.id.iv_img);
            ivProfile=itemView.findViewById(R.id.iv_profile);
            btnMore=itemView.findViewById(R.id.btn_more);

            tvMsg=itemView.findViewById(R.id.tv_msg);
            tvName=itemView.findViewById(R.id.tv_name);
        }
    }
}
