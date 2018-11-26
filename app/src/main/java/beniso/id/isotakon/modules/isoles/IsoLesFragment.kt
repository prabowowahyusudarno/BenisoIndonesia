package beniso.id.isotakon.modules.isoles

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.*
import android.widget.LinearLayout
import beniso.id.isotakon.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [IsoLesFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [IsoLesFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class IsoLesFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_iso_les, container, false)

        val matematika = view.findViewById<LinearLayout>(R.id.matematika)
        val kimia = view.findViewById<LinearLayout>(R.id.kimia)
        val fisika = view.findViewById<LinearLayout>(R.id.fisika)
        val bahasa = view.findViewById<LinearLayout>(R.id.bahasa)
        matematika.setOnClickListener {
            val intent = Intent(context, LesActivity::class.java)
            intent.putExtra("mapel", "Matematika")
            requireActivity().startActivity(intent)
        }
        kimia.setOnClickListener {
            val intent = Intent(context, LesActivity::class.java)
            intent.putExtra("mapel", "Kimia")
            requireActivity().startActivity(intent)
        }
        fisika.setOnClickListener {
            val intent = Intent(context, LesActivity::class.java)
            intent.putExtra("mapel", "Fisika")
            requireActivity().startActivity(intent)
        }
        bahasa.setOnClickListener {
            val intent = Intent(context, LesActivity::class.java)
            intent.putExtra("mapel", "Bahasa")
            requireActivity().startActivity(intent)
        }
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
        fun newInstance(): IsoLesFragment = IsoLesFragment()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.isoles_history, menu)

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
            (when (item.itemId) {
            R.id.isoles_history -> {
                val intent = Intent(context, IsoLesRiwayatActivity::class.java)
                requireActivity().startActivity(intent)
                return true
            }
            else ->
                return super.onOptionsItemSelected(item)

        })
    }
}
