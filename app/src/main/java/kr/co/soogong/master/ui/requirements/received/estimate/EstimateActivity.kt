package kr.co.soogong.master.ui.requirements.received.estimate

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.data.requirements.Estimate
import kr.co.soogong.master.databinding.ActivityEstimateBinding
import kr.co.soogong.master.ui.base.BaseActivity
import kr.co.soogong.master.ui.utils.NumberTextWatcher
import kr.co.soogong.master.uiinterface.main.MainActivityHelper
import kr.co.soogong.master.uiinterface.requirments.received.estimate.EstimateActivityHelper
import kr.co.soogong.master.util.EventObserver
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class EstimateActivity : BaseActivity<ActivityEstimateBinding>(
    R.layout.activity_estimate
) {
    private val keycode by lazy {
        intent.getBundleExtra(EstimateActivityHelper.EXTRA_KEY_BUNDLE)
            ?.getString(EstimateActivityHelper.BUNDLE_KEY_RECEIVED_KEY, "") ?: ""
    }

    @Inject
    lateinit var factory: EstimateViewModel.AssistedFactory

    private val viewModel: EstimateViewModel by viewModels {
        EstimateViewModel.provideFactory(factory, keycode)
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
            lifecycleOwner = this@EstimateActivity

            with(actionBar) {
                title.text = "견적서 작성하기"
                backButton.setOnClickListener {
                    super.onBackPressed()
                }
            }

            amount.addTextChangedListener(NumberTextWatcher(amount))

            setSendClick {
                val estimate = Estimate(
                    price = amount.text.toString(),
                    contents = request.text.toString(),
                    possibleDate = ""
                )
                viewModel.onClickedSend(estimate)
            }
        }
    }

    private fun registerEventObserve() {
        Timber.tag(TAG).d("registerEventObserve: ")
        viewModel.event.observe(this, EventObserver { event ->
            when (event) {
                EstimateViewModel.SEND_EVENT -> {
                    Toast.makeText(this, "견적서가 발송되었습니다.\n채택을 기다려 주세요.", Toast.LENGTH_LONG).show()
                    startActivity(MainActivityHelper.getIntent(this))
                    finish()
                }
            }
        })
    }

    companion object {
        private const val TAG = "EstimateActivity"
    }
}