package kr.co.soogong.master.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kr.co.soogong.master.data.category.BusinessType
import kr.co.soogong.master.data.category.Project
import kr.co.soogong.master.databinding.ViewProfileChipGroupCardBinding

class ProfileChipGroupCard @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
) : ConstraintLayout(context, attributeSet, defStyle) {

    private val binding =
        ViewProfileChipGroupCardBinding.inflate(LayoutInflater.from(context), this, true)

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

    var detail: String? = ""
        set(value) {
            field = value
            binding.detail.text = value
        }

    var detailVisible: Boolean = false
        set(value) {
            field = value
            binding.detail.visibility = if (value) VISIBLE else GONE
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

    fun addChipGroup(items: List<Project>){
        val chipGroup = ChipGroup(context)
        val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        items.map { item ->
            val chip = Chip(context)
            chip.text = item.toString()

            chipGroup.addView(chip)
        }

        params.setMargins(0, 12, 0, 0)

        binding.chipGroupContainer.addView(chipGroup, params)
    }

    fun addChipGroupWithTitle(items : BusinessType){
        val title = AppCompatTextView(context)
        val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        title.text = items.category.toString()
        params.setMargins(0, 16, 0, 0)

        binding.chipGroupContainer.addView(title, params)

        addChipGroup(items.projects)
    }

    fun addDefaultButtonClickListener(listener: OnClickListener) {
        binding.defaultButton.setOnClickListener { listener }
    }
}