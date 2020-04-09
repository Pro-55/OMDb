package com.example.omdb.util.extensions

import android.app.ProgressDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.omdb.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.layout_confirmation_dialog.view.*

fun Fragment.showShortSnackBar(message: String?) = requireActivity().showShortSnackBar(message)

fun FragmentActivity.showShortSnackBar(message: String?) {
    Snackbar.make(
        findViewById(android.R.id.content),
        message ?: "Something went wrong!",
        Snackbar.LENGTH_SHORT
    ).show()
}

fun Fragment.showLongSnackBar(message: String?) = requireActivity().showLongSnackBar(message)

fun FragmentActivity.showLongSnackBar(message: String?) {
    Snackbar.make(
        findViewById(android.R.id.content),
        message ?: "Something went wrong!",
        Snackbar.LENGTH_LONG
    ).show()
}

fun Fragment.showShortToast(message: String) = requireActivity().showShortToast(message)

fun FragmentActivity.showShortToast(message: String) =
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

fun Fragment.showLongToast(message: String) = requireActivity().showLongToast(message)

fun FragmentActivity.showLongToast(message: String) =
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()

fun Context.getProgressDialog(title: String = "", message: String): ProgressDialog =
    ProgressDialog.show(this, title, message, true, false)

fun AlertDialog.Builder.buildConfirmationDialog(
    inflater: LayoutInflater,
    message: String,
    positiveButton: String = "Yes",
    negativeButton: String = "No",
    positiveButtonClick: (view: View) -> Unit = { },
    negativeButtonClick: (view: View) -> Unit = { }
): AlertDialog {
    val view = inflater.inflate(R.layout.layout_confirmation_dialog, null, false)
    view.txt_dialog_msg.text = message
    view.btn_positive.text = positiveButton
    view.btn_negative.text = negativeButton

    val dialog = setView(view).create()

    view.btn_positive.setOnClickListener {
        positiveButtonClick.invoke(it)
        dialog.dismiss()
    }

    view.btn_negative.setOnClickListener {
        negativeButtonClick.invoke(it)
        dialog.dismiss()
    }

    return dialog
}