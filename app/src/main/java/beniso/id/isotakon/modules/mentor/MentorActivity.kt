package beniso.id.isotakon.modules.mentor

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.ListView
import android.widget.Toast
import beniso.id.isotakon.R
import beniso.id.isotakon.models.IsoJawabQuestion
import beniso.id.isotakon.modules.sign_up.SignUpActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_mentor.*

class MentorActivity : AppCompatActivity() {

    var fbAuth = FirebaseAuth.getInstance()
    lateinit var mDatabase: DatabaseReference
    var questionList: MutableList<IsoJawabQuestion> = mutableListOf()
    lateinit var adapter: MentorAdapter
    private var listViewItems: ListView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mentor)
        mDatabase = FirebaseDatabase.getInstance().reference
        cariPertanyaan()
    }

    fun cariPertanyaan(){
        val listener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val children = dataSnapshot!!.children
                children.forEach {
                    println(it.toString())
                    questionList.add(IsoJawabQuestion(it.child("questioner").value.toString(),it.child("question").value.toString(),"null","null","null",it.key))
                }
                setAdapter()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("loadPost:onCancelled ${databaseError.toException()}")
            }
        }
        mDatabase.child("question").addListenerForSingleValueEvent(listener)

    }

    fun setAdapter(){
        item_list.layoutManager = LinearLayoutManager(this)
        item_list.adapter = MentorAdapter(this, questionList){
        }
    }
}
