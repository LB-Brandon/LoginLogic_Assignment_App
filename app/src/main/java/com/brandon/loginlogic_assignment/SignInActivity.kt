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

        initializeViews()
        setListeners()

    }

    private fun initializeViews() {
        etEmail = findViewById(R.id.et_email)
        etPassword = findViewById(R.id.et_password)
        btnSignIn = findViewById(R.id.btn_sign_in)
        btnSignUp = findViewById(R.id.btn_sign_up)
    }

    private fun setListeners() {
        btnSignIn.setOnClickListener {
            if (isSignInValid()) {
                startHomeActivity()
                showToast("로그인 성공")
            } else {
                showToast("아이디/비밀번호를 확인해주세요")
            }
        }

        getContent = registerForActivityResult(MyActivityResultContract()) { result ->
            if (result != null) {
                val userId = result.getString("userId")
                val password = result.getString("password")

                // 반환된 결과값 사용
                etEmail.setText(userId)
                etPassword.setText(password)
            } else {
                Log.d("SignIn", "No result")
            }
        }

        btnSignUp.setOnClickListener {
            startSignUpActivity()
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


    private fun isSignInValid(): Boolean {
        return etEmail.text.isNotEmpty() && etPassword.text.isNotEmpty()
    }

    private fun startHomeActivity() {
        val intent = Intent(this, HomeActivity::class.java)
        intent.putExtra("email", etEmail.text.toString())
        startActivity(intent)
    }

    private fun startSignUpActivity() {
        val intent = Intent(this, SignUpActivity::class.java)
        getContent.launch(intent)
    }
}