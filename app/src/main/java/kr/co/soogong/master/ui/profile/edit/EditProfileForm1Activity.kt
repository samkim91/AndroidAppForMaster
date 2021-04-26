package kr.co.soogong.master.ui.profile.edit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kr.co.soogong.master.R

class EditProfileForm1Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile_form1)
    }

    companion object{
        const val TAG = "EditProfileForm1Activity"
        const val PORTFOLIO = "PORTFOLIO"
        const val PRICE_BY_PROJECTS = "PRICE_BY_PROJECTS"
    }
}