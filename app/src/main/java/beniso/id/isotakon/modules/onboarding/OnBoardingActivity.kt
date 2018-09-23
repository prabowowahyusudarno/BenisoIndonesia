package beniso.id.isotakon.modules.onboarding

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import beniso.id.isotakon.R
import br.com.edsilfer.android.user_onboarding.presenter.ActivityUserOnBoarding

class OnBoardingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)

        val intent = Intent(this, ActivityUserOnBoarding::class.java)
        intent.putExtra(ActivityUserOnBoarding.ARG_ONBOARDING_THEME, OnBoardingConfig.getConfiguration())
        startActivity(intent)
    }
}


