package pm.astraeus.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import pm.astraeus.R
import pm.astraeus.model.TipoMissao

class TipoMissaoAdapter (val tipos_missao : ArrayList<TipoMissao>) :
    RecyclerView.Adapter<TipoMissaoAdapter.ViewHolder>() {

    class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        val card : CardView = itemView.findViewById(R.id.cardTipoMissao)
        val txtTipoMissao : TextView = itemView.findViewById(R.id.txtTipoMissaoTitulo)
        val txtDesc : TextView = itemView.findViewById(R.id.txtTipoMissaoDesc)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_tipo_missao, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return tipos_missao.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val record = tipos_missao[position]
        holder.txtTipoMissao.text = record.tipo_missao
        holder.txtDesc.text = record.descricao
        holder.card.setOnClickListener {
            val context = holder.card.context
            Toast.makeText(context, "pos: " + position, Toast.LENGTH_SHORT).show()
        }
    }

}