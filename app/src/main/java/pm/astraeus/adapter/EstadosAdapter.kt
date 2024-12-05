package pm.astraeus.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import pm.astraeus.R
import pm.astraeus.model.Estado

class EstadosAdapter(val estados: ArrayList<Estado>):
    RecyclerView.Adapter<EstadosAdapter.ViewHolder>() {

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val cardText: CardView = itemView.findViewById(R.id.cardText)
        val txtEstado: TextView = itemView.findViewById(R.id.txtTitulo)
        val txtDescricao: TextView = itemView.findViewById(R.id.txtDescricao)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_estado, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return estados.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val record = estados[position]
        holder.txtEstado.text = record.estado
        holder.txtDescricao.text = record.descricao
        holder.cardText.setOnClickListener {
            val context = holder.cardText.context
            Toast.makeText(context, "pos: "+position, Toast.LENGTH_SHORT).show()
        }
    }

}