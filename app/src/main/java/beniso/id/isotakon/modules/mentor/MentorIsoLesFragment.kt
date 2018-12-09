package beniso.id.isotakon.modules.mentor

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import beniso.id.isotakon.R
import beniso.id.isotakon.models.IsoLestTutorModel
import beniso.id.isotakon.modules.BaseFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_mentor_isoles.*

class MentorIsoLesFragment : BaseFragment(R.layout.fragment_mentor_isoles) {
    lateinit var mDatabase: DatabaseReference
    var isolesModelList: MutableList<IsoLestTutorModel> = mutableListOf()
    var fbAuth = FirebaseAuth.getInstance().currentUser

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mDatabase = FirebaseDatabase.getInstance().reference
        cariPertanyaan(view)
    }

    fun cariPertanyaan(view: View) {
        val listener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val children = dataSnapshot!!.children
                children.forEach {
                    println(it.toString())
                    if (fbAuth?.uid.toString().equals(it.child("idMentor").value.toString())) {
                        isolesModelList.add(IsoLestTutorModel(it.key.toString(),it.child("latitude").value.toString(), it.child("longitude").value.toString(), it.child("mapel").value.toString(), it.child("idMentor").value.toString(), it.child("mulai").value.toString(), it.child("selesai").value.toString(), it.child("siswa").value.toString(), it.child("tarif").value.toString(), it.child("mentor").value.toString(), it.child("status").value.toString()))
                    }
                    }
                setAdapter(view)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("loadPost:onCancelled ${databaseError.toException()}")
            }
        }
        mDatabase.child("janjian_les").addListenerForSingleValueEvent(listener)

    }

    fun setAdapter(view: View) {
        recycler_isoles_mentor.layoutManager = LinearLayoutManager(view.context)
        recycler_isoles_mentor.adapter = MentorIsoLesAdapter(view.context, isolesModelList.asReversed()) {
        }
    }

}
