package kr.co.soogong.master.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import kr.co.soogong.master.databinding.ViewTitleText2Binding
import java.text.SimpleDateFormat
import java.util.*

class TitleText2 @JvmOverloads constructor(
    context: Context, attributeSet: AttributeSet? = null, defStyle: Int = 0
) : ConstraintLayout(context, attributeSet, defStyle) {
    private var binding: ViewTitleText2Binding =
        ViewTitleText2Binding.inflate(LayoutInflater.from(context), this, true)

    var title: String? = ""
        set(value) {
            field = value
            binding.title.text = value
        }

    var detail: String? = ""
        set(value) {
            field = value
            binding.detail.text = value
        }

    fun setDetailDate(date: Date?) {
        val simpleDateFormat = SimpleDateFormat("yyyy년 MM월 dd일 hh시 mm분")
        binding.detail.text = simpleDateFormat.format(date ?: System.currentTimeMillis())
    }
}