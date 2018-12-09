package beniso.id.isotakon.modules.mentor

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import beniso.id.isotakon.R.layout.item_mentor_isoles
import beniso.id.isotakon.models.IsoLestTutorModel
import com.gc.materialdesign.widgets.ProgressDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_mentor_isoles.*

class MentorIsoLesAdapter(private val context: Context, private val items: List<IsoLestTutorModel>, private val listener: (IsoLestTutorModel) -> Unit)
    : RecyclerView.Adapter<MentorIsoLesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(LayoutInflater.from(context).inflate(item_mentor_isoles, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(items[position], listener)
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
            LayoutContainer {
        val mProgressDialog = ProgressDialog(containerView.context, "Silakan Menunggu . . .")
        lateinit var mDatabase: DatabaseReference
        var fbAuth = FirebaseAuth.getInstance().currentUser
        fun bindItem(items: IsoLestTutorModel, listener: (IsoLestTutorModel) -> Unit) {

            tv_siswa_isoles.text = items.siswa
            tv_mapel_isoles.text = items.mapel
            tv_date_isoles.text = items.mulai
            tv_time_isoles.text = items.selesai
            if(!items.status.equals("TERKONFIRMASI")) {
                containerView.setOnClickListener {
                    mDatabase = FirebaseDatabase.getInstance().reference
                    mProgressDialog.show()
                    antrianPenjawab(items)
                }
            } else bt_confirm.text = "TERKONFIRMASI"
        }

        fun antrianPenjawab(items: IsoLestTutorModel) {
            mDatabase.child("janjian_les").child(items.id.toString()).child("status").setValue("TERKONFIRMASI")
            mProgressDialog.dismiss()
            bt_confirm.text = "TERKONFIRMASI"
        }

    }
}



