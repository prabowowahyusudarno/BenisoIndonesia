package beniso.id.isotakon.modules.isoles

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.widget.ListView
import beniso.id.isotakon.R
import beniso.id.isotakon.models.IsoLestTutorModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_mentor.*

class IsoLesRiwayatActivity : AppCompatActivity() {

    var fbAuth = FirebaseAuth.getInstance()
    lateinit var mDatabase: DatabaseReference
    var riwayatModelList: MutableList<IsoLestTutorModel> = mutableListOf()
    lateinit var adapter: IsoLesRiwayatAdapter
    private var listViewItems: ListView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mentor)
        val toolbar = findViewById<Toolbar>(R.id.toolbarMentor)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.title = "Riwayat Tutor"
        mDatabase = FirebaseDatabase.getInstance().reference
        loadRiwayat()
    }

    fun loadRiwayat(){
        val listener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val children = dataSnapshot!!.children
                children.forEach {
                    riwayatModelList.add(IsoLestTutorModel(it.child("latitude").value.toString(),it.child("longitude").value.toString(),it.child("mapel").value.toString(),it.child("idMentor").value.toString(),it.child("mulai").value.toString(),it.child("selesai").value.toString(),it.child("siswa").value.toString(),it.child("tarif").value.toString(),it.child("mentor").value.toString()))
                }
                setAdapter()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("loadPost:onCancelled ${databaseError.toException()}")
            }
        }
        mDatabase.child("janjian_les").addListenerForSingleValueEvent(listener)

    }

    fun setAdapter(){
        item_list_mentor.layoutManager = LinearLayoutManager(this)
        item_list_mentor.adapter = IsoLesRiwayatAdapter(this, riwayatModelList.asReversed()){
        }
    }
}
