package kr.co.soogong.master.ui.requirements.received.detail

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import kr.co.soogong.master.BR
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ActivityDetailBinding
import kr.co.soogong.master.ui.base.BaseActivity
import kr.co.soogong.master.ui.getRepository
import kr.co.soogong.master.ui.requirements.received.estimate.EstimateActivity
import kr.co.soogong.master.uiinterface.requirments.received.detail.DetailActivityHelper
import kr.co.soogong.master.uiinterface.requirments.received.estimate.EstimateActivityHelper
import kr.co.soogong.master.util.EventObserver
import timber.log.Timber

class DetailActivity : BaseActivity<ActivityDetailBinding>(
    R.layout.activity_detail
) {
    private val requirementId by lazy {
        intent.getBundleExtra(DetailActivityHelper.EXTRA_KEY_BUNDLE)
            ?.getLong(DetailActivityHelper.BUNDLE_KEY_RECEIVED_KEY, -1) ?: -1
    }

    private val viewModel: DetailViewModel by viewModels {
        DetailViewModelFactory(getRepository(this), requirementId)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag(TAG).d("onCreate: ")
        initLayout()
    }

    private fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")
        bind {
            setVariable(BR.vm, viewModel)
            lifecycleOwner = this@DetailActivity
            actionBar.apply {
                title.text = "수리요청서"
                backButton.setOnClickListener {
                    super.onBackPressed()
                }
            }

            setAcceptClick {
                viewModel.onClickedAccept()
            }

            setDeinedClick {
                viewModel.onClickedDenied(viewModel.requirement?.value)
            }
        }

        registerEventObserve()
    }

    private fun registerEventObserve() {
        Timber.tag(TAG).d("registerEventObserve: ")
        viewModel.event.observe(this, EventObserver { event ->
            when (event) {
                DetailViewModel.DENIED_EVENT -> {
                    Toast.makeText(this, "거절한 수리 문의 삭제가 완료되었습니다.", Toast.LENGTH_LONG).show()
                    finish()
                }

                DetailViewModel.ACCEPT_EVENT -> {
                    this.run {
                        startActivity(Intent(this, EstimateActivity::class.java).apply {
                            putExtra(EstimateActivityHelper.EXTRA_KEY_BUNDLE, Bundle().apply {
                                putLong(
                                    EstimateActivityHelper.BUNDLE_KEY_RECEIVED_KEY,
                                    requirementId
                                )
                            })
                        })
                    }
                }
            }
        })
    }

    companion object {
        private const val TAG = "DetailActivity"
    }
}