package com.nsw2022.ex26listviewholder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {

    Context context;
    ArrayList<String> items;

    //생성자
    public MyAdapter(Context context,ArrayList<String> items){
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


    //리스트가 보여줄 아이템 뷰 1개를 만들어서 그 안에 현재번째 아이템에 값을 연결하여 리턴해주는 기능
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        //첫번째 파라미터 i : 현재번째 아이템 뷰의 포지션
        //두번째 파라미터 view : 재활용할 view를 참조하는 변수
        // 세번째 파라미터 view : 어답터가 리턴한 뷰를 보여줄 ListView or GridView or Spinner

        //1) create view

        if ( view == null) {//재활용할 뷰가 없는가?
            //res/layout/listview_item.xml 문서를 읽어서 View객체로 생성
            LayoutInflater layoutInflater=LayoutInflater.from(context);
            view=layoutInflater.inflate(R.layout.listview_item,viewGroup,false);//세번째 파라미터값 fasle : 지금 당장 뷰를 뷰그룸에 붙일것인가 여부

            // 만들어진 뷰의 자식 참조변수를 보관하려는 ViewHolder객체 만들기
            ViewHolder holder=new ViewHolder(view);
            view.setTag(holder);

        }

        // 만들어져 있는 view 안에 자식뷰들 참조하기 - findViewByid()라는 기능이 너무 느림. 성능이 떨어짐.
        //TextView tv = view.findViewById(R.id.tv); <- 성능이슈발생
        //findView를 통해 참조하는 객체의 주소를 가진 참조변수 tv를 보관하면 나중에 다시 찾을 필요가 없음.
        // 이 참조변수들을 보관하는 목적의 클래스를 설계 - ViewHolder

        // 아에템뷰안에 tag를 통해 저장해 놓았던 ViewHolder를 꺼내오기
        ViewHolder holder = (ViewHolder) view.getTag();


        //2) bind view
        String item=items.get(i);
        holder.tv.setText(item);


        return view;
    }

    //innder class////////////////////////////
    // 아이템뷰 1개 안에 있는 자식뷰들의 참조변수를 보관하는 클래스
    class ViewHolder{

        TextView tv;

        //생성자메소드
        public ViewHolder(View itemview){
            tv=itemview.findViewById(R.id.tv);
        }

    }////////////////////////

}// MyAdapter class..


