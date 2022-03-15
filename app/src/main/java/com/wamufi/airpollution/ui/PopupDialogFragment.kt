package com.wamufi.airpollution.ui

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class PopupDialogFragment(private val message: String) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setMessage(message).setPositiveButton(android.R.string.ok) { dialog, _ ->
                dialog.dismiss()
                it.finish()
            }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}