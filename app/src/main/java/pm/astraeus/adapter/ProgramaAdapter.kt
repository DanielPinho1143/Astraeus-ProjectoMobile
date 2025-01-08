package pm.astraeus.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import pm.astraeus.R
import pm.astraeus.model.Programa

class ProgramaAdapter(
    private val programas: ArrayList<Programa>,
    private val onItemClick: (Programa) -> Unit
) : RecyclerView.Adapter<ProgramaAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val card: CardView = itemView.findViewById(R.id.cardPrograma)
        val txtProgramaNome: TextView = itemView.findViewById(R.id.txtProgramaNome)
        val txtProgramaDesc: TextView = itemView.findViewById(R.id.txtProgramaDesc)
        val txtProgramaNomeAgencia: TextView = itemView.findViewById(R.id.txtProgramaNomeAgencia)
        val txtProgramaNomeInteiro: TextView = itemView.findViewById(R.id.txtProgramaNomeInteiro)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_programa, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = programas.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val record = programas[position]
        holder.txtProgramaNome.text = record.nome
        holder.txtProgramaDesc.text = record.descricao
        holder.txtProgramaNomeAgencia.text = record.nome_agencia
        holder.txtProgramaNomeInteiro.text = record.nome_inteiro

        holder.card.setOnClickListener {
            onItemClick(record)
        }
    }
}