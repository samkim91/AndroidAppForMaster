package kr.co.soogong.master.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.widget.ViewUtils
import androidx.constraintlayout.widget.ConstraintLayout
import kr.co.soogong.master.databinding.ViewProfileDefaultCardBinding

class ProfileDefaultCard @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
) : ConstraintLayout(context, attributeSet, defStyle) {

    private val binding = ViewProfileDefaultCardBinding.inflate(LayoutInflater.from(context), this, true)

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

    var newBadgeVisible: Boolean = false
        set(value) {
            field = value
            binding.newBadge.visibility = if (value) View.VISIBLE else View.GONE
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

    var firstDetail: String? = ""
        set(value) {
            field = value
            binding.firstDetail.text = value
        }

    var firstDetailVisible: Boolean = false
        set(value) {
            field = value
            binding.firstDetail.visibility = if (value) View.VISIBLE else View.GONE
        }

    var secondDetail: String? = ""
        set(value) {
            field = value
            binding.secondDetail.text = value
        }

    var secondDetailVisible: Boolean = false
        set(value) {
            field = value
            binding.secondDetail.visibility = if (value) View.VISIBLE else View.GONE
        }

    var defaultButtonText: String? = ""
        set(value) {
            field = value
            binding.defaultButton.text = value
        }

    var defaultButtonColor: Int = 0
        set(value) {
            field = value
            binding.defaultButton.setTextColor(value)
        }

    var defaultButtonVisible: Boolean = true
        set(value) {
            field = value
            binding.defaultButton.visibility = if (value) View.VISIBLE else View.GONE
        }

    var firstButtonText: String? = ""
        set(value) {
            field = value
            binding.firstButtonInGroup.text = value
        }

    var secondButtonText: String? = ""
        set(value) {
            field = value
            binding.secondButtonInGroup.text = value
        }

    var buttonGroupVisible: Boolean = false
        set(value) {
            field = value
            binding.buttonGroup.visibility = if (value) View.VISIBLE else View.GONE
        }

    fun addDefaultButtonClickListener(listener: OnClickListener){
        binding.defaultButton.setOnClickListener { listener }
    }

    fun addFirstButtonClickListener(listener: OnClickListener){
        binding.firstButtonInGroup.setOnClickListener { listener }
    }

    fun addSecondButtonClickListener(listener: OnClickListener){
        binding.secondButtonInGroup.setOnClickListener { listener }
    }

}