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
import beniso.id.isotakon.models.IsoJawabQuestionModel
import beniso.id.isotakon.models.IsoLestTutorModel
import beniso.id.isotakon.utils.Helpers
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_les.*
import kotlinx.android.synthetic.main.fragment_iso_jawab.*
import java.text.SimpleDateFormat
import java.util.*

class LesActivity : AppCompatActivity() {

    private val array_size_bt = intArrayOf(R.id.bt_60, R.id.bt_90, R.id.bt_120, R.id.bt_240)
    var formate = SimpleDateFormat("dd MMM, YYYY", Locale.US)
    var timeFormat = SimpleDateFormat("hh:mm a", Locale.US)
    var tarif = "0"
    var date = "null"
    var time = "null"
    var fbAuth = FirebaseAuth.getInstance().currentUser
    var mentorTerpilih = "null"

    lateinit var mDatabase: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_les)

        mDatabase = FirebaseDatabase.getInstance().reference

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val mapel = intent.getStringExtra("mapel")
        date = textview_isoles_date.text.toString()
        time = textview_isoles_time.text.toString()
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.title = mapel
        layout_date_time_picker.setOnClickListener {
            dateTimePicker()
        }
        bt_60.setOnClickListener {
            tarif = "60000"
            textview_isoles_totalharga.text = "Rp. 60.000"
            setSize(it)
        }
        bt_90.setOnClickListener {
            tarif = "85000"
            textview_isoles_totalharga.text = "Rp. 85.000"
            setSize(it)
        }
        bt_120.setOnClickListener {
            tarif = "110000"
            textview_isoles_totalharga.text = "Rp. 110.000"
            setSize(it)
        }
        bt_240.setOnClickListener {
            tarif = "220000"
            textview_isoles_totalharga.text = "Rp. 220.000"
            setSize(it)
        }
        bt_add_to_cart.setOnClickListener {
            if (tarif.equals("0")) Helpers.showToast(applicationContext, "Masukkan Durasi Les", true)
            else saveTutor(mapel)
        }
    }

    fun saveTutor(mapel: String) {
        val listener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val children = dataSnapshot!!.children
                val mentor = HashMap<Int, String>()
                var counter = 1
                children.forEach {
                    if (it.child("mapel").value.toString().equals(mapel) && it.child("status").value.toString().equals("1")) {
                        mentor.put(counter, it.key.toString())
                        counter++
                    }
                }
                try {
                    mentorTerpilih = mentor[randomPick(counter - 1)].toString()
                    val tsLong = System.currentTimeMillis() / 1000
                    val ts = tsLong.toString()
                    mDatabase.child("janjian_les").child(ts).setValue(IsoLestTutorModel(ts,"latitude", "longitude", mapel, mentorTerpilih, date.toString(), time.toString(), fbAuth?.uid.toString(), tarif, dataSnapshot.child(mentorTerpilih).child("nama").value.toString(),"MENUNGGU"))
                    Helpers.showToast(applicationContext, "Mentor didapatkan", true)
                } catch (e: Exception) {
                    Helpers.showToast(applicationContext, "Mentor tidak tersedia", true)
                }

            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("loadPost:onCancelled ${databaseError.toException()}")
            }
        }
        mDatabase.child("user").addListenerForSingleValueEvent(listener)

    }

    fun randomPick(child: Int): Int {
        return (1..child).shuffled().first()
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

    fun dateTimePicker() {
        val now = Calendar.getInstance()
        val datePicker = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            val selectedDate = Calendar.getInstance()
            selectedDate.set(Calendar.YEAR, year)
            selectedDate.set(Calendar.MONTH, month)
            selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            textview_isoles_date.text = formate.format(selectedDate.time)
            date = formate.format(selectedDate.time)

        },
                now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH))

        val timePicker = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
            val selectedTime = Calendar.getInstance()
            selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
            selectedTime.set(Calendar.MINUTE, minute)
            textview_isoles_time.text = timeFormat.format(selectedTime.time)
            time = timeFormat.format(selectedTime.time)
        },
                now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), false)
        timePicker.show()
        datePicker.show()
    }

}
