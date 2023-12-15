package com.brandon.loginlogic_assignment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.core.widget.addTextChangedListener

class SignUpActivity : AppCompatActivity() {

    private lateinit var etName: EditText
    private lateinit var etUserEmail: EditText
    private lateinit var etDomainEmail: EditText
    private lateinit var tvAt: TextView
    private lateinit var spEmail: Spinner
    private lateinit var etPassword: EditText
    private lateinit var etPasswordConfirm: EditText
    private lateinit var btnSignUp: Button

    private lateinit var tvNameValidation: TextView
    private lateinit var tvEmailValidation: TextView
    private lateinit var tvPasswordValidation: TextView
    private lateinit var tvPasswordCondition: TextView
    private lateinit var tvPasswordConfirmValidation: TextView

    private var isPasswordValid = false
    private var emailDomain = ""

    companion object {
        private const val MIN_PASSWORD_LENGTH = 5
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        initializeViews()

        etName.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                tvNameValidation.isVisible = isNameValid().not()
            }
        }

        etUserEmail.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                tvEmailValidation.isVisible = isUserEmailValid().not()
            }
        }

        etPassword.addTextChangedListener {
            // 유효성 검사(개수, 특수문자, 대문자)
            val input = etPassword.text
            if (input.isEmpty()) {
                tvPasswordCondition.isVisible = true
                tvPasswordValidation.visibility = View.INVISIBLE
                isPasswordValid = false
            } else {
                // 입력 내용 존재
                // 유효성 검사
                if (input.length < 5) {
                    tvPasswordCondition.isVisible = false
                    tvPasswordValidation.text = "5자리 이상 입력"
                    tvPasswordValidation.visibility = View.VISIBLE
                    isPasswordValid = false
                } else if ((input.any { it.isUpperCase() }).not()) {
                    tvPasswordCondition.isVisible = false
                    tvPasswordValidation.text = "대문자 1개 이상 입력"
                    tvPasswordValidation.visibility = View.VISIBLE
                    isPasswordValid = false
                } else if ((input.any { !it.isLetterOrDigit() }).not()) {
                    tvPasswordCondition.isVisible = false
                    tvPasswordValidation.text = "특수문자 1개 이상 입력"
                    tvPasswordValidation.visibility = View.VISIBLE
                    isPasswordValid = false
                    Log.d("SignUp", "특수문자")
                } else {
                    tvPasswordCondition.isVisible = false
                    tvPasswordValidation.visibility = View.INVISIBLE
                    isPasswordValid = true
                }
            }
        }

        etPasswordConfirm.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                Log.d("Signup", "${isPasswordConfirmValid()}")
                tvPasswordConfirmValidation.isVisible = isPasswordConfirmValid().not()
            }
        }

        btnSignUp.setOnClickListener {
            Log.d(
                "Signup",
                "${isNameValid()}, ${isUserEmailValid()}, ${isPasswordValid()}, ${isPasswordConfirmValid()}"
            )
            Log.d("Signup", "${etPassword.text}, ${etPasswordConfirm.text}")
            if (isNameValid() && isUserEmailValid() && isPasswordValid() && isPasswordConfirmValid()) {
                // 회원가입 완료
//                val intent = Intent(this, SignInActivity::class.java)
//                startActivity(intent)
                val resultIntent = Intent().apply {
                    putExtra("userId", "${etUserEmail.text.toString()}@${getEmailDomain()}")
                    putExtra("password", etPassword.text.toString())
                }
                setResult(RESULT_OK, resultIntent)
                finish()
            } else {
                Toast.makeText(this, "입력되지 않은 정보가 있습니다.", Toast.LENGTH_SHORT).show()
            }
        }

        spEmail.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                // set domain
                emailDomain = spEmail.getItemAtPosition(position).toString()
                when (position) {
                    0 -> {
                        // 직접입력
                        emailDomain = ""
                        etUserEmail.apply {
                            updateLayoutParams<ConstraintLayout.LayoutParams> {
                                horizontalWeight = 1f
                            }
                        }
                        etDomainEmail.apply {
                            isVisible = true
                            updateLayoutParams<ConstraintLayout.LayoutParams> {
                                horizontalWeight = 1f
                            }
                        }
                        spEmail.apply {
                            updateLayoutParams<ConstraintLayout.LayoutParams> {
                                horizontalWeight = 1f
                            }
                        }

                    }

                    else -> {
                        etUserEmail.apply {
                            updateLayoutParams<ConstraintLayout.LayoutParams> {
                                horizontalWeight = 2f
                            }
                        }
                        etDomainEmail.apply {
                            isVisible = false
                        }
                        spEmail.apply {
                            updateLayoutParams<ConstraintLayout.LayoutParams> {
                                horizontalWeight = 2f
                            }
                        }
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // 아무 것도 선택되지 않았을 때의 처리
            }
        }


    }

    private fun initializeViews() {
        etName = findViewById(R.id.et_name)
        etUserEmail = findViewById(R.id.et_user_email)
        etDomainEmail = findViewById(R.id.et_domain_email)
        tvAt = findViewById(R.id.tv_at)
        spEmail = findViewById(R.id.sp_email)
        etPassword = findViewById(R.id.et_password)
        etPasswordConfirm = findViewById(R.id.et_password_confirm)
        btnSignUp = findViewById(R.id.btn_sign_up)

        tvNameValidation = findViewById(R.id.tv_name_validation)
        tvEmailValidation = findViewById(R.id.tv_email_validation)
        tvPasswordValidation = findViewById(R.id.tv_password_validation)
        tvPasswordCondition = findViewById(R.id.tv_password_condition)
        tvPasswordConfirmValidation = findViewById(R.id.tv_password_confirm_validation)
    }


    private fun isPasswordConfirmValid() =
        (etPassword.text.toString() == etPasswordConfirm.text.toString())

    private fun isUserEmailValid() = etUserEmail.text.isNotEmpty()

    private fun isNameValid() = etName.text.isNotEmpty()

    private fun isPasswordValid() = isPasswordValid

    fun getEmailDomain(): String {
        return emailDomain.ifEmpty {
            etDomainEmail.text.toString()
        }
    }

}