package kr.co.soogong.master.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import kr.co.soogong.master.databinding.ViewProfileMapCardBinding
import kr.co.soogong.master.utility.ButtonHelper

class ProfileMapCard @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
) : ConstraintLayout(context, attributeSet, defStyle) {

    private val binding =
        ViewProfileMapCardBinding.inflate(LayoutInflater.from(context), this, true)

    val mapFragment: FrameLayout
        get() = binding.mapView

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

    var subTitle: String? = ""
        set(value) {
            field = value
            with(binding.subTitle) {
                if (!value.isNullOrEmpty() && detail == 0) {
                    visibility = View.VISIBLE
                    text = value
                } else {
                    visibility = View.GONE
                }
            }
        }

    var detail: Int? = 0
        set(value) {
            field = value
            with(binding.detail) {
                if (value == 0 || value == null) {
                    isVisible = false
                } else {
                    text = "${value}km"
                    isVisible = true
                    newBadgeVisible = false
                }
            }
        }

    // 등록하기 <-> 수정하기 버튼 셋
    var defaultButtonByInt: Int? = 0
        set(value) {
            field = value
            if (value == 0) {
                ButtonHelper.setRegisteringButton(binding.defaultButton)
            } else {
                ButtonHelper.setModifyingButton(binding.defaultButton)
            }
        }

    // 등록하기 <-> 편집하기 버튼 셋
    var defaultButtonByIntV2: Int? = 0
        set(value) {
            field = value
            if (value == 0) {
                ButtonHelper.setRegisteringButton(binding.defaultButton)
            } else {
                ButtonHelper.setEditingButton(binding.defaultButton)
            }
        }

    // 등록하기 <-> 삭제하기, 수정하기 버튼 셋
    var defaultButtonByIntV3: Int? = 0
        set(value) {
            field = value
            if (value == 0) {
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