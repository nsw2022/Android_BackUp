package com.nsw2022.ex25listviewcustom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

// 아답터라면 반드시 구현해야만 기능 메소드 4개를 가진 BaseAdapter를 상속받기
public class MyAdapter extends BaseAdapter {

    Context context;
    ArrayList<Item> items;
    public MyAdapter( Context context,ArrayList<Item>items  ){
        this.context=context;
        this.items=items;
    }


    @Override
    public int getCount() {

        return items.size();
    }

    @Override
    public Object getItem(int i) {

        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    //현재번째 만들어야할 항목 뷰1개의 모양을 객체로 만들어 리턴하는 기능메소드(가장중요)
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        // 1) create view : listview_item.xml 모양대로 뷰 객체 1개를 생성

        // 혹시 기존에 재활용할 뷰가 없는가? - 두번째 파라미터 : view
        if( view == null){
            // layout 폴더안에 있는 XML 문서를 읽어와서 View 객체로 생성해주는 능력을 가진 객체 소환!
            LayoutInflater layoutInflater=LayoutInflater.from(context);
            view=layoutInflater.inflate(R.layout.listview_item, viewGroup, false);
        }



        // 2) bind view : 위에서 만들어진 view 객체에 현재번째 아이템의 값들을 연결

        // 이 메소드의 첫번째 파라미터 i : 현재 만들번째의 position - 0,1,2,3....

        Item item = items.get(i);

        // 아이템 1개 뷰 안에 있는 자식뷰들 참조하기
        ImageView iv=view.findViewById(R.id.iv);
        TextView tvName=view.findViewById(R.id.tv_name);
        TextView tvNation=view.findViewById(R.id.tv_nation);;

        tvName.setText( item.name  );
        tvNation.setText( item.nation );
        iv.setImageResource( item.imgId );

        return view;
    }
}
