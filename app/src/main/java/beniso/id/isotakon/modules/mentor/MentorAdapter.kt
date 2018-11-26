package beniso.id.isotakon.modules.mentor

import android.app.AlertDialog
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import beniso.id.isotakon.R.layout.activity_mentor_item
import beniso.id.isotakon.models.IsoJawabQuestionModel
import com.gc.materialdesign.widgets.ProgressDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.activity_mentor_item.*

class MentorAdapter(private val context: Context, private val items: List<IsoJawabQuestionModel>, private val listener: (IsoJawabQuestionModel) -> Unit)
    : RecyclerView.Adapter<MentorAdapter.ViewHolder>() {

    lateinit var mDatabase: DatabaseReference
    var fbAuth = FirebaseAuth.getInstance().currentUser


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(LayoutInflater.from(context).inflate(activity_mentor_item, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(items[position], listener)
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
            LayoutContainer {
        val mProgressDialog = ProgressDialog(containerView.context, "Silakan Menunggu . . .")
        lateinit var mDatabase: DatabaseReference
        var fbAuth = FirebaseAuth.getInstance().currentUser

        fun bindItem(items: IsoJawabQuestionModel, listener: (IsoJawabQuestionModel) -> Unit) {

            var questionName = "Nama : " + items.questioner
            var questionText = "Pertanyaan : " + items.question
            var questionID = items.id.toString()
            tv_name.text = questionName
            tv_question.text = questionText
            containerView.setOnClickListener {
                mDatabase = FirebaseDatabase.getInstance().reference
                mProgressDialog.show()
                antrianPenjawab(questionID,questionText,0)
            }
        }

        fun antrianPenjawab(ts: String,questionText: String,counter: Int) {
            var answerer = ""
            val listener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    answerer = dataSnapshot.child("answerer").value.toString()
                    if (!answerer.equals("null")) {
                        mProgressDialog.dismiss()
                        if (answerer.equals(fbAuth!!.uid)){
                            menjawab(questionText)
                        }
                    } else if (counter == 0) {
                        var children = dataSnapshot.child("antrian").childrenCount + 1
                        mDatabase.child("question").child(ts).child("antrian").child(children.toString()).setValue(fbAuth!!.uid)
                        antrianPenjawab(ts,questionText,1)
                    } else antrianPenjawab(ts,questionText,1)

                }
                override fun onCancelled(databaseError: DatabaseError) {
                    println("loadPost:onCancelled ${databaseError.toException()}")
                }
            }
            mDatabase.child("question").child(ts).addListenerForSingleValueEvent(listener)
        }

        fun menjawab(question: String) {
            val alert = AlertDialog.Builder(containerView.context)
            val itemEditText = EditText(containerView.context)
            alert.setMessage("Jawab Pertanyaan")
            alert.setTitle(question)
            alert.setView(itemEditText)
            alert.setPositiveButton("Submit") { dialog, positiveButton -> }
            alert.show()


        }
    }


}