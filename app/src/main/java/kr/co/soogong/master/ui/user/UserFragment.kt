package kr.co.soogong.master.ui.user

import android.os.Bundle
import androidx.fragment.app.Fragment

class UserFragment : Fragment() {
    companion object {
        fun newInstance(): UserFragment {
            val args = Bundle()

            val fragment = UserFragment()
            fragment.arguments = args
            return fragment
        }
    }
}