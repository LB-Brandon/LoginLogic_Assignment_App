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

        ivProfile = findViewById(R.id.iv_profile)
        btnFinish = findViewById(R.id.btn_finish)
        tvName = findViewById(R.id.tv_id)

        val receivedIntent = intent
        val receivedData = receivedIntent.getStringExtra("email")
        if (receivedData != null) {
            tvName.text = receivedData
        } else {
            Log.d("Home Activity", "No Intent Data")
        }

        btnFinish.setOnClickListener {
            finish()
        }

        when (Random.nextInt(1, 4)) {
            1 -> {
                ivProfile.setImageDrawable(
                    ContextCompat.getDrawable(this, R.drawable.ic_profile1)
                )
            }

            2 -> {
                ivProfile.setImageDrawable(
                    ContextCompat.getDrawable(this, R.drawable.ic_profile2)
                )
            }

            3 -> {
                ivProfile.setImageDrawable(
                    ContextCompat.getDrawable(this, R.drawable.ic_profile3)
                )
            }
        }


    }
}