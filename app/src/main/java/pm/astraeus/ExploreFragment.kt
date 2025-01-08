package pm.astraeus

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Html.fromHtml
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import pm.astraeus.adapter.ProgramaAdapter
import pm.astraeus.model.Programa

class ExploreFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProgramaAdapter
    private val programasList = ArrayList<Programa>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_explore, container, false)

        recyclerView = view.findViewById(R.id.recyclerViewMissao)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter = ProgramaAdapter(programasList) { programa ->
            openDetalhesPrograma(programa.id) // Pass id as Integer
        }
        recyclerView.adapter = adapter

        fetchProgramas()

        val btnOpenCorpos = view.findViewById<Button>(R.id.btnCorpos)
        btnOpenCorpos.setOnClickListener {
            openCorpos()
        }

        val btnOpenAgencias = view.findViewById<Button>(R.id.btnAgencias)
        btnOpenAgencias.setOnClickListener {
            openAgencias()
        }

        return view
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun fetchProgramas() {
        val url = "https://esan-tesp-ds-paw.web.ua.pt/tesp-ds-g23/projecto/web/api.php/programa"

        val jsonRequest = JSONObject().apply {
            put("method", "GET")
        }

        val requestQueue = Volley.newRequestQueue(requireContext())
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST,
            url,
            jsonRequest,
            { response ->
                try {
                    val programas = parseProgramas(fromHtml(response.toString()).toString())
                    programasList.clear()
                    programasList.addAll(programas)
                    adapter.notifyDataSetChanged()
                } catch (e: Exception) {
                    Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                    Log.e("ExploreFragment", "JSON Parsing error", e)
                }
            },
            { error ->
                Toast.makeText(requireContext(), "Network Error: ${error.message}", Toast.LENGTH_SHORT).show()
                Log.e("ExploreFragment", "Volley error", error)
            }
        )
        requestQueue.add(jsonObjectRequest)
    }

    private fun parseProgramas(jsonResponse: String): List<Programa> {
        val programas = ArrayList<Programa>()
        val rootObject = JSONObject(jsonResponse)
        val recordsArray = rootObject.getJSONArray("records")

        for (i in 0 until recordsArray.length()) {
            val recordObject = recordsArray.getJSONObject(i)
            val programa = Programa().apply {
                id = recordObject.getInt("id")
                nome = recordObject.getString("nome")
                descricao = recordObject.getString("descricao")
                nome_agencia = recordObject.getString("nome_agencia")
                nome_inteiro = recordObject.getString("nome_inteiro")
            }
            programas.add(programa)
        }
        return programas
    }

    private fun openDetalhesPrograma(id: Int) {
        val detalhesPrograma = DetalhesProgramaFragment()

        val args = Bundle()
        args.putInt("ID_PROGRAMA", id)  // Pass id as Integer
        detalhesPrograma.arguments = args

        parentFragmentManager.beginTransaction()
            .replace(R.id.placeholder, detalhesPrograma)
            .addToBackStack(null)
            .commit()
    }

    private fun openCorpos() {
        val corpos = CorpoFragment()

        parentFragmentManager.beginTransaction()
            .replace(R.id.placeholder, corpos)
            .addToBackStack(null)
            .commit()
    }

    private fun openAgencias() {
        val agencias = AgenciaFragment()

        parentFragmentManager.beginTransaction()
            .replace(R.id.placeholder, agencias)
            .addToBackStack(null)
            .commit()
    }

}