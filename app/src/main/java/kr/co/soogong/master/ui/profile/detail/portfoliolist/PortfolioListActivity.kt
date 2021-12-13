package kr.co.soogong.master.ui.profile.detail.portfoliolist

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.data.common.ButtonTheme
import kr.co.soogong.master.databinding.ActivityEditPortfolioContainerBinding
import kr.co.soogong.master.ui.base.BaseActivity
import kr.co.soogong.master.ui.dialog.popup.CustomDialog
import kr.co.soogong.master.ui.dialog.popup.DialogData
import kr.co.soogong.master.ui.profile.detail.portfoliolist.PortfolioListViewModel.Companion.REQUEST_FAILED
import kr.co.soogong.master.uihelper.profile.EditProfileContainerActivityHelper
import kr.co.soogong.master.uihelper.profile.EditProfileContainerFragmentHelper.ADD_PORTFOLIO
import kr.co.soogong.master.uihelper.profile.EditProfileContainerFragmentHelper.ADD_PRICE_BY_PROJECTS
import kr.co.soogong.master.uihelper.profile.EditProfileContainerFragmentHelper.EDIT_PORTFOLIO
import kr.co.soogong.master.uihelper.profile.EditProfileContainerFragmentHelper.EDIT_PRICE_BY_PROJECTS
import kr.co.soogong.master.uihelper.profile.PortfolioListActivityHelper.PORTFOLIO
import kr.co.soogong.master.uihelper.profile.PortfolioListActivityHelper.PRICE_BY_PROJECTS
import kr.co.soogong.master.utility.EventObserver
import kr.co.soogong.master.utility.extension.toast
import timber.log.Timber

@AndroidEntryPoint
class PortfolioListActivity : BaseActivity<ActivityEditPortfolioContainerBinding>(
    R.layout.activity_edit_portfolio_container
) {
    private val viewModel: PortfolioListViewModel by viewModels()

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
            lifecycleOwner = this@PortfolioListActivity

            with(abHeader) {
                setButtonBackClickListener {
                    onBackPressed()
                }
            }

            setBasicLayout()
            setButtonClickListener()
            setRecyclerview()
        }
    }

    private fun setBasicLayout() {
        with(binding) {
            when (viewModel.pageName) {
                PORTFOLIO -> {
                    abHeader.title = PORTFOLIO
                    with(sbbAddingItem) {
                        subheadline = getString(R.string.write_portfolio)
                        hint = getString(R.string.write_portfolio_hint)
                        buttonText = getString(R.string.add_portfolio)
                    }
                    tvAddedItems.text = getString(R.string.added_portfolio)
                }
                PRICE_BY_PROJECTS -> {
                    abHeader.title = PRICE_BY_PROJECTS
                    with(sbbAddingItem) {
                        subheadline = getString(R.string.write_price_by_project)
                        hint = getString(R.string.write_price_by_project_hint)
                        buttonText = getString(R.string.add_price_by_project)
                    }
                    tvAddedItems.text = getString(R.string.added_price_by_project)
                }
            }
        }
    }

    private fun setButtonClickListener() {
        binding.buttonThemeAddingItem = ButtonTheme.OutlinedPrimary
        binding.addingItemClickListener = View.OnClickListener {
            startActivity(
                EditProfileContainerActivityHelper.getIntent(
                    this@PortfolioListActivity,
                    if (viewModel.pageName == PORTFOLIO) ADD_PORTFOLIO else ADD_PRICE_BY_PROJECTS
                )
            )
        }
    }

    private fun setRecyclerview() {
        binding.rvItems.adapter =
            PortfolioListAdapter(
                buttonLeftClickListener = { id ->
                    CustomDialog.newInstance(
                        dialogData = when (viewModel.pageName) {
                            PORTFOLIO -> DialogData.getAskingDeletePortfolioDialogData(this@PortfolioListActivity)
                            else -> DialogData.getAskingDeletePriceByProjectDialogData(this@PortfolioListActivity)
                        }).let {
                        it.setButtonsClickListener(
                            onPositive = { viewModel.deletePortfolio(id) },
                            onNegative = { }
                        )
                        it.show(supportFragmentManager, it.tag)
                    }
                },
                buttonRightClickListener = { id ->
                    startActivity(
                        EditProfileContainerActivityHelper.getIntent(
                            this@PortfolioListActivity,
                            if (viewModel.pageName == PORTFOLIO) EDIT_PORTFOLIO else EDIT_PRICE_BY_PROJECTS,
                            id
                        )
                    )
                })
    }

    private fun registerEventObserve() {
        viewModel.action.observe(this, EventObserver { event ->
            when (event) {
                REQUEST_FAILED -> toast(getString(R.string.error_message_of_request_failed))
            }
        })
    }

    companion object {
        private const val TAG = "EditProfileWithCardActivity"

    }
}