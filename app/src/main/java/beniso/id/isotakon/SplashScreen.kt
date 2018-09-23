package beniso.id.isotakon

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import beniso.id.isotakon.modules.login.LoginActivity

class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val myThread = object : Thread() {
            override fun run() {
                try {
                    Thread.sleep(3000)
                        val intent = Intent(applicationContext, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }

            }
        }
        myThread.start()
    }
}
