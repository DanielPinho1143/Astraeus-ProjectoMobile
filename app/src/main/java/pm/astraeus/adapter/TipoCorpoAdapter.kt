package pm.astraeus.adapter

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import pm.astraeus.R
import pm.astraeus.model.TipoCorpo

class TipoCorpoAdapter (val tipos_corpo : ArrayList<TipoCorpo>) :
    RecyclerView.Adapter<TipoCorpoAdapter.ViewHolder>() {

    class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        val card : CardView = itemView.findViewById(R.id.cardTipoCorpo)
        val txtTipoCorpo : TextView = itemView.findViewById(R.id.txtTipoCorpoTitulo)
        val txtDesc : TextView = itemView.findViewById(R.id.txtTipoCorpoDesc)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_tipo_corpo, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return tipos_corpo.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val record = tipos_corpo[position]
        holder.txtTipoCorpo.text = record.tipo_corpo
        holder.txtDesc.text = record.descricao
        holder.card.setOnClickListener {
            val context = holder.card.context
            Toast.makeText(context, "pos: " + position, Toast.LENGTH_SHORT).show()
        }
    }

}