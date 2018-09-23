package beniso.id.isotakon.modules.sign_up

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import beniso.id.isotakon.MainActivity
import beniso.id.isotakon.R
import beniso.id.isotakon.modules.login.LoginActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_signup.*

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        bt_masuk_signup.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        bt_daftar_signup.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

    }
}
