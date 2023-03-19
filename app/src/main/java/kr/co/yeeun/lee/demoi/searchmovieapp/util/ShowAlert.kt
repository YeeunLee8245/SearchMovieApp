package kr.co.yeeun.lee.demoi.searchmovieapp.util

import android.app.AlertDialog
import android.content.Context
import kr.co.yeeun.lee.demoi.searchmovieapp.R

object ShowAlert {
    fun showAlert(context: Context, msg: String) {
        AlertDialog.Builder(context)
            .setMessage(msg)
            .setCancelable(false)
            .setPositiveButton(context.getString(R.string.ok)) {alert, index -> alert?.dismiss()}
            .create()
            .show()
    }
}