package com.nsw2022.myapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.VH> {
    Context context;
    ArrayList<LibraryItem> libraryItems;

    public MyAdapter(Context context, ArrayList<LibraryItem> items) {
        this.context = context;
        this.libraryItems = items;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View viewItem = layoutInflater.inflate(R.layout.item_libray,parent,false);

        VH holder=new VH(viewItem);



        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        LibraryItem lib = libraryItems.get(position);

        holder.tvName.setText(lib.LBRRY_NAME);
        holder.tvHoliday.setText(lib.FDRM_CLOSE_DATE);
        holder.tvAdd.setText(lib.ADRES);
        holder.tvTel.setText(lib.TEL_NO);


    }

    @Override
    public int getItemCount() {
        return libraryItems.size();
    }

    class VH extends RecyclerView.ViewHolder{

        TextView tvName;            //이름
        TextView tvHoliday;         //공휴일
        TextView tvTel;             //도서관번호
        TextView tvAdd;             //주소



        public VH(@NonNull View itemView) {
            super(itemView);

           tvName=itemView.findViewById(R.id.tv_lib_name);
           tvHoliday=itemView.findViewById(R.id.tv_holiday_lib);
           tvTel=itemView.findViewById(R.id.tv_tel_lib);
           tvAdd=itemView.findViewById(R.id.tv_address_lib);



        }
    }
}
