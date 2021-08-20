package kr.co.soogong.master.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import kr.co.soogong.master.databinding.ViewRequirementDrawerContainerBinding
import kr.co.soogong.master.utility.extension.startHalfRotateAnimation

class RequirementDrawerContainer @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintLayout(context, attributeSet, defStyle) {
    private var binding =
        ViewRequirementDrawerContainerBinding.inflate(LayoutInflater.from(context), this, true)

    var label: String? = ""
        set(value) {
            field = value
            with(binding) {
                drawerLabel.text = value
                drawerLabel.setOnClickListener {
                    drawerIcon.startHalfRotateAnimation(!detailContainer.isVisible)
                    detailContainer.isVisible = !detailContainer.isVisible
                }
            }
        }

    fun setContainer() {
        // TODO: 2021/08/20 상태값에 따라 넣을 값 정의
    }
}