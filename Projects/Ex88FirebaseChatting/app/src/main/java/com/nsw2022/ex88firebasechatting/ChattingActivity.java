package com.nsw2022.ex88firebasechatting;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.nsw2022.ex88firebasechatting.databinding.ActivityChattingBinding;
import com.nsw2022.ex88firebasechatting.databinding.ActivityMainBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class ChattingActivity extends AppCompatActivity {

    ActivityChattingBinding binding;

    String chatName = "chat"; //채팅방 명 [ Firebase Firestore DB의 Collection 이름으로 사용 ]

    FirebaseFirestore firebaseFirestore;
    CollectionReference chatRef;

    ArrayList<MessageItem> messageItems = new ArrayList<>();
    ChatAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityChattingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //제목줄(ActionBar)의 제목글씨와 서브제목 글씨를 설정
        getSupportActionBar().setTitle(chatName);      //채팅방 이름
        getSupportActionBar().setSubtitle(G.nickName); //닉네임

        // 리사이클러뷰를 만들어주는 아답터 객체 생성 및 설정
        adapter=new ChatAdapter(this,messageItems);
        binding.recyclerView.setAdapter(adapter);


        //Firebase Firestore DB 관리자 객체 소환
        firebaseFirestore = FirebaseFirestore.getInstance();

        // 채팅방 이름으로 Collection 만드로 이를 참조
        chatRef=firebaseFirestore.collection(chatName);

        //chatRef 에 데이터가 변경되는 것을 인지하는 리스너 추가.
        chatRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                // 변경된 Document 항목들만 찾아주세요
                List<DocumentChange> documentChangeList = value.getDocumentChanges();
                for ( DocumentChange documentChange : documentChangeList  ){
                    // 변경된 Document 의 데이터를 촬영한 SnapShot 얻어오기
                    DocumentSnapshot snapshot = documentChange.getDocument();

                    // document 안에 있는 Field 값을 얻어오기
                    Map<String, Object> msg = snapshot.getData();
                    String nickName=msg.get("nickName").toString();
                    String message=msg.get("message").toString();
                    String profileUri=msg.get("profileUrl").toString();
                    String time=msg.get("time").toString();

                    // 읽어들인 메세지를 리사이클러뷰 보여주는 대량의 데이터 ArrayList 에 추가
                    MessageItem item = new MessageItem(nickName,message,profileUri,time);
                    messageItems.add(item);
                    //Toast.makeText(ChattingActivity.this, ""+messageItems.size(), Toast.LENGTH_SHORT).show();

                    //리사이클러뷰를 갱신
                    adapter.notifyItemInserted(messageItems.size()-1);
                    binding.recyclerView.scrollToPosition(adapter.getItemCount()-1); //리사이클러뷰의 스크롤 위치를 마지막으로

                }

            }
        });

        binding.btnSend.setOnClickListener(v->clickSend());

    }

    void clickSend(){
        //Firestore DB에 저장할 데이터들 ( 닉네임, 메세지, 프로필 이미지 URL, 작성된 시간)
        String nickName = G.nickName;
        String message = binding.et.getText().toString();
        String profileUrl = G.profileURL;

        //메세지작성시간을 문자열로 [ 시:분 ]
        Calendar calendar = Calendar.getInstance(); //현재시간 객체
        String time = calendar.get(Calendar.HOUR_OF_DAY) + ":" +calendar.get(Calendar.MINUTE);

        // 파이어 스토어 DB에 저장할 값들을 가진 MessageItem 객체 생성
        MessageItem item = new MessageItem(nickName,message,profileUrl,time);

        // chatRef 가 참조하는 Collection 에 MessageItem 객체를 통째로 값 추가
        chatRef.document("MSG"+ System.currentTimeMillis()).set(item);

        //다음메세지 입력을 위해 EditText 의 글씨 없애기
        binding.et.setText("");

        //소프트 키보드 안보이도록
        InputMethodManager imm= (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);

    }
}