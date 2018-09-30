package beniso.id.isotakon.modules.login

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import beniso.id.isotakon.MainActivity
import beniso.id.isotakon.R
import beniso.id.isotakon.modules.onboarding.OnBoardingConfig
import beniso.id.isotakon.modules.sign_up.SignUpActivity
import beniso.id.isotakon.utils.Helpers
import br.com.edsilfer.android.user_onboarding.presenter.ActivityUserOnBoarding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    var fbAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val intent = Intent(this, ActivityUserOnBoarding::class.java)
        intent.putExtra(ActivityUserOnBoarding.ARG_ONBOARDING_THEME, OnBoardingConfig.getConfiguration())
        startActivity(intent)

        bt_masuk.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
        bt_daftar.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }


    }

    fun signIn(view: View, email: String, password: String) {
        Helpers.showDialogInfo(this, R.string.caption_alert, getString(R.string.caption_close), DialogInterface.OnDismissListener {
            finish()
        }, "Wait ....")
        fbAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, OnCompleteListener<AuthResult> { task ->
            if (task.isSuccessful) {
                var intent = Intent(this, MainActivity::class.java)
                intent.putExtra("id", fbAuth.currentUser?.email)
                startActivity(intent)

            } else {
                //showMessage(view,"Error: ${task.exception?.message}")
                Helpers.showDialogInfo(this, R.string.caption_alert, getString(R.string.caption_close), DialogInterface.OnDismissListener {
                    finish()
                }, "Error: ${task.exception?.message}")

            }
        })

    }

}
