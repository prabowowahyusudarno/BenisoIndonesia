package beniso.id.isotakon.modules.mentor

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import beniso.id.isotakon.R
import beniso.id.isotakon.models.IsoJawabQuestionModel
import beniso.id.isotakon.modules.BaseFragment
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_mentor_isojawab.*

class MentorIsoJawabFragment : BaseFragment(R.layout.fragment_mentor_isojawab) {
    lateinit var mDatabase: DatabaseReference
    var questionModelList: MutableList<IsoJawabQuestionModel> = mutableListOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mDatabase = FirebaseDatabase.getInstance().reference
        cariPertanyaan(view)
    }
    fun cariPertanyaan(view: View){
        val listener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val children = dataSnapshot!!.children
                children.forEach {
                    println(it.toString())
                    questionModelList.add(IsoJawabQuestionModel(it.child("questioner").value.toString(),it.child("question").value.toString(),"null","null","null",it.key))
                }
                setAdapter(view)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("loadPost:onCancelled ${databaseError.toException()}")
            }
        }
        mDatabase.child("question").addListenerForSingleValueEvent(listener)

    }

    fun setAdapter(view: View){
        item_list_mentor.layoutManager = LinearLayoutManager(view.context)
        item_list_mentor.adapter = MentorAdapter(view.context, questionModelList.asReversed()){
        }
    }

}
