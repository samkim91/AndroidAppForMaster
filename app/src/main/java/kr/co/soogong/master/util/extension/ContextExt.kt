@file:JvmName("ContextExt")

package kr.co.soogong.master.util.extension;

import android.content.Context
import android.widget.Toast

fun Context.toast(message: CharSequence) =
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()