package kr.co.soogong.master.ui.profile.detail.portfoliolist

import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ActivityEditProfileWithCardBinding
import kr.co.soogong.master.ui.base.BaseActivity
import kr.co.soogong.master.ui.dialog.popup.CustomDialog
import kr.co.soogong.master.ui.dialog.popup.DialogData
import kr.co.soogong.master.ui.profile.detail.portfoliolist.PortfolioListViewModel.Companion.REQUEST_FAILED
import kr.co.soogong.master.uihelper.profile.EditProfileContainerActivityHelper
import kr.co.soogong.master.uihelper.profile.EditProfileContainerFragmentHelper.ADD_PORTFOLIO
import kr.co.soogong.master.uihelper.profile.EditProfileContainerFragmentHelper.ADD_PRICE_BY_PROJECTS
import kr.co.soogong.master.uihelper.profile.EditProfileContainerFragmentHelper.EDIT_PORTFOLIO
import kr.co.soogong.master.uihelper.profile.EditProfileContainerFragmentHelper.EDIT_PRICE_BY_PROJECTS
import kr.co.soogong.master.uihelper.profile.PortfolioListActivityHelper
import kr.co.soogong.master.uihelper.profile.PortfolioListActivityHelper.PORTFOLIO
import kr.co.soogong.master.uihelper.profile.PortfolioListActivityHelper.PRICE_BY_PROJECTS
import kr.co.soogong.master.utility.EventObserver
import kr.co.soogong.master.utility.extension.toast
import timber.log.Timber

@AndroidEntryPoint
class PortfolioListActivity : BaseActivity<ActivityEditProfileWithCardBinding>(
    R.layout.activity_edit_profile_with_card
) {
    private val pageName: String by lazy {
        PortfolioListActivityHelper.getPageName(intent)
    }

    private val viewModel: PortfolioListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag(TAG).d("onCreate: ")
        initLayout()
        registerEventObserve()
    }

    override fun onStart() {
        super.onStart()
        Timber.tag(TAG).d("onStart: ")
        viewModel.requestPortfolioList()
    }

    override fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")
        bind {
            vm = viewModel
            lifecycleOwner = this@PortfolioListActivity

            with(actionBar) {
                backButton.setOnClickListener {
                    super.onBackPressed()
                }
            }

            setLayout()
            setDefaultButton()
            setRecyclerview()
        }
    }

    private fun setLayout() {
        bind {
            when (pageName) {
                PORTFOLIO -> {
                    actionBar.title.text = PORTFOLIO
                    with(introductionCard) {
                        title =
                            getString(kr.co.soogong.master.R.string.introduction_card_title_for_portfolio)
                        subTitle =
                            getString(kr.co.soogong.master.R.string.introduction_card_subtitle_for_portfolio)
                        defaultButtonText =
                            getString(kr.co.soogong.master.R.string.introduction_card_button_text_for_portfolio)
                    }
                }
                PRICE_BY_PROJECTS -> {
                    actionBar.title.text = PRICE_BY_PROJECTS
                    with(introductionCard) {
                        title =
                            getString(kr.co.soogong.master.R.string.introduction_card_title_for_price_by_projects)
                        subTitle =
                            getString(kr.co.soogong.master.R.string.introduction_card_subtitle_for_price_by_projects)
                        defaultButtonText =
                            getString(kr.co.soogong.master.R.string.introduction_card_button_text_for_price_by_projects)
                    }
                }
            }
        }
    }

    private fun setDefaultButton() {
        binding.introductionCard.addDefaultButtonClickListener {
            Timber.tag(TAG).w("DefaultButtonClickListener: ")
            startActivity(
                EditProfileContainerActivityHelper.getIntent(
                    this@PortfolioListActivity,
                    if (pageName == PORTFOLIO) ADD_PORTFOLIO else ADD_PRICE_BY_PROJECTS
                )
            )
        }
    }

    private fun setRecyclerview() {
        binding.recyclerview.adapter =
            PortfolioListAdapter(
                leftButtonClickListener = { id ->
                    val dialog = CustomDialog.newInstance(
                        dialogData = when(pageName) {
                            PORTFOLIO -> DialogData.getAskingDeletePortfolioDialogData(this@PortfolioListActivity)
                            else -> DialogData.getAskingDeletePriceByProjectDialogData(this@PortfolioListActivity)
                        },
                        yesClick = {
                            viewModel.deletePortfolio(id)
                        },
                        noClick = { })

                    dialog.show(supportFragmentManager, dialog.tag)
                },
                rightButtonClickListener = { id ->
                    startActivity(
                        EditProfileContainerActivityHelper.getIntent(
                            this@PortfolioListActivity,
                            if (pageName == PORTFOLIO) EDIT_PORTFOLIO else EDIT_PRICE_BY_PROJECTS,
                            id
                        )
                    )
                })
    }

    private fun registerEventObserve() {
        viewModel.action.observe(this, EventObserver { event ->
            when(event) {
                REQUEST_FAILED -> toast(getString(R.string.error_message_of_request_failed))
            }
        })
    }

    companion object {
        private const val TAG = "EditProfileWithCardActivity"

    }
}