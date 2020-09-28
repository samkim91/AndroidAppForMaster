package kr.co.soogong.master.ui.settings.password

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PasswordViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PasswordViewModel() as T
    }

}