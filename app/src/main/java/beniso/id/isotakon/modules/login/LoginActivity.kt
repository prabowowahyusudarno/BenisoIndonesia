package beniso.id.isotakon.modules.login

import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import beniso.id.isotakon.MainActivity
import beniso.id.isotakon.R
import beniso.id.isotakon.utils.Helpers
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    var fbAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    fun signIn(view: View, email: String, password: String){
        Helpers.showDialogInfo(this, R.string.caption_alert, getString(R.string.caption_close), DialogInterface.OnDismissListener {
            finish()
        }, "Wait ....")
        fbAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, OnCompleteListener<AuthResult> { task ->
            if(task.isSuccessful){
                var intent = Intent(this, MainActivity::class.java)
                intent.putExtra("id", fbAuth.currentUser?.email)
                startActivity(intent)

            }else{
                //showMessage(view,"Error: ${task.exception?.message}")
                Helpers.showDialogInfo(this, R.string.caption_alert, getString(R.string.caption_close), DialogInterface.OnDismissListener {
                    finish()
                }, "Error: ${task.exception?.message}")

            }
        })

    }

}
