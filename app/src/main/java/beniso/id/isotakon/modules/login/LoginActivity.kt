package beniso.id.isotakon.modules.login

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import beniso.id.isotakon.MainActivity
import beniso.id.isotakon.R
import beniso.id.isotakon.modules.mentor.MentorActivity
import beniso.id.isotakon.modules.onboarding.OnBoardingConfig
import beniso.id.isotakon.modules.sign_up.SignUpActivity
import beniso.id.isotakon.utils.Helpers
import br.com.edsilfer.android.user_onboarding.presenter.ActivityUserOnBoarding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    var fbAuth = FirebaseAuth.getInstance()
    lateinit var mDatabase: DatabaseReference
    var role = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mDatabase = FirebaseDatabase.getInstance().reference

        val intent = Intent(this, ActivityUserOnBoarding::class.java)
        intent.putExtra(ActivityUserOnBoarding.ARG_ONBOARDING_THEME, OnBoardingConfig.getConfiguration())
        startActivity(intent)

        bt_masuk.setOnClickListener {
            view ->
            signIn(view,user_email.text.toString(), user_password.text.toString())
        }
        bt_daftar.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }


    }

    fun signIn(view: View, email: String, password: String) {
        Helpers.showDialogInfo(this, R.string.caption_alert, getString(R.string.caption_close), DialogInterface.OnDismissListener {

        }, "Wait ....")
        fbAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, OnCompleteListener<AuthResult> { task ->
            if (task.isSuccessful) {
               checkRole()
            } else {
                //showMessage(view,"Error: ${task.exception?.message}")
                Helpers.showDialogInfo(this, R.string.caption_alert, getString(R.string.caption_close), DialogInterface.OnDismissListener {

                }, "Error: ${task.exception?.message}")

            }
        })

    }

    private fun checkRole() {
        val userId = fbAuth!!.currentUser!!.uid
        val listener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                role = dataSnapshot.child("role").value.toString()
                loginRole()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("loadPost:onCancelled ${databaseError.toException()}")
            }
        }
        mDatabase.child("user").child(userId).addListenerForSingleValueEvent(listener)
    }

    fun  loginRole(){
        if (role.equals("user")) {
            var intent = Intent(this, MainActivity::class.java)
            intent.putExtra("id", fbAuth.currentUser?.email)
            startActivity(intent)
        } else {
            var intent = Intent(this, MentorActivity::class.java)
            intent.putExtra("id", fbAuth.currentUser?.email)
            startActivity(intent)
        }
    }

}
