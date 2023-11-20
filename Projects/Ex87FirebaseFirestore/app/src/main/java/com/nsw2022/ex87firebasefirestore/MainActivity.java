package com.nsw2022.ex87firebasefirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.nsw2022.ex87firebasefirestore.databinding.ActivityMainBinding;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    //Firebase 연동

    ActivityMainBinding binding;

   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnSave.setOnClickListener(v->clickSave());
        binding.btnLoad.setOnClickListener(v->clickLoad());
    }

    void clickLoad(){
       // FireStore DB 데이터들 가져오기

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        CollectionReference userRef = firebaseFirestore.collection("users");
        userRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                QuerySnapshot snapshots = task.getResult();
                StringBuffer buffer = new StringBuffer();
                for( QueryDocumentSnapshot snapshot : snapshots ){

                    Map<String,Object> user = snapshot.getData();
                    String name=user.get("name").toString();
                    int age=Integer.parseInt(user.get("age").toString());
                    String address=user.get("address").toString();

                    buffer.append(name+ " : " + age + " - " + address + "\n");

                }

                binding.tv.setText(buffer.toString());
            }
        });

    }

    void clickSave(){
        //저장할 데이터들
        String name = binding.etName.getText().toString();
        int age = Integer.parseInt(binding.etAge.getText().toString());
        String address = binding.etAddress.getText().toString();

        //firebase db에 저장하기위해 Map Collection 으로 묶어서 저장
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        Map<String, Object> person = new HashMap<>();
        person.put("name",name);
        person.put("age",age);
        person.put("address",address);

        //Collection을 참조하는 참조객체 [ MySQL(RDMS)의 table 이름같은 역할 ]
        CollectionReference peopleRef = firebaseFirestore.collection("people");//없으면 만듦.

        //한줄의 데이터가 추가됨. 한줄(Document)을 이름은 자동으로 랜덤하게 부여됨.
//        peopleRef.document().set(person).addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void unused) {
//                Toast.makeText(MainActivity.this, "save success", Toast.LENGTH_SHORT).show();
//            }
//        });

        // document 추가할때 이름을 지정할 수 있음. 만약 순서대로 저장되도록 하고 싶다면..
        // 일반적으로는 날짜와 시간값을 이용함.
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
        String timeStamp =sdf.format(new Date());

//        peopleRef.document(timeStamp).set(person).addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void unused) {
//                Toast.makeText(MainActivity.this, "saved", Toast.LENGTH_SHORT).show();
//            }
//        });

        // 개발자가 만든 Data 용 class 를 곧바로 저장하는 것도 가능합니다.
        // HashMap 으로 데이터를 만들지 않고. 데이터들을 멤버로 가진 Person 객체를 한방에 저장하기
        CollectionReference userRef = firebaseFirestore.collection("users");
        PersonVO p = new PersonVO(name,age,address);
        userRef.add(p); //userRef.document().set(p); 의 축약 기능


    }
}