package kr.co.soogong.master.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import androidx.constraintlayout.widget.ConstraintLayout
import kr.co.soogong.master.databinding.ViewTitleEmailBinding

class TitleEmail @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintLayout(context, attributeSet, defStyle) {
    private val binding =
        ViewTitleEmailBinding.inflate(LayoutInflater.from(context), this, true)

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

    val firstDetailView: EditText
        get() = binding.firstDetail

    val secondDetailView: EditText
        get() = binding.secondDetail

    var firstDetail: String = ""
        set(value) {
            if(value.contains("@")){
                val id = value.split("@")
                field = id[0]
                binding.firstDetail.setText(id[0])
            } else {
                field = value
                binding.firstDetail.setText(value)
            }
        }

    var secondDetail: String = ""
        set(value) {
            if(value.contains("@")){
                val id = value.split("@")
                field = id[1]
                binding.secondDetail.setText(id[1])
            } else {
                field = value
                binding.secondDetail.setText(value)
            }
        }

    var firstDetailHint: String? = ""
        set(value) {
            field = value
            binding.firstDetail.hint = value
        }

    var secondDetailHint: String? = ""
        set(value) {
            field = value
            binding.secondDetail.hint = value
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

    fun addDropdownClickListener(
        listener: OnClickListener
    ) {
        binding.detailIcon.setOnClickListener(listener)
    }
}