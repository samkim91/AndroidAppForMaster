package kr.co.soogong.master.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kr.co.soogong.master.ui.main.MainActivity
import kr.co.soogong.master.ui.sign.SignMainActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Share 검사 후 페이지 이동 결정
        
        val intent: Intent = if (true) {
            Intent(this, MainActivity::class.java)
        } else {
            Intent(this, SignMainActivity::class.java)
        }

        startActivity(intent)

        finish()
    }
}