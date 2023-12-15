package com.brandon.loginlogic_assignment

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContract

class MyActivityResultContract : ActivityResultContract<Intent, Bundle?>() {
    override fun createIntent(context: Context, input: Intent): Intent {
        // 다른 액티비티를 열기 위한 Intent 생성
        return input
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Bundle? {
        // 여러 데이터를 Bundle로 반환
        return when {
            resultCode == RESULT_OK && intent != null -> {
                Bundle().apply {
                    // 피계약자로부터 받은 데이터를 Bundle 형태로 반환한다
                    putString("userId", intent.getStringExtra("userId"))
                    putString("password", intent.getStringExtra("password"))
                }
            }

            else -> null
        }
    }
}
