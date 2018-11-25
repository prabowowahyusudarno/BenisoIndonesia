package beniso.id.isotakon.modules.isojawab

import android.app.AlertDialog
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.EditText
import android.widget.TextView
import beniso.id.isotakon.R
import beniso.id.isotakon.R.layout.activity_mentor_item
import beniso.id.isotakon.R.layout.item_iso_jawab
import beniso.id.isotakon.models.IsoJawabQuestion
import com.gc.materialdesign.widgets.ProgressDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.activity_mentor_item.*
import kotlinx.android.synthetic.main.item_iso_jawab.*
import org.jetbrains.anko.support.v4.indeterminateProgressDialog
import kotlin.reflect.jvm.internal.impl.load.java.lazy.ContextKt

class IsoJawabRiwayatAdapter(private val context: Context, private val items: List<IsoJawabQuestion>, private val listener: (IsoJawabQuestion) -> Unit)
    : RecyclerView.Adapter<IsoJawabRiwayatAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(LayoutInflater.from(context).inflate(item_iso_jawab, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(items[position], listener)
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
            LayoutContainer {

        fun bindItem(items: IsoJawabQuestion, listener: (IsoJawabQuestion) -> Unit) {
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