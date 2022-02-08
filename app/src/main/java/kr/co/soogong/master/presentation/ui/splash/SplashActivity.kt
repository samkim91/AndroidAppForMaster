package kr.co.soogong.master.presentation.ui.splash

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.contract.HttpContract
import kr.co.soogong.master.data.entity.auth.VersionDto
import kr.co.soogong.master.presentation.ui.common.dialog.popup.DefaultDialog
import kr.co.soogong.master.presentation.ui.common.dialog.popup.DialogData
import kr.co.soogong.master.presentation.ui.splash.SplashViewModel.Companion.GET_MASTER_UID_RETURN_NULL
import kr.co.soogong.master.presentation.ui.splash.SplashViewModel.Companion.GET_MASTER_UID_SUCCESSFULLY
import kr.co.soogong.master.presentation.ui.splash.SplashViewModel.Companion.GET_VERSION_INFO_FAILED
import kr.co.soogong.master.presentation.ui.splash.SplashViewModel.Companion.GET_VERSION_SUCCESSFULLY
import kr.co.soogong.master.presentation.uihelper.auth.AuthContainerActivityHelper
import kr.co.soogong.master.presentation.uihelper.main.MainActivityHelper
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
                                .d("current/latest: $currentVersion/${versionDto.version}")

                            if (currentVersion != versionDto.version) {
                                DefaultDialog.newInstance(
                                    dialogData = if (versionDto.mandatoryYn)
                                        DialogData.getUpdatingAppMandatory()
                                    else
                                        DialogData.getUpdatingAppRecommended(),
                                    cancelable = false
                                ).let {
                                    it.setButtonsClickListener(
                                        onPositive = {
                                            startActivity(Intent(Intent.ACTION_VIEW).apply {
                                                data = Uri.parse(HttpContract.GOOGLE_STORE_URL)
                                                setPackage("com.android.vending")
                                            })
                                        },
                                        onNegative = {
                                            if (versionDto.mandatoryYn) exitProcess(0) else viewModel.checkSignIn()
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
            }
        })

        viewModel.action.observe(this, EventObserver { action ->
            when (action) {
                GET_MASTER_UID_SUCCESSFULLY -> startActivity(MainActivityHelper.getIntent(this))
                GET_MASTER_UID_RETURN_NULL -> startActivity(AuthContainerActivityHelper.getIntent(
                    this))
                GET_VERSION_INFO_FAILED -> toast(getString(R.string.get_version_info_failed))
            }
        })
    }

    override fun onStart() {
        super.onStart()
        viewModel.checkLatestVersion()
    }

    companion object {
        private const val TAG = "SplashActivity"
    }
}