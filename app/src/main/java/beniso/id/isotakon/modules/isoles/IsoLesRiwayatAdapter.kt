package beniso.id.isotakon.modules.isoles

import android.app.AlertDialog
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import beniso.id.isotakon.R.layout.item_riwayat_iso_les
import beniso.id.isotakon.models.IsoLestTutorModel
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_riwayat_iso_les.*

class IsoLesRiwayatAdapter(private val context: Context, private val items: List<IsoLestTutorModel>, private val listener: (IsoLestTutorModel) -> Unit)
    : RecyclerView.Adapter<IsoLesRiwayatAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(LayoutInflater.from(context).inflate(item_riwayat_iso_les, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(items[position], listener)
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
            LayoutContainer {

        fun bindItem(items: IsoLestTutorModel, listener: (IsoLestTutorModel) -> Unit) {

            tv_mentor_riwayat_isoles.text = items.mentor
            tv_mapel_riwayat_isoles.text = items.mapel
            tv_tarif.text = "Rp. ${items.tarif}"
            tv_date_riwayat_isoles.text = items.mulai
            tv_time_riwayat_isoles.text = items.selesai
            tv_status.text = items.status
            containerView.setOnClickListener {

            }
        }
    }
}



