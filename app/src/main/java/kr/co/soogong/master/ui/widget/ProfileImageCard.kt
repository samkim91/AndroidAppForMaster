package kr.co.soogong.master.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import kr.co.soogong.master.R
import kr.co.soogong.master.data.dto.AttachmentDto
import kr.co.soogong.master.databinding.ViewProfileImageCardBinding
import kr.co.soogong.master.utility.ButtonHelper
import kr.co.soogong.master.utility.extension.setImageUrl

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

    var newBadgeVisible: Boolean = true
        set(value) {
            field = value
            binding.newBadge.visibility = if (value) VISIBLE else GONE
        }

    var imageUrl: String? = null
        set(value) {
            field = value
            if (!value.isNullOrEmpty()) {
                newBadgeVisible = false
                binding.subTitle.visibility = View.GONE

                binding.imageContainer.isVisible = true
                binding.image.setImageUrl(value)
            }
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
            with(binding.subTitle) {
                if (!value.isNullOrEmpty() && !imageUrl.isNullOrEmpty()) {
                    visibility = View.VISIBLE
                    text = value
                } else {
                    visibility = View.GONE
                }
            }
        }

    var firstDetail: String? = ""
        set(value) {
            field = value
            with(binding.firstDetail) {
                visibility = if (value.isNullOrEmpty()) View.GONE else View.VISIBLE
                text = value
            }
        }

    var firstDetailWithSimpleText: String? = ""
        set(value) {
            field = value
            if (!value.isNullOrEmpty()) {
                binding.firstDetail.visibility = View.VISIBLE
                binding.firstDetail.text =
                    resources.getString(R.string.simple_text_for_registered_image)
            }
        }

    var secondDetail: String? = ""
        set(value) {
            field = value
            with(binding.secondDetail) {
                visibility = if (value.isNullOrEmpty()) View.GONE else View.VISIBLE
                text = value
            }
        }

    // 등록하기 <-> 수정하기 버튼 셋
    var defaultButtonByImage: String? =""
        set(value) {
            field = value
            if (value.isNullOrEmpty()) {
                ButtonHelper.setRegisteringButton(binding.defaultButton)
            } else {
                ButtonHelper.setModifyingButton(binding.defaultButton)
            }
        }

    // 등록하기 <-> 수정하기 버튼 셋
    var defaultButtonByString: String? = ""
        set(value) {
            field = value
            if (value.isNullOrEmpty()) {
                ButtonHelper.setRegisteringButton(binding.defaultButton)
            } else {
                ButtonHelper.setModifyingButton(binding.defaultButton)
            }
        }

    // 등록하기 <-> 편집하기 버튼 셋
    var defaultButtonByImageV2: String? = ""
        set(value) {
            field = value
            if (value.isNullOrEmpty()) {
                ButtonHelper.setRegisteringButton(binding.defaultButton)
            } else {
                ButtonHelper.setEditingButton(binding.defaultButton)
            }
        }

    // 등록하기 <-> 삭제하기, 수정하기 버튼 셋
    var defaultButtonByImageV3: String? = ""
        set(value) {
            field = value
            if (value.isNullOrEmpty()) {
                ButtonHelper.setRegisteringButton(binding.defaultButton)
            } else {
                binding.defaultButton.visibility = View.GONE
                binding.buttonGroup.visibility = View.VISIBLE
                ButtonHelper.setDeletingButton(binding.firstButtonInGroup)
                ButtonHelper.setModifyingButton(binding.secondButtonInGroup)
            }
        }

    fun addDefaultButtonClickListener(listener: OnClickListener) {
        binding.defaultButton.setOnClickListener(listener)
    }

    fun addFirstButtonClickListener(listener: OnClickListener) {
        binding.firstButtonInGroup.setOnClickListener(listener)
    }

    fun addSecondButtonClickListener(listener: OnClickListener) {
        binding.secondButtonInGroup.setOnClickListener(listener)
    }

}