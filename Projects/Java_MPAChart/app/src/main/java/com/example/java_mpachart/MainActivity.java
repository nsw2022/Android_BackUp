package com.example.java_mpachart;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.java_mpachart.databinding.ActivityMainBinding;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    LineChart lineChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //1. 데이터셋에 데이터 넣기
        LineDataSet lineDataSet1 = new LineDataSet(data1(),"Data Set1");

        //2. 리스트셋에 데이터 추가
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSet1);

        //3. 라인데이터에 리스트 추가
        LineData data = new LineData(dataSets);

        //4. 차트에 라인데이터 추가
        lineChart.setData(data);

        //5. 차트초기화
        lineChart.invalidate();




    }//onCreate

    private ArrayList<Entry> data1(){

        ArrayList<Entry> dataList = new ArrayList<>();

        dataList.add(new Entry(0,10));
        dataList.add(new Entry(0,20));
        dataList.add(new Entry(0,30));
        dataList.add(new Entry(0,40));

        return dataList;
    };
}