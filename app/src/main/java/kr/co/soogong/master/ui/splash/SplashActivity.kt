package kr.co.soogong.master.ui.splash

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.data.dto.auth.VersionDto
import kr.co.soogong.master.ui.dialog.popup.CustomDialog
import kr.co.soogong.master.ui.dialog.popup.DialogData
import kr.co.soogong.master.ui.splash.SplashViewModel.Companion.GET_MASTER_DIRECT_REPAIR
import kr.co.soogong.master.ui.splash.SplashViewModel.Companion.GET_MASTER_UID_FAILED
import kr.co.soogong.master.ui.splash.SplashViewModel.Companion.GET_MASTER_UID_SUCCESSFULLY
import kr.co.soogong.master.ui.splash.SplashViewModel.Companion.GET_VERSION_SUCCESSFULLY
import kr.co.soogong.master.ui.splash.SplashViewModel.Companion.REQUEST_FAILED
import kr.co.soogong.master.ui.splash.SplashViewModel.Companion.UPDATE_DIRECT_REPAIR_SUCCESSFULLY
import kr.co.soogong.master.uihelper.auth.SignMainActivityHelper
import kr.co.soogong.master.uihelper.main.MainActivityHelper
import kr.co.soogong.master.utility.EventObserver
import kr.co.soogong.master.utility.extension.toast
import timber.log.Timber
import kotlin.system.exitProcess

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag(TAG).d("onCreate: ")
        registerEventObserve()
    }

    private fun registerEventObserve() {
        viewModel.event.observe(this, EventObserver { (event, value) ->
            when (event) {
                GET_VERSION_SUCCESSFULLY -> {
                    (value as VersionDto).let { versionDto ->
                        this.packageManager.getPackageInfo(this.packageName,
                            0).versionName.let { currentVersion ->
                            Timber.tag(TAG)
                                .d("Ver current/latest: $currentVersion/${versionDto.version}")

                            if (currentVersion != versionDto.version) {
                                CustomDialog.newInstance(
                                    dialogData = if (versionDto.mandatoryYn)
                                        DialogData.getUpdatingAppMandatory(this)
                                    else
                                        DialogData.getUpdatingAppRecommended(this),
                                    cancelable = false
                                ).let {
                                    it.setButtonsClickListener(
                                        onPositive = {
                                            startActivity(Intent(Intent.ACTION_VIEW).apply {
                                                data =
                                                    Uri.parse("https://play.google.com/store/search?q=%EC%88%98%EA%B3%B5")
                                                setPackage("com.android.vending")
                                            })
                                        },
                                        onNegative = {
                                            if (versionDto.mandatoryYn) {
                                                exitProcess(0)
                                            } else {
                                                viewModel.checkSignIn()
                                            }
                                        }
                                    )
                                    it.show(supportFragmentManager, it.tag)
                                }
                            } else {
                                viewModel.checkSignIn()
                            }
                        }
                    }
                }
                GET_MASTER_DIRECT_REPAIR -> {
                    (value as Boolean).let {
                        if (it) startActivity(MainActivityHelper.getIntent(this))
                        else {
                            CustomDialog.newInstance(
                                dialogData = DialogData.getConfirmingDirectRepairYn(this),
                                cancelable = false
                            ).let { dialog ->
                                dialog.setButtonsClickListener(
                                    onPositive = { viewModel.updateDirectRepairYn(true) },
                                    onNegative = { viewModel.updateDirectRepairYn(false) }
                                )
                                dialog.show(supportFragmentManager, dialog.tag)
                            }
                        }
                    }
                }
                UPDATE_DIRECT_REPAIR_SUCCESSFULLY -> {
                    (value as Boolean).let {
                        if (it) startActivity(MainActivityHelper.getIntent(this))
                        else exitProcess(0)
                    }
                }
            }
        })

        viewModel.action.observe(this, EventObserver { action ->
            when (action) {
                GET_MASTER_UID_SUCCESSFULLY -> viewModel.requestMasterSimpleInfo()
                GET_MASTER_UID_FAILED -> startActivity(SignMainActivityHelper.getIntent(this))
                REQUEST_FAILED -> toast(getString(R.string.error_message_of_request_failed))
            }
        })
    }

    override fun onStart() {
        super.onStart()
        Timber.tag(TAG).d("onStart: ")
        viewModel.checkLatestVersion()
    }

    companion object {
        private const val TAG = "SplashActivity"
    }
}