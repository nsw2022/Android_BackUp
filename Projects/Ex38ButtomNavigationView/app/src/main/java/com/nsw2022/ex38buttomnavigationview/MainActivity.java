package com.nsw2022.ex38buttomnavigationview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bnv;

    ArrayList<Fragment> fragments = new ArrayList<>();
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 처음에 보여질 Fragment를 1개 만들어서 붙이기 {HomeFragment}
        fragments.add(new HomeFragment());
        fragments.add(null);
        fragments.add(null);

        //프레그먼트를 동적으로 추가/삭제/show/hide 같은 제어를 하고 싶다면 관리자에게 요청
        //프레그먼트 관리자 객체를 소환
        fragmentManager=getSupportFragmentManager();

        // id가 fragment_container 인 ViewGroup에 HomeFragemnet를 붙이기
        fragmentManager.beginTransaction().add(R.id.fragment_container,fragments.get(0)).commit();


        bnv=findViewById(R.id.bnv);
        bnv.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                //프래그먼트를 동적으로 제어하는 작업시작 -- 하면.. 작업자가 리턴됨
                FragmentTransaction tran= fragmentManager.beginTransaction();

                //기존에 보여주고 있는 프레그먼트가 누군지 모르게에 그냥 전원다 안 보이도록
                if ( fragments.get(0) != null )tran.hide(fragments.get(0));
                if ( fragments.get(1) != null )tran.hide(fragments.get(1));
                if ( fragments.get(2) != null )tran.hide(fragments.get(2));

                switch (item.getItemId()){
                    case R.id.menu_bnv_home:
                        tran.show(fragments.get(0));
                       break;

                    case R.id.menu_bnv_favorite:
                        if (fragments.get(1)==null){
                            fragments.set(1, new FavoriteFragment());
                            tran.add(R.id.fragment_container,fragments.get(1));
                        }
                        tran.show(fragments.get(1));
                      break;

                    case  R.id.menu_bnv_location:
                        if (fragments.get(2)==null){
                            fragments.set(2,new LocationFragment());
                            tran.add(R.id.fragment_container,fragments.get(2));
                        }
                        tran.show(fragments.get(2));
                     break;

                }//switch

                //작업완료
                tran.commit();

                // return이 true가 아니면 아이템 선택하는 효과 색상이 반영되지 않음.
                return true;
            }
        });



    }
}