package pm.astraeus.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import pm.astraeus.R
import pm.astraeus.model.Agencia
import pm.astraeus.model.Corpo
import pm.astraeus.model.Estado

class CorpoAdapter(val corpos: ArrayList<Corpo>):
    RecyclerView.Adapter<CorpoAdapter.ViewHolder>() {

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val card: CardView = itemView.findViewById(R.id.cardCorpo)
        val txtCorpoNome: TextView = itemView.findViewById(R.id.txtCorpoNome)
        val txtCorpoDescricao: TextView = itemView.findViewById(R.id.txtCorpoDescricao)
        val txtCorpoTipo: TextView = itemView.findViewById(R.id.txtCorpoTipo)
        val imgCorpo: ImageView = itemView.findViewById(R.id.imgCorpo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_corpo, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return corpos.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val record = corpos[position]
        holder.txtCorpoNome.text = record.nome
        holder.txtCorpoDescricao.text = record.descricao
        holder.txtCorpoTipo.text = record.tipo_corpo
        Picasso.get()
            .load(record.url_imagem)
            .into(holder.imgCorpo)
        holder.card.setOnClickListener {
            val context = holder.card.context
            Toast.makeText(context, "pos: "+position, Toast.LENGTH_SHORT).show()
        }
    }

}