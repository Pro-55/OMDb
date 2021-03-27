package com.example.omdb.util.extensions

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.util.DisplayMetrics
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.webkit.MimeTypeMap
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import java.io.File

fun FragmentActivity.getDisplayMetrics(): DisplayMetrics {
    val displayMetrics = DisplayMetrics()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) display?.getRealMetrics(displayMetrics)
    else windowManager?.defaultDisplay?.getMetrics(displayMetrics)
    return displayMetrics
}

fun FragmentActivity.hideKeyboard() {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    imm?.hideSoftInputFromWindow(findViewById<View>(android.R.id.content)?.windowToken, 0)
}

fun FragmentActivity.showKeyboard(view: View) {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    imm?.showSoftInput(view, 0)
}

fun File.getMimeType(): String? {
    var type: String? = null
    val extension = MimeTypeMap.getFileExtensionFromUrl(absolutePath)
    extension?.let {
        type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(it)
    }
    return type
}

fun FragmentActivity.requiredPermissions(permissions: Array<String>): Array<String> {
    return permissions.filter { permission ->
        ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED
    }.toTypedArray()
}

@RequiresApi(Build.VERSION_CODES.M)
fun FragmentActivity.shouldShowRational(permissions: Array<String>): Boolean {
    return permissions.all { shouldShowRequestPermissionRationale(it) }
}

fun FragmentActivity.showRationalDialog(title: String) {
    AlertDialog.Builder(this)
        .setTitle(title)
        .setMessage("We need permissions to perform this action")
        .setPositiveButton("Ok") { _, _ ->
            showShortToast("Please enable The permission manually")

            val appSettingsIntent = Intent()
            appSettingsIntent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS

            val packageUri =
                Uri.fromParts("package", packageName, null)
            appSettingsIntent.data = packageUri
            startActivity(appSettingsIntent)
        }.show()
}

fun FragmentActivity.getActionDialog(view: View): Dialog {
    return Dialog(this).apply { setContentView(view) }
}