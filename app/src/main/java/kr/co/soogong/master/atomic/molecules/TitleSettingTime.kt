package kr.co.soogong.master.atomic.molecules

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import kr.co.soogong.master.databinding.ViewTitleSettingTimeBinding

class TitleSettingTime @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
) : ConstraintLayout(context, attributeSet, defStyle) {
    private val binding =
        ViewTitleSettingTimeBinding.inflate(LayoutInflater.from(context), this, true)

    var title: String? = ""
        set(value) {
            field = value
            binding.title.text = value
        }

    var titleVisible: Boolean = true
        set(value) {
            field = value
            binding.title.visibility = if (value) View.VISIBLE else View.GONE
        }

    var subTitle: String? = ""
        set(value) {
            field = value
            binding.subTitle.text = value
        }

    var subTitleVisible: Boolean = false
        set(value) {
            field = value
            binding.subTitle.visibility = if (value) View.VISIBLE else View.GONE
        }

    val starTime: TextView
        get() = binding.startTime

    val endTime: TextView
        get() = binding.endTime

    var setStartTime: String = ""
        set(value) {
            field = value
            binding.startTime.text = value
        }

    var setEndTime: String = ""
        set(value) {
            field = value
            binding.endTime.text = value
        }

    var setStartTimeHint: String? = ""
        set(value) {
            field = value
            binding.startTime.hint = value
        }

    var setEndTimeHint: String? = ""
        set(value) {
            field = value
            binding.endTime.hint = value
        }

    var alertText: String? = ""
        set(value) {
            field = value
            binding.alert.text = value
        }

    var alertVisible: Boolean = false
        set(value) {
            field = value
            binding.alert.visibility = if (value) View.VISIBLE else View.GONE
        }

    fun addStartTimeClickListener(
        onClick: () -> Unit,
    ) {
        binding.startTime.setOnClickListener { onClick() }
    }

    fun addEndTimeClickListener(
        onClick: () -> Unit,
    ) {
        binding.endTime.setOnClickListener { onClick() }
    }
}