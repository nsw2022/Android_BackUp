package com.nsw2022.ex38buttomnavigationview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LocationFragment extends Fragment {

    //화면을 만들기 전에 보여줄 데이터를 읽어오는 등의 작업을 수행하는 메소드
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //서버나 DB같은 곳에서 데이터를 읽어오는 코드를 작성..
        loadData();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_location, container, false);
    }

    ArrayList<LocationFragmentRecyclerItem> items= new ArrayList<>();
    RecyclerView recyclerView;
    LocationFragmentRecyclerAdapter adapter;

    ArrayList<LocationFragmentRecyclerItem> items2= new ArrayList<>();
    RecyclerView recyclerView2;
    LocationFragmentRecyclerAdapter adapter2;

    ArrayList<LocationFragmentRecyclerItem> items3= new ArrayList<>();
    RecyclerView recyclerView3;
    LocationFragmentRecyclerAdapter adapter3;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView= view.findViewById(R.id.recycler);
        adapter= new LocationFragmentRecyclerAdapter(getActivity(), items);
        recyclerView.setAdapter(adapter);

        recyclerView2= view.findViewById(R.id.recycler2);
        adapter2= new LocationFragmentRecyclerAdapter(getActivity(), items2);
        recyclerView2.setAdapter(adapter2);

        recyclerView3= view.findViewById(R.id.recycler3);
        adapter3= new LocationFragmentRecyclerAdapter(getActivity(), items3);
        recyclerView3.setAdapter(adapter3);

    }


    // 데이터를 읽어오는 코드를 작성하는 용도의 메소드
    void loadData(){

        items.add( new LocationFragmentRecyclerItem("One Piece", R.drawable.bg_one01, 3.5) );
        items.add( new LocationFragmentRecyclerItem("One Piece", R.drawable.bg_one02, 3.5) );
        items.add( new LocationFragmentRecyclerItem("One Piece", R.drawable.bg_one03, 3.5) );
        items.add( new LocationFragmentRecyclerItem("One Piece", R.drawable.bg_one04, 3.5) );
        items.add( new LocationFragmentRecyclerItem("One Piece", R.drawable.bg_one05, 3.5) );
        items.add( new LocationFragmentRecyclerItem("One Piece", R.drawable.bg_one06, 3.5) );
        items.add( new LocationFragmentRecyclerItem("One Piece", R.drawable.bg_one07, 3.5) );
        items.add( new LocationFragmentRecyclerItem("One Piece", R.drawable.bg_one08, 3.5) );
        items.add( new LocationFragmentRecyclerItem("One Piece", R.drawable.bg_one09, 3.5) );
        items.add( new LocationFragmentRecyclerItem("One Piece", R.drawable.bg_one10, 3.5) );
        items.add( new LocationFragmentRecyclerItem("One Piece", R.drawable.bg_one05, 3.5) );


        items2.add( new LocationFragmentRecyclerItem("원피스", R.drawable.bg_one01, 3.5) );
        items2.add( new LocationFragmentRecyclerItem("원피스", R.drawable.bg_one01, 3.5) );
        items2.add( new LocationFragmentRecyclerItem("원피스", R.drawable.bg_one01, 3.5) );
        items2.add( new LocationFragmentRecyclerItem("원피스", R.drawable.bg_one01, 3.5) );
        items2.add( new LocationFragmentRecyclerItem("원피스", R.drawable.bg_one01, 3.5) );
        items2.add( new LocationFragmentRecyclerItem("원피스", R.drawable.bg_one01, 3.5) );
        items2.add( new LocationFragmentRecyclerItem("원피스", R.drawable.bg_one01, 3.5) );
        items2.add( new LocationFragmentRecyclerItem("원피스", R.drawable.bg_one01, 3.5) );
        items2.add( new LocationFragmentRecyclerItem("원피스", R.drawable.bg_one01, 3.5) );
        items2.add( new LocationFragmentRecyclerItem("원피스", R.drawable.bg_one01, 3.5) );
        items2.add( new LocationFragmentRecyclerItem("원피스", R.drawable.bg_one01, 3.5) );


        items3.add( new LocationFragmentRecyclerItem("밀집모자해적단", R.drawable.bg_one01, 3.5) );
        items3.add( new LocationFragmentRecyclerItem("밀집모자해적단", R.drawable.bg_one01, 3.5) );
        items3.add( new LocationFragmentRecyclerItem("밀집모자해적단", R.drawable.bg_one01, 3.5) );
        items3.add( new LocationFragmentRecyclerItem("밀집모자해적단", R.drawable.bg_one01, 3.5) );
        items3.add( new LocationFragmentRecyclerItem("밀집모자해적단", R.drawable.bg_one01, 3.5) );
        items3.add( new LocationFragmentRecyclerItem("밀집모자해적단", R.drawable.bg_one01, 3.5) );
        items3.add( new LocationFragmentRecyclerItem("밀집모자해적단", R.drawable.bg_one01, 3.5) );
        items3.add( new LocationFragmentRecyclerItem("밀집모자해적단", R.drawable.bg_one01, 3.5) );
        items3.add( new LocationFragmentRecyclerItem("밀집모자해적단", R.drawable.bg_one01, 3.5) );

    }

}
