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
import pm.astraeus.adapter.CorpoAdapter
import pm.astraeus.model.Agencia
import pm.astraeus.model.Corpo

class CorpoFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CorpoAdapter
    private val corposList = ArrayList<Corpo>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_corpo, container, false)

        // Inicia RecyclerView
        recyclerView = view.findViewById(R.id.recyclerViewCorpo)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = CorpoAdapter(corposList)
        recyclerView.adapter = adapter

        // fetch
        fetchCorpos()

        return view
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun fetchCorpos() {
        val url = "https://esan-tesp-ds-paw.web.ua.pt/tesp-ds-g23/projecto/web/api.php/corpo"

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
                    val corpos = parseCorpos(fromHtml(response.toString()).toString())

                    // atualização de dados
                    corposList.clear()
                    corposList.addAll(corpos)
                    adapter.notifyDataSetChanged()

                } catch (e: Exception) {
                    // exceções
                    Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                    Log.e("CorpoFragment", "JSON Parsing error", e)
                }
            },
            { error ->
                // erros de rede
                Toast.makeText(requireContext(), "Network Error: ${error.message}", Toast.LENGTH_SHORT).show()
                Log.e("CorpoFragment", "Volley error", error)
            }
        )

        // queue
        requestQueue.add(jsonObjectRequest)
    }

    private fun parseCorpos(jsonResponse: String): List<Corpo> {
        val corpos = ArrayList<Corpo>()

        val rootObject = JSONObject(jsonResponse)

        // extração de dados
        val recordsArray = rootObject.getJSONArray("records")

        for (i in 0 until recordsArray.length()) {
            val recordObject = recordsArray.getJSONObject(i)
            val corpo = Corpo().apply {
                this.nome = recordObject.getString("nome")
                this.descricao = recordObject.getString("descricao")
                this.tipo_corpo = recordObject.getString("tipo_corpo")
                this.url_imagem = recordObject.getString("url_imagem")
            }
            corpos.add(corpo)
        }

        return corpos
    }
}