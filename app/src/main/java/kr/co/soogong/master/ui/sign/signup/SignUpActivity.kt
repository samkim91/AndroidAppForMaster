package kr.co.soogong.master.ui.sign.signup

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import kr.co.soogong.master.R
import kr.co.soogong.master.data.user.SignUpInfo
import kr.co.soogong.master.databinding.ActivitySignUpBinding
import kr.co.soogong.master.ext.createLabelToggle
import kr.co.soogong.master.ui.base.BaseActivity
import kr.co.soogong.master.ui.category.CategoryFragment
import kr.co.soogong.master.ui.sign.signup.SignUpViewModel.Companion.EMAIL_ERROR
import kr.co.soogong.master.ui.sign.signup.SignUpViewModel.Companion.PASSWORD_CONFIRMATION_ERROR
import kr.co.soogong.master.ui.sign.signup.SignUpViewModel.Companion.PASSWORD_ERROR
import kr.co.soogong.master.ui.sign.signup.SignUpViewModel.Companion.SIGNUP_SUCCESS
import kr.co.soogong.master.ui.sign.signup.SignUpViewModel.Companion.USER_NAME_ERROR
import kr.co.soogong.master.uiinterface.sign.signin.SignInActivityHelper
import kr.co.soogong.master.uiinterface.sign.signup.AddressActivityHelper
import kr.co.soogong.master.util.EventObserver
import timber.log.Timber
import java.text.SimpleDateFormat

class SignUpActivity : BaseActivity<ActivitySignUpBinding>(
    R.layout.activity_sign_up
) {
    private val viewModel: SignUpViewModel by lazy {
        ViewModelProvider(this).get(SignUpViewModel::class.java)
    }

    private var area: String? = null
    private var location: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag(TAG).d("onCreate: ")
        initLayout()
        registerEventObserve()
    }

    private fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")

        bind {
            vm = viewModel
            lifecycleOwner = this@SignUpActivity

            with(actionBar) {
                title.text = "가입신청"
                backButton.setOnClickListener {
                    super.onBackPressed()
                }
            }

            id.addTextChangedListener(afterTextChanged = {
                if (id.text.isNullOrEmpty()) {
                    id.hintVisible = true
                    id.hintText = "필수 입력 입니다"
                } else {
                    id.hintVisible = false
                }
            })
            password.addTextChangedListener(afterTextChanged = {
                if (password.text.isNullOrEmpty()) {
                    password.hintVisible = true
                    password.hintText = "필수 입력 입니다"
                } else {
                    password.hintVisible = false
                }
            })
            confirmPassword.addTextChangedListener(afterTextChanged = {
                if (confirmPassword.text.isNullOrEmpty()) {
                    confirmPassword.hintVisible = true
                    confirmPassword.hintText = "필수 입력 입니다"
                } else {
                    confirmPassword.hintVisible = false
                }
            })
            username.addTextChangedListener(afterTextChanged = {
                if (username.text.isNullOrEmpty()) {
                    username.hintVisible = true
                    username.hintText = "필수 입력 입니다"
                } else {
                    username.hintVisible = false
                }
            })

            viewModel.list.observe(this@SignUpActivity, { list ->
                if (!list.isNullOrEmpty()) {
                    category.removeAllViews()

                    for (item in list) {
                        val view = createLabelToggle(
                            this@SignUpActivity,
                            item,
                            checked = true,
                            clickable = false
                        )
                        category.addView(view)
                    }
                }
            })

            setCategorySelectClick {
                CategoryFragment.newInstance(
                    list = viewModel.list.value ?: emptyList(),
                    listener = {
                        viewModel.sendList(it)
                    }).show(supportFragmentManager, CategoryFragment.TAG)
            }

            setFindLocationClick {
                startActivityForResult(
                    AddressActivityHelper.getIntent(this@SignUpActivity),
                    AddressActivityHelper.SEARCH_ADDRESS_ACTIVITY
                )
            }

            setSignUpClick {
                viewModel.signUp(
                    SignUpInfo(
                        email = binding.id.text ?: "",
                        password = binding.password.text ?: "",
                        passwordConfirmation = binding.confirmPassword.text ?: "",
                        username = binding.username.text ?: "",
                        phoneNumber = binding.tel.text ?: "",
                        area = area ?: "",
                        location = location ?: "",
                        businessNumber = binding.number.text ?: "",
                        tel = binding.tel.text ?: "",
                        address = binding.address.text ?: "",
                        detailAddress = binding.addressDetail.text.toString(),
                        description = "",
                        openDate = SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis()),
                        status = "mobile",
                    )
                )
            }
        }
    }

    private fun registerEventObserve() {
        viewModel.event.observe(this, EventObserver { (event, data) ->
            when (event) {
                SIGNUP_SUCCESS -> {
                    startActivity(SignInActivityHelper.getIntent(this))
                    finish()
                }
                EMAIL_ERROR -> {
                    binding.id.hintText = data as? String
                    binding.id.hintVisible = true
                }
                PASSWORD_ERROR -> {
                    binding.password.hintText = data as? String
                    binding.password.hintVisible = true
                }
                PASSWORD_CONFIRMATION_ERROR -> {
                    binding.confirmPassword.hintText = data as? String
                    binding.confirmPassword.hintVisible = true
                }
                USER_NAME_ERROR -> {
                    binding.username.hintText = data as? String
                    binding.username.hintVisible = true
                }
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            AddressActivityHelper.SEARCH_ADDRESS_ACTIVITY -> {
                if (resultCode == RESULT_OK) {
                    area = data?.extras?.getString(AddressActivityHelper.AREA)
                    location = data?.extras?.getString(AddressActivityHelper.LOCATION)
                    val address = data?.extras?.getString(AddressActivityHelper.ADDRESS)
                    binding.address.text = address
                    if (address.isNullOrEmpty()) {
                        binding.addressDetail.visibility = View.GONE
                    } else {
                        binding.addressDetail.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    companion object {
        private const val TAG = "SignUpActivity"
    }
}