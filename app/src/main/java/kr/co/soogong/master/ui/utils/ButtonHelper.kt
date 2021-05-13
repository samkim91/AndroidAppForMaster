package kr.co.soogong.master.ui.utils

import android.content.Context
import android.location.Geocoder
import androidx.appcompat.widget.AppCompatTextView
import kr.co.soogong.master.R
import timber.log.Timber

object ButtonHelper {
    const val TAG = "ButtonHelper"

    fun setRegisteringButton(textView: AppCompatTextView) {
        with(textView) {
            text = resources.getString(R.string.register)
            setTextColor(resources.getColor(kr.co.soogong.master.R.color.color_1FC472, null))
        }
    }

    fun setModifyingButton(textView: AppCompatTextView) {
        with(textView) {
            text = resources.getString(R.string.modify)
            setTextColor(resources.getColor(R.color.color_616161, null))
        }
    }

    fun setEditingButton(textView: AppCompatTextView) {
        with(textView) {
            text = resources.getString(R.string.edit)
            setTextColor(resources.getColor(R.color.color_616161, null))
        }
    }

    fun setDeletingButton(textView: AppCompatTextView) {
        with(textView) {
            text = resources.getString(R.string.delete)
            setTextColor(resources.getColor(R.color.color_616161, null))
        }
    }
}