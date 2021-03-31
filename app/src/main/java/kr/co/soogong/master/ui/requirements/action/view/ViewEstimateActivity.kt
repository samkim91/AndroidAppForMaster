package kr.co.soogong.master.ui.requirements.action.view

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.data.estimation.Estimation
import kr.co.soogong.master.databinding.ActivityViewEstimateBinding
import kr.co.soogong.master.domain.requirements.EstimationStatus
import kr.co.soogong.master.ui.base.BaseActivity
import kr.co.soogong.master.ui.dialog.CustomDialog
import kr.co.soogong.master.ui.dialog.DialogData.Companion.cancelDialogData
import kr.co.soogong.master.uiinterface.image.ImageViewActivityHelper
import kr.co.soogong.master.uiinterface.requirments.action.view.ViewEstimateActivityHelper
import kr.co.soogong.master.util.extension.addAdditionInfoView
import kr.co.soogong.master.util.extension.visible
import timber.log.Timber
import java.util.Observer
import javax.inject.Inject

@AndroidEntryPoint
class ViewEstimateActivity : BaseActivity<ActivityViewEstimateBinding>(
    R.layout.activity_view_estimate
) {
    private val estimationId: String by lazy {
        ViewEstimateActivityHelper.getEstimationId(intent)
    }

    private val viewModel: ViewEstimateViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag(TAG).d("onCreate: ")
        initLayout()
        registerEventObserve()
    }

    override fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")
        bind {
            vm = viewModel
            lifecycleOwner = this@ViewEstimateActivity

            with(actionBar) {
                backButton.setOnClickListener {
                    super.onBackPressed()
                }
            }

            photoList.adapter = ViewEstimateImageAdapter(
                cardClickClickListener = { position ->
                    startActivity(
                        ImageViewActivityHelper.getIntent(
                            this@ViewEstimateActivity,
                            estimationId,
                            position
                        )
                    )
                }
            )

            refuse.setOnClickListener {
                val dialog = CustomDialog(cancelDialogData(this@ViewEstimateActivity),
                    yesClick = {

                    },
                    noClick = {

                    }
                )
                dialog.show(supportFragmentManager, dialog.tag)
            }
        }
    }

    private fun registerEventObserve() {
        viewModel.estimation.observe(this@ViewEstimateActivity, { estimation ->
            binding.actionBar.title.text =
                getString(R.string.view_estimate_title, estimation?.keycode)

            // 고객 요청 내용
            val additionInfo = estimation?.additionalInfo
            if (!additionInfo.isNullOrEmpty()) {
                binding.customFrame.removeAllViews()
                additionInfo.forEach { item ->
                    addAdditionInfoView(
                        binding.customFrame,
                        this@ViewEstimateActivity,
                        item.description,
                        item.value
                    )
                }
            }

            // 나의 제안 내용
            val message = estimation?.transmissions?.message
            if (message != null) {

            }

            // 견적 요청 상태에서의 견적 마감일, 버튼들 보이
            if (EstimationStatus.getStatus(estimation?.status, estimation?.transmissions) == EstimationStatus.Request){
                binding.dueDateContainer.visibility = View.VISIBLE
                binding.refuse.visibility = View.VISIBLE
                binding.accept.visibility = View.VISIBLE
            }

            // 나의 최종 시공 내용

            // 고객 리뷰

        })
    }

    companion object {
        private const val TAG = "ViewEstimateActivity"
    }
}