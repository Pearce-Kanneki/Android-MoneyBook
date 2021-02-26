package com.kanneki.moneybook.activityresult.contract

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.kanneki.moneybook.ui.scanner.ScannerActivity

class ScannerResultContract: ActivityResultContract<String, String>() {

    companion object{
        const val RESULT_CODE = "result"
    }

    override fun createIntent(context: Context, input: String?): Intent {
        return Intent(context, ScannerActivity::class.java)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): String {

        val data = intent?.getStringExtra(RESULT_CODE)
        return if (resultCode == Activity.RESULT_OK) data ?: "" else ""
    }


}