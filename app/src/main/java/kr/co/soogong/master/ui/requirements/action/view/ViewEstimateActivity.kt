package kr.co.soogong.master.ui.requirements.action.view

import android.os.Bundle
import androidx.activity.viewModels
import kr.co.soogong.master.BR
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ActivityViewEstimateBinding
import kr.co.soogong.master.ext.addAdditionInfoView
import kr.co.soogong.master.ui.base.BaseActivity
import kr.co.soogong.master.ui.getRepository
import kr.co.soogong.master.uiinterface.requirments.action.view.ViewEstimateActivityHelper
import timber.log.Timber

class ViewEstimateActivity : BaseActivity<ActivityViewEstimateBinding>(
    R.layout.activity_view_estimate
) {

    private val estimationId: String by lazy {
        ViewEstimateActivityHelper.getEstimationId(intent)
    }

    private val viewModel: ViewEstimateViewModel by viewModels {
        ViewEstimateViewModelFactory(getRepository(this), estimationId)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag(TAG).d("onCreate: ")
        initLayout()
    }

    override fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")
        bind {
            setVariable(BR.vm, viewModel)
            lifecycleOwner = this@ViewEstimateActivity

            with(actionBar) {
                viewModel.id.observe(this@ViewEstimateActivity, { id ->
                    title.text = getString(R.string.view_estimate_title, id)
                })
                backButton.setOnClickListener {
                    super.onBackPressed()
                }
            }

            photoList.adapter = ViewEstimateImageAdapter()

            viewModel.additionInfo.observe(this@ViewEstimateActivity, { additionInfo ->
                additionInfo ?: return@observe
                customFrame.removeAllViews()
                additionInfo.forEach { item ->
                    addAdditionInfoView(
                        customFrame,
                        this@ViewEstimateActivity,
                        item.description,
                        item.value
                    )
                }
            })

            viewModel.transmissions.observe(this@ViewEstimateActivity, { transmissions ->
                transmissions?.message ?: return@observe

            })
        }
    }

    companion object {
        private const val TAG = "ViewEstimateActivity"
    }
}