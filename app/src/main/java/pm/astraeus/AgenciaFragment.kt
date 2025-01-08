package pm.astraeus

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Html.fromHtml
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
import pm.astraeus.adapter.AgenciaAdapter
import pm.astraeus.adapter.EstadosAdapter
import pm.astraeus.model.Agencia
import pm.astraeus.model.Estado

class AgenciaFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AgenciaAdapter
    private val agenciasList = ArrayList<Agencia>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_agencia, container, false)

        // Inicia RecyclerView
        recyclerView = view.findViewById(R.id.recyclerViewAgencia)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = AgenciaAdapter(agenciasList)
        recyclerView.adapter = adapter

        // fetch
        fetchAgencias()

        return view
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun fetchAgencias() {
        val url = "https://esan-tesp-ds-paw.web.ua.pt/tesp-ds-g23/projecto/web/api.php/agencia"

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
                    val agencias = parseAgencias(fromHtml(response.toString()).toString())

                    // atualização de dados
                    agenciasList.clear()
                    agenciasList.addAll(agencias)
                    adapter.notifyDataSetChanged()

                } catch (e: Exception) {
                    // exceções
                    Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                    Log.e("EstadoMissaoFragment", "JSON Parsing error", e)
                }
            },
            { error ->
                // erros de rede
                Toast.makeText(requireContext(), "Network Error: ${error.message}", Toast.LENGTH_SHORT).show()
                Log.e("EstadoMissaoFragment", "Volley error", error)
            }
        )

        // queue
        requestQueue.add(jsonObjectRequest)
    }

    private fun parseAgencias(jsonResponse: String): List<Agencia> {
        val agencias = ArrayList<Agencia>()

        val rootObject = JSONObject(jsonResponse)

        // extração de dados
        val recordsArray = rootObject.getJSONArray("records")

        for (i in 0 until recordsArray.length()) {
            val recordObject = recordsArray.getJSONObject(i)
            val agencia = Agencia().apply {
                this.nome = recordObject.getString("nome")
                this.nome_inteiro = recordObject.getString("nome_inteiro")
                this.descricao = recordObject.getString("descricao")
                this.formacao = recordObject.getString("formacao")
                this.origem = recordObject.getString("origem")
                this.tipo = recordObject.getString("tipo")
                this.url_imagem = recordObject.getString("url_imagem")
            }
            agencias.add(agencia)
        }

        return agencias
    }
}