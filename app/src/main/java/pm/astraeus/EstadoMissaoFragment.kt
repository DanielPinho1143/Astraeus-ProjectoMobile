package pm.astraeus

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import pm.astraeus.adapter.EstadosAdapter
import pm.astraeus.model.Estado

class EstadoMissaoFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: EstadosAdapter
    private val estadosList = ArrayList<Estado>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.tab_estado_missao, container, false)

        // Inicia RecyclerView
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = EstadosAdapter(estadosList)
        recyclerView.adapter = adapter

        // fetch
        fetchEstados()

        return view
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun fetchEstados() {
        val url = "https://esan-tesp-ds-paw.web.ua.pt/tesp-ds-g23/projecto/web/api.php/estado"

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
                    val estados = parseEstados(response.toString())

                    // atualização de dados
                    estadosList.clear()
                    estadosList.addAll(estados)
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

    private fun parseEstados(jsonResponse: String): List<Estado> {
        val estados = ArrayList<Estado>()

        val rootObject = JSONObject(jsonResponse)

        // extração de dados
        val recordsArray = rootObject.getJSONArray("records")

        for (i in 0 until recordsArray.length()) {
            val recordObject = recordsArray.getJSONObject(i)
            val estado = Estado().apply {
                this.estado = recordObject.getString("estado")
                this.descricao = recordObject.getString("descricao")
            }
            estados.add(estado)
        }

        return estados
    }
}