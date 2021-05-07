package com.example.omdb.util.extensions

import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.omdb.R
import com.example.omdb.databinding.LayoutConfirmationDialogBinding
import com.google.android.material.snackbar.Snackbar

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

fun Fragment.showShortToast(message: String?) = requireActivity().showShortToast(message)

fun FragmentActivity.showShortToast(message: String?) =
    Toast.makeText(this, message ?: "Something went wrong!", Toast.LENGTH_SHORT).show()

fun Fragment.showLongToast(message: String?) = requireActivity().showLongToast(message)

fun FragmentActivity.showLongToast(message: String?) =
    Toast.makeText(this, message ?: "Something went wrong!", Toast.LENGTH_LONG).show()

fun AlertDialog.Builder.buildConfirmationDialog(
    inflater: LayoutInflater,
    message: String,
    positiveButton: String = "Yes",
    negativeButton: String = "No",
    positiveButtonClick: (view: View) -> Unit = { },
    negativeButtonClick: (view: View) -> Unit = { }
): AlertDialog {
    val binding: LayoutConfirmationDialogBinding =
        DataBindingUtil.inflate(inflater, R.layout.layout_confirmation_dialog, null, false)
    binding.txtDialogMsg.text = message
    binding.btnPositive.text = positiveButton
    binding.btnNegative.text = negativeButton

    val dialog = setView(binding.root).create()

    binding.btnPositive.setOnClickListener {
        positiveButtonClick.invoke(it)
        dialog.dismiss()
    }

    binding.btnNegative.setOnClickListener {
        negativeButtonClick.invoke(it)
        dialog.dismiss()
    }

    return dialog
}