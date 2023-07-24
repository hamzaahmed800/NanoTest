package com.nano.test.util

import android.app.Activity
import android.app.AlertDialog

import com.nano.test.R

class LoadingDialog(val mActivity: Activity) {
    private lateinit var isdialog: AlertDialog
    fun startLoading(){
        /**set View*/
        val infalter = mActivity.layoutInflater
        val dialogView = infalter.inflate(R.layout.loading_item,null)
        /**set Dialog*/
        val bulider = AlertDialog.Builder(mActivity)
        bulider.setView(dialogView)
        bulider.setCancelable(false)
        isdialog = bulider.create()
        isdialog.show()
    }
    fun isDismiss(){
        if(isdialog != null && isdialog.isShowing){
            isdialog.dismiss()
        }
    }
}