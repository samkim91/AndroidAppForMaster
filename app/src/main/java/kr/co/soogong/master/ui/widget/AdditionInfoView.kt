package kr.co.soogong.master.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ViewAdditionInfoBinding
import java.text.SimpleDateFormat
import java.util.*

class AdditionInfoView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintLayout(context, attributeSet, defStyle) {
    private var binding: ViewAdditionInfoBinding =
        ViewAdditionInfoBinding.inflate(LayoutInflater.from(context), this, true)

    var question: String? = ""
        set(value) {
            field = value
            binding.question.text = value
        }

    var answer: String? = ""
        set(value) {
            field = value
            if (value.isNullOrEmpty()) {
                binding.answer.setText(R.string.null_text)
            } else {
                binding.answer.text = value
            }
        }

    fun setDetailDate(date: Date?) {
        val simpleDateFormat = SimpleDateFormat("yyyy년 MM월 dd일 hh시 mm분")
        binding.answer.text = simpleDateFormat.format(date ?: System.currentTimeMillis())
    }
}