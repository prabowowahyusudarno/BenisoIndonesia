package beniso.id.isotakon.modules.isoles

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.Button
import android.widget.Toast
import beniso.id.isotakon.R
import beniso.id.isotakon.utils.Helpers
import kotlinx.android.synthetic.main.activity_les.*
import java.text.SimpleDateFormat
import java.util.*

class LesActivity : AppCompatActivity() {

    private val array_size_bt = intArrayOf(R.id.bt_60, R.id.bt_90, R.id.bt_120, R.id.bt_240)
    var formate = SimpleDateFormat("dd MMM, YYYY", Locale.US)
    var timeFormat = SimpleDateFormat("hh:mm a", Locale.US)

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
        layout_date_time_picker.setOnClickListener {
            dateTimePicker()
        }
        bt_60.setOnClickListener {
            textview_isoles_totalharga.text = "Rp. 60.000"
            setSize(it)
        }
        bt_90.setOnClickListener {
            textview_isoles_totalharga.text = "Rp. 85.000"
            setSize(it)
        }
        bt_120.setOnClickListener {
            textview_isoles_totalharga.text = "Rp. 110.000"
            setSize(it)
        }
        bt_240.setOnClickListener {
            textview_isoles_totalharga.text = "Rp. 220.000"
            setSize(it)
        }
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

    fun dateTimePicker(){
        val now = Calendar.getInstance()
        val datePicker = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            val selectedDate = Calendar.getInstance()
            selectedDate.set(Calendar.YEAR,year)
            selectedDate.set(Calendar.MONTH,month)
            selectedDate.set(Calendar.DAY_OF_MONTH,dayOfMonth)
            val date = formate.format(selectedDate.time)
            textview_isoles_date.text = date
        },
                now.get(Calendar.YEAR),now.get(Calendar.MONTH),now.get(Calendar.DAY_OF_MONTH))

        val timePicker = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
            val selectedTime = Calendar.getInstance()
            selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
            selectedTime.set(Calendar.MINUTE, minute)
            textview_isoles_time.text = timeFormat.format(selectedTime.time)
        },
                now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), false)
        timePicker.show()
        datePicker.show()
    }

}
