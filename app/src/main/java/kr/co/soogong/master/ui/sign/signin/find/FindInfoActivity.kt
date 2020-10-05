package kr.co.soogong.master.ui.sign.signin.find

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ActivityFindInfoBinding
import kr.co.soogong.master.ui.base.BaseActivity
import kr.co.soogong.master.ui.sign.signin.find.FindInfoViewModel.Companion.FAIL_TO_CONTACT
import kr.co.soogong.master.ui.sign.signin.find.FindInfoViewModel.Companion.FAIL_TO_NAME
import kr.co.soogong.master.ui.sign.signin.find.FindInfoViewModel.Companion.SUCCESS_TO_FIND_INFO
import kr.co.soogong.master.util.EventObserver
import timber.log.Timber

class FindInfoActivity : BaseActivity<ActivityFindInfoBinding>(
    R.layout.activity_find_info
) {
    private val viewModel: FindInfoViewModel by lazy {
        ViewModelProvider(this).get(FindInfoViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag(TAG).d("onCreate: ")
        initLayout()
        registerEventObserve()
    }

    private fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")

        bind {
            lifecycleOwner = this@FindInfoActivity

            with(actionBar) {
                title.text = "아이디·비밀번호 찾기"
                backButton.setOnClickListener {
                    super.onBackPressed()
                }
            }

            setFindInfoClick {
                val name = inputName.text
                val contact = inputContact.text

                viewModel.findInfo(name, contact)
            }

            inputName.addTextChangedListener(afterTextChanged = {
                inputName.hintText = "업체명을 입력해 주시기 바랍니다."
                inputName.hintVisible = inputName.text.isNullOrEmpty()
            })

            inputContact.addTextChangedListener(afterTextChanged = {
                inputContact.hintText = "연락처를 입력해 주시기 바랍니다."
                inputContact.hintVisible = inputContact.text.isNullOrEmpty()
            })
        }
    }

    private fun registerEventObserve() {
        viewModel.event.observe(this, EventObserver { (event, message) ->
            when (event) {
                FAIL_TO_NAME -> {

                }
                FAIL_TO_CONTACT -> {

                }
                SUCCESS_TO_FIND_INFO -> {

                }
            }
        })
    }

    companion object {
        private const val TAG = "FindInfoActivity"
    }
}