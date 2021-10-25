package kr.co.soogong.master.ui.splash

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.data.dto.auth.VersionDto
import kr.co.soogong.master.ui.dialog.popup.CustomDialog
import kr.co.soogong.master.ui.dialog.popup.DialogData
import kr.co.soogong.master.ui.splash.SplashViewModel.Companion.GET_MASTER_UID_FAILED
import kr.co.soogong.master.ui.splash.SplashViewModel.Companion.GET_MASTER_UID_SUCCESSFULLY
import kr.co.soogong.master.ui.splash.SplashViewModel.Companion.GET_VERSION_SUCCESSFULLY
import kr.co.soogong.master.uihelper.auth.SignMainActivityHelper
import kr.co.soogong.master.uihelper.main.MainActivityHelper
import kr.co.soogong.master.utility.EventObserver
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
                                    dialogData = if (versionDto.mandatoryYn) DialogData.getUpdatingAppMandatory(
                                        this)
                                    else DialogData.getUpdatingAppRecommended(this),
                                    yesClick = {
                                        startActivity(Intent(Intent.ACTION_VIEW).apply {
                                            data =
                                                Uri.parse("https://play.google.com/store/search?q=%EC%88%98%EA%B3%B5")
                                            setPackage("com.android.vending")
                                        })
                                    },
                                    noClick = {
                                        if (versionDto.mandatoryYn) {
                                            exitProcess(0)
                                        } else {
                                            viewModel.checkSignIn()
                                        }
                                    }
                                ).run {
                                    if (versionDto.mandatoryYn) isCancelable = false
                                    show(supportFragmentManager, tag)
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
                GET_MASTER_UID_FAILED -> startActivity(SignMainActivityHelper.getIntent(this))
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