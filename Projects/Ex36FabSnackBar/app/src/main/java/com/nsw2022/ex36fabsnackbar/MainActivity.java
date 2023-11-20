package com.nsw2022.ex36fabsnackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton fab;

    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab=findViewById(R.id.fab);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast를 개량한 SnackBar
                Snackbar snackbar=Snackbar.make(view,"This is SnackBar",Snackbar.LENGTH_INDEFINITE);
                snackbar.setAction("확인", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });

                snackbar.setActionTextColor( getResources().getColor(R.color.teal_200,null)  );
                snackbar.setTextColor(getResources().getColor(R.color.teal_700,null));

                snackbar.show();
            }
        });

        btn=findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //빈 코디네이터를 통해 스낵바가 나타나는 위치 변경시키기
                CoordinatorLayout container =findViewById(R.id.snackbar_container);

                Snackbar.make(container,"Hello Snackbar",Snackbar.LENGTH_INDEFINITE).show();

                Snackbar.make(container,"Hello Snackbar", Snackbar.LENGTH_INDEFINITE).setAction("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                }).show();

            }
        });

    }
}