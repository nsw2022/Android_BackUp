package com.nsw2022.tpqucikplacebykakaosearchapi.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.firestore.FirebaseFirestore
import com.nsw2022.tpqucikplacebykakaosearchapi.G
import com.nsw2022.tpqucikplacebykakaosearchapi.R
import com.nsw2022.tpqucikplacebykakaosearchapi.databinding.ActivityEmailLoginBinding
import com.nsw2022.tpqucikplacebykakaosearchapi.model.UserAccount

class EmailLoginActivity : AppCompatActivity() {
    val binding:ActivityEmailLoginBinding by lazy { ActivityEmailLoginBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //툴바에 업버튼 만들기
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)

        binding.btnSignIn.setOnClickListener { clickSignIn() }

    }//OnCreate//////////////////////////////////////

    //업버튼 클릭시에 액티비티를 종료
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun clickSignIn(){
        var email=binding.etEmail.text.toString()
        var password = binding.etPassword.text.toString()

        // Firebase Firestore DB에서 이메일 로그인 여부 확인
        val db:FirebaseFirestore = FirebaseFirestore.getInstance()

        db.collection("emailUsers")
            .whereEqualTo("email",email)
            .whereEqualTo("password",password)
            .get().addOnSuccessListener {
                if (it.documents.size > 0){//where 조건에 맞는 데이터가 있다는 것임.
                    // 로그인 성공
                    // [ 회원정보를 다른 Activity 에서도 사용할 가능성 있음으로..  전역변수에 처럼 클래스 이름만으로 사용가능한 변수애 저장 ]
                    var id = it.documents[0].id //document 의 랜덤한 식별자
                    G.userAccount= UserAccount(id, email)

                    //로그인 성공했으니
                    val intent:Intent = Intent(this,MainActivity::class.java)

                    // 다른 액티비티로 넘어가면서 task에 있는 모든 액티비티들을 제거하고 새로운 task 로 시작
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)

                }else{
                    //로그인 실패
                    AlertDialog.Builder(this).setMessage("이메일과 비밀번호를 다시 확인해 주시기 바랍니다.").show()
                    binding.etEmail.requestFocus()
                    binding.etEmail.selectAll()
                }
            }.addOnFailureListener {
                Toast.makeText(this, "서버오류 : ${it.message}", Toast.LENGTH_SHORT).show()
            }


    }
}