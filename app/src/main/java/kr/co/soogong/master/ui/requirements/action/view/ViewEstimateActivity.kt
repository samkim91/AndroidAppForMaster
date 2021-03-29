package kr.co.soogong.master.ui.requirements.action.view

import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ActivityViewEstimateBinding
import kr.co.soogong.master.ui.base.BaseActivity
import kr.co.soogong.master.ui.dialog.CustomDialog
import kr.co.soogong.master.ui.dialog.DialogData.Companion.cancelDialogData
import kr.co.soogong.master.uiinterface.image.ImageViewActivityHelper
import kr.co.soogong.master.uiinterface.requirments.action.view.ViewEstimateActivityHelper
import kr.co.soogong.master.util.extension.addAdditionInfoView
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class ViewEstimateActivity : BaseActivity<ActivityViewEstimateBinding>(
    R.layout.activity_view_estimate
) {
    private val estimationId: String by lazy {
        ViewEstimateActivityHelper.getEstimationId(intent)
    }

    @Inject
    lateinit var factory: ViewEstimateViewModel.AssistedFactory

    private val viewModel: ViewEstimateViewModel by viewModels {
        ViewEstimateViewModel.provideFactory(factory, estimationId)
    }

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

            val message = estimation?.transmissions?.message
            if (message != null) {

            }
        })
    }

    companion object {
        private const val TAG = "ViewEstimateActivity"
    }
}