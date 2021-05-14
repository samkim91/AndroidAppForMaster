package kr.co.soogong.master.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.widget.ViewUtils
import androidx.constraintlayout.widget.ConstraintLayout
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ViewProfileDefaultCardBinding
import kr.co.soogong.master.ui.utils.ButtonHelper

class ProfileDefaultCard @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
) : ConstraintLayout(context, attributeSet, defStyle) {

    private val binding =
        ViewProfileDefaultCardBinding.inflate(LayoutInflater.from(context), this, true)

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

    var newBadgeVisible: Boolean = true
        set(value) {
            field = value
            binding.newBadge.visibility = if (value) View.VISIBLE else View.GONE
        }

    var subTitle: String? = ""
        set(value) {
            field = value
            with(binding.subTitle) {
                if (!value.isNullOrEmpty() && firstDetail.isNullOrEmpty()) {
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
                if(value.isNullOrEmpty()){
                    visibility = View.GONE
                } else {
                    visibility = View.VISIBLE
                    binding.subTitle.visibility = View.GONE
                    newBadgeVisible = false
                    text = value
                }
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
    var defaultButtonByStringV2: String? = ""
        set(value) {
            field = value
            if (value.isNullOrEmpty()) {
                ButtonHelper.setRegisteringButton(binding.defaultButton)
            } else {
                ButtonHelper.setEditingButton(binding.defaultButton)
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