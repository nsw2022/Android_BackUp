package com.nsw2022.sqlite_phonebook;

import android.content.Context;
import android.renderscript.ScriptGroup;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PhoneBookAdapter {

    Context context;
    ArrayList<Phonbook> PhoneList = new ArrayList<>();

    public PhoneBookAdapter(Context context, ArrayList<Phonbook> phoneList) {
        this.context = context;
        PhoneList = phoneList;
    }

    class VH extends RecyclerView.ViewHolder{
        TextView id_text, name_text;
        ImageView imageView;
        LinearLayout mainLayout;

        public VH(@NonNull View itemView) {
            super(itemView);



        }
    }

}
