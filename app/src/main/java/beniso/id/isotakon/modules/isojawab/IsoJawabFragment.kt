package beniso.id.isotakon.modules.isojawab

import android.content.ContentValues
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import beniso.id.isotakon.MainActivity

import beniso.id.isotakon.R
import beniso.id.isotakon.models.IsoJawabQuestion
import beniso.id.isotakon.modules.login.LoginActivity
import beniso.id.isotakon.modules.mentor.MentorAdapter
import beniso.id.isotakon.modules.sign_up.SignUpActivity
import beniso.id.isotakon.utils.Helpers
import com.gc.materialdesign.widgets.ProgressDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_mentor.*
import kotlinx.android.synthetic.main.fragment_iso_jawab.*
import kotlinx.android.synthetic.main.fragment_iso_jawab.view.*
import kotlinx.android.synthetic.main.rsc_util_loading_indeterminate_bar.*
import org.jetbrains.anko.support.v4.indeterminateProgressDialog

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [IsoJawabFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [IsoJawabFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class IsoJawabFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null
    lateinit var mDatabase: DatabaseReference
    var fbAuth = FirebaseAuth.getInstance().currentUser
    var questionList: MutableList<IsoJawabQuestion> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)


        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_iso_jawab, container, false)
        mDatabase = FirebaseDatabase.getInstance().reference
            view.bt_photo.setOnClickListener { launchCamera() }
        view.bt_ask.setOnClickListener {
            sendAsk(view)
        }
        riwayatPertanyaan(view)
        return view
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
     interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        @JvmStatic
        fun newInstance(): IsoJawabFragment = IsoJawabFragment()
    }

    private fun launchCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivity(intent)
    }

    fun sendAsk(view: View){
        Helpers.showToast(view.context,"Pertanyaan dikirimkan silahkan menunggu sejenak..",true)
        val tsLong = System.currentTimeMillis() / 1000
        val ts = tsLong.toString()
        mDatabase.child("question").child(ts).setValue(IsoJawabQuestion(fbAuth!!.uid,et_post.text.toString(),"null","null","null",ts))
        antrianPenjawab(ts,view)
    }

    fun antrianPenjawab(ts: String,view: View){
//        val mProgressDialog = ProgressDialog(view.context, "Memilih mentor . . .")
        fun waitMentorResponse () {

            val listener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.hasChild("antrian")) {
                        val myThread = object : Thread() {
                            override fun run() {
                                try {
//                                    mProgressDialog.show()
                                    Thread.sleep(10000)
                                    var random = randomPick(dataSnapshot.child("antrian").childrenCount.toInt())
                                    var answerer = dataSnapshot.child("antrian").child(random.toString()).value.toString()
                                    mDatabase.child("question").child(ts).child("answerer").setValue(answerer)
//                                    mProgressDialog.dismiss()
                                } catch (e: InterruptedException) {
                                    e.printStackTrace()
                                }

                            }
                        }
                        myThread.start()
                    } else waitMentorResponse()

                }

                override fun onCancelled(databaseError: DatabaseError) {
                    println("loadPost:onCancelled ${databaseError.toException()}")
                }
            }
            mDatabase.child("question").child(ts).addListenerForSingleValueEvent(listener)
        }
        waitMentorResponse()
    }

    fun randomPick (child: Int): Int? {
        return (1..child).shuffled().first()
    }

    fun riwayatPertanyaan(view: View){
        val userId = fbAuth!!.uid.toString()
        var questioner = ""
        val listener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val children = dataSnapshot!!.children
                children.forEach {
                    questioner = it.child("questioner").value.toString()
                    if (userId.equals(questioner)) questionList.add(IsoJawabQuestion(questioner,it.child("question").value.toString(),it.child("answerer").value.toString(),it.child("answer").value.toString(),it.child("description").value.toString(),it.key))
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
        recycler_riwayat_isojawab.layoutManager = LinearLayoutManager(view.context)
        recycler_riwayat_isojawab.adapter = IsoJawabRiwayatAdapter(view.context, questionList.asReversed()){
        }
    }

    }

