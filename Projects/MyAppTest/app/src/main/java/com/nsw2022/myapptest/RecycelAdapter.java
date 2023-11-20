package com.nsw2022.myapptest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecycelAdapter extends RecyclerView.Adapter<RecycelAdapter.VH> {
    Context context;
    ArrayList<RecycelItem> libraryItems;

    public RecycelAdapter(Context context, ArrayList<RecycelItem> libraryItems) {
        this.context = context;
        this.libraryItems = libraryItems;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View itemView = layoutInflater.inflate(R.layout.recycelr_item,parent,false);

        VH holder = new VH(itemView);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        RecycelItem libraryItem=libraryItems.get(position);

        holder.lib_name.setText(libraryItem.LBRRY_NAME);
        holder.lib_guadd.setText(libraryItem.CODE_VALUE);
        holder.lib_add.setText(libraryItem.ADRES);
        holder.lib_tel.setText(libraryItem.TEL_NO);
        holder.lib_hoilday.setText(libraryItem.FDRM_CLOSE_DATE);
    }

    @Override
    public int getItemCount() {
        return libraryItems.size();
    }

    class VH extends RecyclerView.ViewHolder{
        TextView lib_name;          //도서관이름
        TextView lib_guadd;          //구주소
        TextView lib_add;               //상세주소
        TextView lib_tel;              //번호
        TextView lib_hoilday;     //공휴일

        public VH(@NonNull View itemView) {
            super(itemView);
            lib_name=itemView.findViewById(R.id.tv_name_lib);
            lib_guadd=itemView.findViewById(R.id.tv_guaddress_lib);
            lib_add=itemView.findViewById(R.id.tv_address_lib);
            lib_tel=itemView.findViewById(R.id.tv_tel_lib);
            lib_hoilday=itemView.findViewById(R.id.tv_holiday_lib);

        }
    }
}
