package kr.co.soogong.master.ui.sign.signup

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.data.category.Category
import kr.co.soogong.master.data.category.Project
import kr.co.soogong.master.data.user.SignUpInfo
import kr.co.soogong.master.databinding.ActivitySignUpBinding
import kr.co.soogong.master.ext.addTextView3
import kr.co.soogong.master.ui.base.BaseActivity
import kr.co.soogong.master.ui.sign.signup.SignUpViewModel.Companion.EMAIL_ERROR
import kr.co.soogong.master.ui.sign.signup.SignUpViewModel.Companion.PASSWORD_CONFIRMATION_ERROR
import kr.co.soogong.master.ui.sign.signup.SignUpViewModel.Companion.PASSWORD_ERROR
import kr.co.soogong.master.ui.sign.signup.SignUpViewModel.Companion.SIGNUP_SUCCESS
import kr.co.soogong.master.ui.sign.signup.SignUpViewModel.Companion.USER_NAME_ERROR
import kr.co.soogong.master.uiinterface.category.CategoryActivityHelper
import kr.co.soogong.master.uiinterface.sign.signin.SignInActivityHelper
import kr.co.soogong.master.uiinterface.sign.signup.AddressActivityHelper
import kr.co.soogong.master.util.EventObserver
import timber.log.Timber
import java.text.SimpleDateFormat

@AndroidEntryPoint
class SignUpActivity : BaseActivity<ActivitySignUpBinding>(
    R.layout.activity_sign_up
) {
    private val viewModel: SignUpViewModel by viewModels()

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

            setCategorySelectClick {
                startActivityForResult(
                    CategoryActivityHelper.getIntent(this@SignUpActivity),
                    CategoryActivityHelper.SEARCH_CATEGORY_ACTIVITY
                )
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
                        area = viewModel.area ?: "",
                        location = viewModel.location ?: "",
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
                    viewModel.area = data?.extras?.getString(AddressActivityHelper.AREA)
                    viewModel.location = data?.extras?.getString(AddressActivityHelper.LOCATION)
                    val address = data?.extras?.getString(AddressActivityHelper.ADDRESS)
                    binding.address.text = address
                    if (address.isNullOrEmpty()) {
                        binding.addressDetail.visibility = View.GONE
                    } else {
                        binding.addressDetail.visibility = View.VISIBLE
                    }
                }
            }
            CategoryActivityHelper.SEARCH_CATEGORY_ACTIVITY -> {
                if (resultCode == RESULT_OK) {
                    val category: Category? =
                        data?.extras?.getParcelable(CategoryActivityHelper.BUNDLE_CATEGORY)
                    val projectList =
                        data?.extras?.getParcelableArray(CategoryActivityHelper.BUNDLE_PROJECT_LIST)

                    binding.category.text = category?.name

                    binding.categoryGroup.removeAllViews()

                    for (item in projectList?.asList() ?: emptyList()) {
                        val item = item as? Project
                        addTextView3(
                            binding.categoryGroup,
                            this@SignUpActivity,
                            item?.name
                        )
                    }
                }
            }
        }
    }

    companion object {
        private const val TAG = "SignUpActivity"
    }
}