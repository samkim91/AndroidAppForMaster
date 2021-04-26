package kr.co.soogong.master.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import kr.co.soogong.master.databinding.ViewProfileDefaultCardBinding
import kr.co.soogong.master.databinding.ViewProfileImageCardBinding
import kr.co.soogong.master.util.extension.setImageUrl

class ProfileImageCard @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
) : ConstraintLayout(context, attributeSet, defStyle) {

    private val binding =
        ViewProfileImageCardBinding.inflate(LayoutInflater.from(context), this, true)

    var title: String? = ""
        set(value) {
            field = value
            binding.title.text = value
        }

    var titleVisible: Boolean = true
        set(value) {
            field = value
            binding.title.visibility = if (value) VISIBLE else GONE
        }

    var newBadgeVisible: Boolean = false
        set(value) {
            field = value
            binding.newBadge.visibility = if (value) VISIBLE else GONE
        }

    var imageUrl: String = ""
        set(value) {
            field = value
            binding.image.setImageUrl(value)
        }

    var imageCount: Int = 0
        set(value) {
            field = value
            binding.imageCount.text = "+$value"
            binding.imageCountContainer.visibility = if (value > 2) VISIBLE else GONE
        }

    var subTitle: String? = ""
        set(value) {
            field = value
            binding.subTitle.text = value
        }

    var subTitleVisible: Boolean = false
        set(value) {
            field = value
            binding.subTitle.visibility = if (value) VISIBLE else GONE
        }

    var firstDetail: String? = ""
        set(value) {
            field = value
            binding.firstDetail.text = value
        }

    var firstDetailVisible: Boolean = false
        set(value) {
            field = value
            binding.firstDetail.visibility = if (value) VISIBLE else GONE
        }

    var secondDetail: String? = ""
        set(value) {
            field = value
            binding.secondDetail.text = value
        }

    var secondDetailVisible: Boolean = false
        set(value) {
            field = value
            binding.secondDetail.visibility = if (value) VISIBLE else GONE
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
            binding.defaultButton.visibility = if (value) VISIBLE else GONE
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
            binding.buttonGroup.visibility = if (value) VISIBLE else GONE
        }

    fun addDefaultButtonClickListener(listener: OnClickListener) {
        binding.defaultButton.setOnClickListener { listener }
    }

    fun addFirstButtonClickListener(listener: OnClickListener) {
        binding.firstButtonInGroup.setOnClickListener { listener }
    }

    fun addSecondButtonClickListener(listener: OnClickListener) {
        binding.secondButtonInGroup.setOnClickListener { listener }
    }

}