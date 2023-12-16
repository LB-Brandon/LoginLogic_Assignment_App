package com.brandon.loginlogic_assignment

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
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
        setListeners()

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


    private fun setListeners() {
        setFocusChangeListener(etName, tvNameValidation, this::isNameValid)
        setFocusChangeListener(etUserEmail, tvEmailValidation, this::isUserEmailValid)

        etPassword.addTextChangedListener {
            validatePassword()
        }
        setFocusChangeListener(
            etPasswordConfirm, tvPasswordConfirmValidation, this::isPasswordConfirmValid
        )
        btnSignUp.setOnClickListener {
            if (validateUserInputs()) {
                val resultIntent = Intent().apply {
                    putExtra("userId", "${etUserEmail.text}@${getEmailDomain()}")
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
                        etDomainEmail.isVisible = true
                    }

                    else -> {
                        etDomainEmail.isVisible = false
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // 아무 것도 선택되지 않았을 때의 처리
            }
        }
    }

    private fun setFocusChangeListener(
        editText: EditText, errorTextView: TextView, validationFunc: () -> Boolean
    ) {
        editText.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                errorTextView.isVisible = validationFunc().not()
            }
        }
    }

    private fun validateUserInputs(): Boolean {
        return isNameValid() && isUserEmailValid() && isPasswordValid() && isPasswordConfirmValid()
    }

    private fun validatePassword() {
        val input = etPassword.text.toString()
        isPasswordValid = when {
            input.isEmpty() -> {
                tvPasswordCondition.isVisible = true
                tvPasswordValidation.isVisible = false
                false
            }

            input.length < MIN_PASSWORD_LENGTH -> {
                tvPasswordCondition.isVisible = false
                tvPasswordValidation.text = "5자리 이상 입력"
                tvPasswordValidation.isVisible = true
                false
            }

            (input.any { it.isUpperCase() }).not() -> {
                tvPasswordCondition.isVisible = false
                tvPasswordValidation.text = "대문자 1개 이상 입력"
                tvPasswordValidation.isVisible = true
                false
            }

            (input.all { it.isLetterOrDigit() }) -> {
                tvPasswordCondition.isVisible = false
                tvPasswordValidation.text = "특수문자 1개 이상 입력"
                tvPasswordValidation.isVisible = true
                false
            }

            else -> {
                tvPasswordCondition.isVisible = false
                tvPasswordValidation.isVisible = false
                true
            }
        }
    }


    private fun isPasswordConfirmValid() =
        (etPassword.text.toString() == etPasswordConfirm.text.toString())

    private fun isUserEmailValid() = etUserEmail.text.isNotEmpty()


    private fun isNameValid() = etName.text.isNotEmpty()

    private fun isPasswordValid() = isPasswordValid

    private fun getEmailDomain(): String {
        return emailDomain.ifEmpty {
            etDomainEmail.text.toString()
        }
    }

}