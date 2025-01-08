package pm.astraeus.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import pm.astraeus.R
import pm.astraeus.model.Missao
import pm.astraeus.model.MissaoHasCorpo

class MissaoAdapter(private val missaoList: List<Missao>) : RecyclerView.Adapter<MissaoAdapter.MissaoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MissaoViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_missao, parent, false)
        return MissaoViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MissaoViewHolder, position: Int) {
        val missao = missaoList[position]

        // Null-safe assignment for text fields
        holder.missaoNome.text = missao.nome ?: "No Name"
        holder.missaoTipo.text = missao.tipo_missao_nome ?: "No Type"
        holder.missaoEstado.text = missao.estado_nome ?: "No State"
        holder.missaoResultado.text = missao.resultado ?: "No Result"
        holder.missaoInicio.text = missao.inicio ?: "No Start Date"
        holder.missaoFim.text = missao.fim ?: "No End Date"
        holder.missaoDescricao.text = missao.descricao ?: "No Description"

        holder.corposRecyclerView.layoutManager = LinearLayoutManager(holder.corposRecyclerView.context, LinearLayoutManager.HORIZONTAL, false)

        // Null check for corpos list
        if (missao.corpos != null && missao.corpos.isNotEmpty()) {
            val corpoAdapter = MissaoHasCorpoAdapter(missao.corpos)
            holder.corposRecyclerView.adapter = corpoAdapter
        } else {
            // If corpos is null or empty, you can set the RecyclerView to be empty or hide it.
            holder.corposRecyclerView.visibility = View.GONE // Hide RecyclerView if no corpos
        }
    }

    override fun getItemCount(): Int {
        return missaoList.size
    }

    class MissaoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val missaoNome: TextView = itemView.findViewById(R.id.txtMissaoNome)
        val missaoTipo: TextView = itemView.findViewById(R.id.txtTipoMissaoNome)
        val missaoEstado: TextView = itemView.findViewById(R.id.txtEstadoNome)
        val missaoResultado: TextView = itemView.findViewById(R.id.txtMissaoResultado)
        val missaoInicio: TextView = itemView.findViewById(R.id.txtInicio)
        val missaoFim: TextView = itemView.findViewById(R.id.txtFim)
        val missaoDescricao: TextView = itemView.findViewById(R.id.txtMissaoDescricao)
        val corposRecyclerView: RecyclerView = itemView.findViewById(R.id.recyclerCorpo)
    }
}

class MissaoHasCorpoAdapter(private val corpoList: List<MissaoHasCorpo>) : RecyclerView.Adapter<MissaoHasCorpoAdapter.CorpoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CorpoViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_missao_has_corpo, parent, false)
        return CorpoViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CorpoViewHolder, position: Int) {
        val corpo = corpoList[position]

        // Safely set corpoNome, check if corpo.corpo_nome is null
        holder.corpoNome.text = corpo.corpo_nome ?: "No Corp Name" // If corpo_nome is null, use a default value

        // Safely handle the image URL
        if (!corpo.imagem.isNullOrEmpty()) {
            Picasso.get().load(corpo.imagem).into(holder.corpoImagem)
        } else {
            Picasso.get().load("https://esan-tesp-ds-paw.web.ua.pt/tesp-ds-g23/uploads/file_not_found.png").into(holder.corpoImagem)
        }
    }

    override fun getItemCount(): Int {
        return corpoList.size
    }

    class CorpoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val corpoNome: TextView = itemView.findViewById(R.id.txtCorpoNome)
        val corpoImagem: ImageView = itemView.findViewById(R.id.imgCorpo)
    }
}