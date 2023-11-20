package nsw2022.nsw2022.ex01hellobyjava;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // 화면에 보여지는 뷰들의 참조변수는 가급적 멤버변수로 만들 것을 권장
     TextView tv;
     Button btn;

     LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        //java언어만으로 화면 꾸미기

        // 액티비티는 view클래스를 상속해서 만든 클래스들만 놓여질 수 있음.

        // 글씨를 보여주는 액자(TextView) 객체 생성 및 설정

        tv=new TextView(this);
        tv.setText("Hello andriod");


        // 액티비티에 Textview를 붙이기
        // this.setContentView(tv);

        //버튼의 역할을 하는 갹채 샹종자를
        btn=new Button(this);
        btn.setText("버튼");

        // 액티비티에 Button을 붙이기
        // this.setContentView(btn);

        // 기존에 설정되었던 뷰인 TextView가 없어지고.. Button으로 대체됨.
        // 액티비티는 한번에 1개의 뷰만 보여주도록 설계되어 있음.

        // 그래서 여러개의 뷰를 화면에 배치하려면..
        // View여러개를 담을 수 있는 ViewGroup 용 객체를 1개 만들어서 거기에 모두 붙이고
        // 그 ViewGroup 1개를 액티비티에 설정.

        // ViewGroup 용 클래스들 중에서 뷰들을 수직 또는 수평으로 배치시켜주는
        // LinearLayout 이라는 클래스를 객체로 생성하여 TextView와 Button을 붙이기

        layout= new LinearLayout(this);
        layout.addView(tv);
        layout.addView(btn);

        // 액티비티에 ViewGroup을 붙이기
        this.setContentView(layout);

        // 버튼이 클릭되었을때 TextView의 글씨를 변경
        // 버튼이 클릭되는 것에 듣고 반응하는 리스너 객체 생성 및 설정
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {// 버튼이 클릭되면 자동 실행되는 콜백 메소드
                //TextView의 글씨를 변경
                tv.setText("Nice to meet you");

            }
        });

    }
}