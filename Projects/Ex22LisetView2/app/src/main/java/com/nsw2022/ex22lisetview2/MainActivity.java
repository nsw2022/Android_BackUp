package com.nsw2022.ex22lisetview2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;

    EditText et;
    Button btn;

    // 대량의 데이터를 저장할 List 객체 - 배열과 다르게 요소의 개수 추가/삭제 가능함.
    ArrayList<String> datas=new ArrayList<String>();

    // 아답터 중에서 String 1개를 TextView 1개로 연결해주는 View들을 만들어주는 AraayAdatper 참조변수
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView=findViewById(R.id.listview);
        et=findViewById(R.id.et);
        btn=findViewById(R.id.btn);



        // 대량의 데이터를 리스트에 추가 - 실무에서는 DB나 서버에 있는 자료를 받아옴, 지금은 테스트로..
        datas.add(new String("aaa"));
        datas.add(new String("bbb"));
        datas.add("ccc");// 자동 new String("ccc")

        // 데이터의 갯수만큼 view 객체를 만들어주는 아답터 객체 생성
        adapter= new ArrayAdapter(this,R.layout.listview_item,datas);
        listView.setAdapter(adapter);

        // EditText에 글씨를 입력하고 [ADD]버튼을 클릭하면 리스트뷰의 항목으로 추가해보기..
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // EditText에 써있는 글씨를 얻어오기
                String s= et.getText().toString();

                // 리스트뷰에 항목으로 추가하는 것이 아님!! 리스트뷰가 보여줄 뷰를 만들어내는
                // 아답터에게!! 데이터를 추가해야함 근데.. 아답터는 대량의 데이터를 가진 datas 라는
                // 리스트의 요소개수대로 만들어냄. 그러므로 아답터에 데이터를 추가하는 것이 아니라.

                //대량의 데이터를 가진 datas 리스트 배열 추가해야만 함
                datas.add(s);
                // 아답터에게 데이터가 변경되었음을 공지해줘야 리스트뷰에 새로운 데이터가 갱신되어 보여짐
                adapter.notifyDataSetChanged();

                //리스트뷰의 스크롤 위치를 마지막으로 이동하고 싶다면.
                listView.setSelection(datas.size()-1);

                et.setText("");
            }
        });

        // 리스트뷰의 항목(item)을 롱~~~~~~클릭하였을때 해당 항목을 삭제해보기..
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                // 세번째 파라미터 i : 클릭한 아이템의 포지션번호 : 0,1,2,3,4....

                // 리스트뷰에 항목을 지우는 것이아니라.. 대량의 데이터를 가진 리스트배열에서 삭제해야함.
                datas.remove(i);
                adapter.notifyDataSetChanged();//아답터에게 데이터가 변경되었음을 공지

                // 리턴값을 true 해야 롱클릭 후에 클릭이벤트가 발동하지 않음.
                return true;
            }
        });



    }
}