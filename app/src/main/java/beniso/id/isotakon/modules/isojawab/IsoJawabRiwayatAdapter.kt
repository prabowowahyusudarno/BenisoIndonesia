package beniso.id.isotakon.modules.isojawab

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import beniso.id.isotakon.R.layout.item_iso_jawab
import beniso.id.isotakon.models.IsoJawabQuestionModel
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_iso_jawab.*

class IsoJawabRiwayatAdapter(private val context: Context, private val items: List<IsoJawabQuestionModel>, private val listener: (IsoJawabQuestionModel) -> Unit)
    : RecyclerView.Adapter<IsoJawabRiwayatAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(LayoutInflater.from(context).inflate(item_iso_jawab, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(items[position], listener)
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
            LayoutContainer {

        fun bindItem(items: IsoJawabQuestionModel, listener: (IsoJawabQuestionModel) -> Unit) {
            var status = ""
            if (items.answerer.equals("null")) status = "Belum Terjawab"
            else if (items.answer.equals("null")) status = "Sedang Dikerjakan"
            else status = "Terjawab"
            textview_riwayat_pertanyaan.text = items.question
            textview_riwayat_status.text = status
            textview_riwayat_mapel.text = items.description
            textview_riwayat_jawaban.text = items.answer

        }
    }


}