package com.brandon.loginlogic_assignment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity

class SignInActivity : AppCompatActivity() {
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnSignIn: Button
    private lateinit var btnSignUp: Button

    private lateinit var getContent: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        etEmail = findViewById(R.id.et_email)
        etPassword = findViewById(R.id.et_password)
        btnSignIn = findViewById(R.id.btn_sign_in)
        btnSignUp = findViewById(R.id.btn_sign_up)

        btnSignIn.setOnClickListener {
            if (etEmail.text.isEmpty() || etPassword.text.isEmpty()) {
                Toast.makeText(this, "아이디/비밀번호를 확인해주세요", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, HomeActivity::class.java)
                intent.putExtra("email", etEmail.text.toString())
                startActivity(intent)
                Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show()
            }
        }

//        getContent =
//            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//                if (result.resultCode == RESULT_OK) {
//                    // 결과 값 받아옴
//                    val selectedData = result.data?.data
//                }else{
//                    // 결과 값 선택 실패
//                }
//            }
        getContent = registerForActivityResult(MyActivityResultContract()) { result ->
            if (result != null) {
                val userId = result.getString("userId")
                val password = result.getString("password")

                // 결과값 사용
                etEmail.setText(userId)
                etPassword.setText(password)
            } else {
                Log.d("SignIn", "No result")
            }
        }

        btnSignUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
//            startActivity(intent)
            getContent.launch(intent)
        }
    }
}