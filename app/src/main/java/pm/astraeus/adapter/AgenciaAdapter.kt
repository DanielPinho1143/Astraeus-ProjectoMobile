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
import pm.astraeus.model.Estado

class AgenciaAdapter(val agencias: ArrayList<Agencia>):
    RecyclerView.Adapter<AgenciaAdapter.ViewHolder>() {

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val card: CardView = itemView.findViewById(R.id.cardAgencia)
        val txtAgenciaNome: TextView = itemView.findViewById(R.id.txtAgenciaNome)
        val txtAgenciaNomeInteiro: TextView = itemView.findViewById(R.id.txtAgenciaNomeInteiro)
        val txtAgenciaDescricao: TextView = itemView.findViewById(R.id.txtAgenciaDescricao)
        val txtAgenciaFormacao: TextView = itemView.findViewById(R.id.txtAgenciaFormacao)
        val txtAgenciaOrigem: TextView = itemView.findViewById(R.id.txtAgenciaOrigem)
        val txtAgenciaTipo: TextView = itemView.findViewById(R.id.txtAgenciaTipo)
        val imgAgencia: ImageView = itemView.findViewById(R.id.imgAgencia)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_agencia, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return agencias.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val record = agencias[position]
        holder.txtAgenciaNome.text = record.nome
        holder.txtAgenciaNomeInteiro.text = record.nome_inteiro
        holder.txtAgenciaDescricao.text = record.descricao
        holder.txtAgenciaFormacao.text = record.formacao
        holder.txtAgenciaOrigem.text = record.origem
        holder.txtAgenciaTipo.text = record.tipo
        Picasso.get()
            .load(record.url_imagem)
            .into(holder.imgAgencia)
        holder.card.setOnClickListener {
            val context = holder.card.context
            Toast.makeText(context, "pos: "+position, Toast.LENGTH_SHORT).show()
        }
    }

}