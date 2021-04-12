package kr.co.soogong.master.ui.requirements.action.view

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.data.estimation.Message
import kr.co.soogong.master.databinding.ActivityViewEstimateBinding
import kr.co.soogong.master.domain.requirements.EstimationStatus
import kr.co.soogong.master.ui.base.BaseActivity
import kr.co.soogong.master.ui.dialog.CustomDialog
import kr.co.soogong.master.ui.dialog.DialogData.Companion.cancelDialogData
import kr.co.soogong.master.ui.requirements.action.view.ViewEstimateViewModel.Companion.FAIL
import kr.co.soogong.master.ui.requirements.action.view.ViewEstimateViewModel.Companion.SUCCESS
import kr.co.soogong.master.uiinterface.image.ImageViewActivityHelper
import kr.co.soogong.master.uiinterface.requirments.action.cancel.CancelEstimateActivityHelper
import kr.co.soogong.master.uiinterface.requirments.action.end.EndEstimateActivityHelper
import kr.co.soogong.master.uiinterface.requirments.action.view.ViewEstimateActivityHelper
import kr.co.soogong.master.uiinterface.requirments.action.write.WriteEstimateActivityHelper
import kr.co.soogong.master.util.EventObserver
import kr.co.soogong.master.util.extension.addAdditionInfoView
import kr.co.soogong.master.util.extension.addTransmissionMessage
import kr.co.soogong.master.util.extension.toast
import timber.log.Timber
import java.util.*

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

            // 견적을 보낼래요 버튼
            acceptButton.setOnClickListener {
                startActivity(WriteEstimateActivityHelper.getIntent(this@ViewEstimateActivity, estimationId))
            }

            // 견적을 내기 어려워요 버튼
            refuseButton.setOnClickListener {
                val dialog = CustomDialog(cancelDialogData(this@ViewEstimateActivity),
                    yesClick = {
                        vm?.refuseToEstimate()
                    },
                    noClick = { }
                )

                dialog.show(supportFragmentManager, dialog.tag)
            }

            // 취소 됐음 버튼
            cancelButton.setOnClickListener {
                startActivity(CancelEstimateActivityHelper.getIntent(this@ViewEstimateActivity, estimationId))
            }

            // 시공 완료 버튼
            doneButton.setOnClickListener {
                startActivity(EndEstimateActivityHelper.getIntent(this@ViewEstimateActivity, estimationId))
            }

            // 리뷰 요청하기 버튼

        }
    }

    private fun registerEventObserve() {
        viewModel.estimation.observe(this@ViewEstimateActivity, { estimation ->
            binding.actionBar.title.text =
                getString(R.string.view_estimate_title, estimation?.keycode)

            val status = EstimationStatus.getStatus(estimation?.status, estimation?.transmissions)

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

            when (status) {
                // 상태 : 견적요청
                // view : 고객 요청 내용
                // footer : 견적 마감일, 견적을 보낼래요, 견적을 내기 어려워요
                EstimationStatus.Request -> {
                    binding.requestedButtonGroup.visibility = View.VISIBLE
                }

                // 상태 : 매칭대기
                // view : 고객 요청 내용, 나의 제안 내용
                // footer : none
                EstimationStatus.Waiting -> {
                    bindTransmissionData(estimation?.transmissions?.message)
                }

                // 상태 : 시공진행중
                // view : 고객 요청 내용(+고객에게 전화하기), 나의 제안 내용
                // footer : 취소 됐음, 시공 완료
                EstimationStatus.Progress -> {
                    // todo.. 고객에게 전화하기 버튼 추가
                    binding.cancelButton.visibility = View.VISIBLE
                    binding.doneButton.visibility = View.VISIBLE

                    bindTransmissionData(estimation?.transmissions?.message)
                }

                // 상태 : 고객완료요청
                // view : 고객 요청 내용, 나의 제안 내용
                // footer : 시공 완료
                EstimationStatus.CustomDone -> {
                    binding.doneButton.visibility = View.VISIBLE

                    bindTransmissionData(estimation?.transmissions?.message)
                }

                // 상태 : 시공완료
                // view : 나의 최종 시공 내용, 고객 요청 내용
                // footer : 리뷰 요청하기
                EstimationStatus.Done -> {
                    binding.requestReviewButton.visibility = View.VISIBLE
                    bindDoneData(estimation?.transmissions?.message)
                }

                // 상태 : 평가완료
                // view : 고객 리뷰, 나의 최종 시공 내용, 고객 요청 내용
                // footer : none
                EstimationStatus.Final -> {
                    //Todo.. 고객 리뷰 데이터를 서버에서 return 해줘야함.
                    binding.customerReviewGroup.visibility = View.VISIBLE
                    bindDoneData(estimation?.transmissions?.message)
                }

                // 상태 : 시공취소
                // view : 고객 시공 취소 사유, 고객 요청 내용, 나의 제안 내용
                // footer : none
                EstimationStatus.Cancel -> {
                    binding.doneGroup.visibility = View.VISIBLE
                    bindTransmissionData(estimation?.transmissions?.message)
                }
            }
        })

        viewModel.action.observe(this@ViewEstimateActivity, EventObserver { event ->
            when(event) {
                SUCCESS -> {
                    toast(getString(R.string.view_estimate_on_refuse_to_estimate_success))
                    onBackPressed()
                }

                FAIL -> {
                    toast(getString(R.string.error_message_of_request_failed))
                }
            }
        })
    }

    private fun bindTransmissionData(message: Message?) {
        if (message != null) {
            binding.transmissionGroup.visibility = View.VISIBLE
            binding.customFrameForTransmissionDetail.removeAllViews()
            addTransmissionMessage(
                binding.customFrameForTransmissionDetail,
                this@ViewEstimateActivity,
                message
            )
        }
    }

    private fun bindDoneData(message: Message?) {
        if (message != null) {
            binding.doneGroup.visibility = View.VISIBLE
            binding.customFrameForDoneDetail.removeAllViews()
            addTransmissionMessage(
                binding.customFrameForDoneDetail,
                this@ViewEstimateActivity,
                message
            )
        }
    }

    companion object {
        private const val TAG = "ViewEstimateActivity"
    }
}
