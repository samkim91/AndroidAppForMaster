package kr.co.soogong.master.atomic.molecules

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import kr.co.soogong.master.databinding.ViewSettingsButtonBinding

class SettingsButton @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
) : ConstraintLayout(context, attributeSet, defStyle) {
    private val binding =
        ViewSettingsButtonBinding.inflate(LayoutInflater.from(context), this, true)

    var title: String? = ""
        set(value) {
            field = value
            binding.title.text = value
        }

    var badge: Boolean = false
        set(value) {
            field = value
            binding.badge.visibility = if (value) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
}