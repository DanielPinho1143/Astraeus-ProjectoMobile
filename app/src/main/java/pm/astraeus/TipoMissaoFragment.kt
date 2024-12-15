package pm.astraeus

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import pm.astraeus.adapter.TipoMissaoAdapter
import pm.astraeus.model.TipoMissao

class TipoMissaoFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TipoMissaoAdapter
    private val tiposMissaoList = ArrayList<TipoMissao>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.tab_tipo_missao, container, false)

        // Inicia RecyclerView
        recyclerView = view.findViewById(R.id.recyclerViewTipoMissao)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = TipoMissaoAdapter(tiposMissaoList)
        recyclerView.adapter = adapter

        // fetch
        fetchTiposMissao()

        return view
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun fetchTiposMissao() {
        val url = "https://esan-tesp-ds-paw.web.ua.pt/tesp-ds-g23/projecto/web/api.php/tipo_missao"

        // Criação de envio JSON
        val jsonRequest = JSONObject().apply {
            put("method", "GET")
        }

        // Inicialização de pedido Volley
        val requestQueue = Volley.newRequestQueue(requireContext())

        // Criação de um JsonObjectRequest
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST,
            url,
            jsonRequest,
            { response ->
                try {
                    val tipos_missao = parseTiposMissao(response.toString())

                    // atualização de dados
                    tiposMissaoList.clear()
                    tiposMissaoList.addAll(tipos_missao)
                    adapter.notifyDataSetChanged()

                } catch (e: Exception) {
                    // exceções
                    Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                    Log.e("TipoMissaoFragment", "JSON Parsing error", e)
                }
            },
            { error ->
                // erros de rede
                Toast.makeText(requireContext(), "Network Error: ${error.message}", Toast.LENGTH_SHORT).show()
                Log.e("TipoMissaoFragment", "Volley error", error)
            }
        )

        // queue
        requestQueue.add(jsonObjectRequest)
    }

    private fun parseTiposMissao(jsonResponse: String): List<TipoMissao> {
        val tiposMissao = ArrayList<TipoMissao>()

        val rootObject = JSONObject(jsonResponse)

        // extração de dados
        val recordsArray = rootObject.getJSONArray("records")

        for (i in 0 until recordsArray.length()) {
            val recordObject = recordsArray.getJSONObject(i)
            val tipo_missao = TipoMissao().apply {
                this.tipo_missao = recordObject.getString("tipo_missao")
                this.descricao = recordObject.getString("descricao")
            }
            tiposMissao.add(tipo_missao)
        }

        return tiposMissao
    }
}