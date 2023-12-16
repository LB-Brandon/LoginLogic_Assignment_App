package com.brandon.loginlogic_assignment

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlin.random.Random

class HomeActivity : AppCompatActivity() {

    private lateinit var ivProfile: ImageView
    private lateinit var btnFinish: Button
    private lateinit var tvName: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        initializeViews()
        setProfileImage()
        setIntentData()

        btnFinish.setOnClickListener {
            finish()
        }

    }

    private fun setIntentData() {
        val receivedIntent = intent
        val receivedData = receivedIntent.getStringExtra("email")
        tvName.text = receivedData ?: "None"
    }

    private fun setProfileImage() {
        val profileImages = listOf(
            R.drawable.ic_profile1,
            R.drawable.ic_profile2,
            R.drawable.ic_profile3
        )
        val randomImage = profileImages.random()
        ivProfile.setImageDrawable(ContextCompat.getDrawable(this, randomImage))
    }

    private fun initializeViews() {
        ivProfile = findViewById(R.id.iv_profile)
        btnFinish = findViewById(R.id.btn_finish)
        tvName = findViewById(R.id.tv_id)
    }
}