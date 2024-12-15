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
import pm.astraeus.adapter.TipoCorpoAdapter
import pm.astraeus.model.TipoCorpo

class TipoCorpoFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TipoCorpoAdapter
    private val tiposCorpoList = ArrayList<TipoCorpo>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.tab_tipo_corpo, container, false)

        // Inicia RecyclerView
        recyclerView = view.findViewById(R.id.recyclerViewTipoCorpo)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = TipoCorpoAdapter(tiposCorpoList)
        recyclerView.adapter = adapter

        // fetch
        fetchTiposCorpo()

        return view
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun fetchTiposCorpo() {
        val url = "https://esan-tesp-ds-paw.web.ua.pt/tesp-ds-g23/projecto/web/api.php/tipo_corpo"

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
                    val tipos_corpo = parseTiposCorpo(response.toString())

                    // atualização de dados
                    tiposCorpoList.clear()
                    tiposCorpoList.addAll(tipos_corpo)
                    adapter.notifyDataSetChanged()

                } catch (e: Exception) {
                    // exceções
                    Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                    Log.e("TipoCorpoFragment", "JSON Parsing error", e)
                }
            },
            { error ->
                // erros de rede
                Toast.makeText(requireContext(), "Network Error: ${error.message}", Toast.LENGTH_SHORT).show()
                Log.e("TipoCorpoFragment", "Volley error", error)
            }
        )

        // queue
        requestQueue.add(jsonObjectRequest)
    }

    private fun parseTiposCorpo(jsonResponse: String): List<TipoCorpo> {
        val tiposCorpo = ArrayList<TipoCorpo>()

        val rootObject = JSONObject(jsonResponse)

        // extração de dados
        val recordsArray = rootObject.getJSONArray("records")

        for (i in 0 until recordsArray.length()) {
            val recordObject = recordsArray.getJSONObject(i)
            val tipo_corpo = TipoCorpo().apply {
                this.tipo_corpo = recordObject.getString("tipo_corpo")
                this.descricao = recordObject.getString("descricao")
            }
            tiposCorpo.add(tipo_corpo)
        }

        return tiposCorpo
    }
}