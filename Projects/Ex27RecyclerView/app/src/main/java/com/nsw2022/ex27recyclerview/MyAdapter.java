package com.nsw2022.ex27recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter {

    Context context;
    ArrayList<Item> items;

    public MyAdapter(Context context, ArrayList<Item> items) {
        this.context = context;
        this.items = items;
    }

    // 재활용할 뷰가 없어서 만들어야 할때 자동으로 실행되는 매소드
    // 항목 뷰를 만들고 참조변수들을 가진 ViewHolder객체를 생성하여 return 해주는 메소드
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View itmeView=layoutInflater.inflate(R.layout.recyclerview_item,parent,false);

        //inner class로 설계한 ViewHoler 클래스를 객체로 생성
        VH vh =new VH(itmeView);

        return vh;//ViewHolder 리턴
    }


    // 헤딩반쩨(position)의 항목뷰에 items의 값들을 연결하는 메소드
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        //현재 연결할 Item 얻어오기
        Item item=items.get(position);
        VH vh=(VH)holder;
        vh.tvName.setText(item.name);
        vh.tvMsg.setText(item.message);


    }


    // 아답터가 만들 아이템 뷰의 총 갯수
    @Override
    public int getItemCount() {
        return items.size();
    }

    //inner class
    // 아이템 뷰 1개 안에 있는 자식뷰들의 참조값을 보관하는 용도의 클래스 [ ViewHolder 클래스를 상속해야만 함 ]
    class VH extends RecyclerView.ViewHolder {

        TextView tvName, tvMsg;


        // 생성자 - constructor [필수]
        public VH(@NonNull View itemView) {
            super(itemView);

            tvName=itemView.findViewById(R.id.tv_name);
            tvMsg=itemView.findViewById(R.id.tv_msg);

            //항목(아이템)뷰가 클릭될때 반응하기
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //현재 클릭한 위치( position) 언어 오기
                    int position=getAdapterPosition();

                    Item item = items.get(position);
                    Toast.makeText(context, item.name, Toast.LENGTH_SHORT).show();

                }
            });


        }
    }



}//MyAdater class..
