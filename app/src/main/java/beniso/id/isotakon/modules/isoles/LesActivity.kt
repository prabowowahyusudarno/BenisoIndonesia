package beniso.id.isotakon.modules.isoles

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.Button
import beniso.id.isotakon.R

class LesActivity : AppCompatActivity() {

    private val array_size_bt = intArrayOf(R.id.bt_60, R.id.bt_90, R.id.bt_120, R.id.bt_240)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_les)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)

        val mapel = intent.getStringExtra("mapel")
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.title = mapel

    }

    fun setSize(view: View) {
        val button = view as Button
        button.isEnabled = false
        button.setTextColor(Color.WHITE)
        for (id in array_size_bt) {
            if (view.getId() != id) {
                val unselectedButton = findViewById<View>(id) as Button
                unselectedButton.isEnabled = true
                unselectedButton.setTextColor(Color.BLACK)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
