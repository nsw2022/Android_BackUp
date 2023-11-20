package com.nsw2022.ex28recyclerview2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.VH> {

    Context context;
    ArrayList<Item> items;

    public MyAdapter(Context context, ArrayList<Item> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater=LayoutInflater.from(context);

        //res.layout/recyclerview_item.xml 문서를 읽어와서 view객체로 만들기
        View itemView=layoutInflater.inflate(R.layout.recyclerview_item,parent,false);

        VH holder = new VH(itemView);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {

        //현재번째 아이템 얻어오기
        Item item = items.get(position);

        holder.tvName.setText(item.name);
        holder.tvMsg.setText(item.message);

        // 이미지 설정 - setImageResource()기능으로 이미지를 설정하면. gif나 네트워크 상에 있는 이미지들은 보여지지 않음
        //holder.ivProfile.setImageResource(item.profileImgID);
        //holder.ivImg.setImageResource(item.imgId);

        // gif or 네트워크 이미지들을 적절한 크기로 알아서 편하게 보여주는 외부 라이브러리 더 선호
        // 이미지 로드 라이브러리 : Picasso or Glide(구글 소속 - bumptect)
        Glide.with(context).load(item.profileImgID).into(holder.ivProfile);
        Glide.with(context).load(item.imgId).into(holder.ivImg);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    //inner class - ViewHoler [ 아이템 뷰 1개 안에 있는 자식뷰들의 참조변수를 보관하는 용도의 클래스 ]
    class VH extends RecyclerView.ViewHolder{

        TextView tvName;
        TextView tvMsg;

        CircleImageView ivProfile;
        ImageView ivImg;
        ImageView btnMore;

        public VH(@NonNull View itemView) {
            super(itemView);
            tvName= itemView.findViewById(R.id.tv_name);
            tvMsg= itemView.findViewById(R.id.tv_msg);


            ivProfile=itemView.findViewById(R.id.iv_profile);
            ivImg=itemView.findViewById(R.id.iv_img);
            btnMore=itemView.findViewById(R.id.btn_more);



        }
    }////////////////////////////////////////inner class...

}////////////////////////////MyAdter class...
