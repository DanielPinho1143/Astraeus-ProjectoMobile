package pm.astraeus

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Html.fromHtml
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import org.json.JSONObject
import pm.astraeus.adapter.MissaoAdapter
import pm.astraeus.interfaces.MissaoApi
import pm.astraeus.model.Missao
import pm.astraeus.model.MissaoRequestData
import pm.astraeus.model.MissaoResponse
import pm.astraeus.model.Programa
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetalhesProgramaFragment : Fragment() {

    private var idPrograma: Int? = null
    private lateinit var nome: TextView
    private lateinit var descricao: TextView
    private lateinit var agencia: TextView
    private lateinit var agenciaInteiro: TextView
    private lateinit var imagem: ImageView

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MissaoAdapter
    private val missaoList = ArrayList<Missao>()

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detalhes_programa, container, false)

        nome = view.findViewById(R.id.txtProgramaDetalhesNome)
        descricao = view.findViewById(R.id.txtProgramaDetalhesDescricao)
        agencia = view.findViewById(R.id.txtNomeAgencia)
        agenciaInteiro = view.findViewById(R.id.txtNomeAgenciaInteiro)
        imagem = view.findViewById(R.id.imgAgenciaPrograma)

        recyclerView = view.findViewById(R.id.recyclerViewMissao)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = MissaoAdapter(missaoList)
        recyclerView.adapter = adapter

        // Log to confirm RecyclerView is initialized
        Log.d("DetalhesFragment", "RecyclerView initialized.")

        arguments?.let {
            idPrograma = it.getInt("ID_PROGRAMA", -1)
            Log.d("DetalhesFragment", "ID_PROGRAMA: $idPrograma")
        }

        idPrograma?.let {
            if (it != -1) {
                fetchProgramaDetalhes(it)
                fetchMissaoData(it.toString())
            } else {
                Toast.makeText(requireContext(), "Invalid ID_PROGRAMA", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

    private fun fetchProgramaDetalhes(idPrograma: Int) {
        val url = "https://esan-tesp-ds-paw.web.ua.pt/tesp-ds-g23/projecto/web/api.php/programa/$idPrograma"
        Log.d("DetalhesFragment", "API URL: $url")

        val requestQueue = Volley.newRequestQueue(requireContext())

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            { response ->
                Log.d("DetalhesFragment", "API Response: $response")
                try {
                    val programa = parseProgramaDetalhes(response)
                    updateUI(programa)
                } catch (e: Exception) {
                    Log.e("DetalhesFragment", "JSON Parsing error", e)
                    Toast.makeText(requireContext(), "Error parsing JSON: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            },
            { error ->
                Log.e("DetalhesFragment", "Volley error", error)
                Toast.makeText(requireContext(), "Network Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        )

        requestQueue.add(jsonObjectRequest)
    }

    private fun parseProgramaDetalhes(response: JSONObject): Programa {
        val recordsArray = response.getJSONArray("records")
        if (recordsArray.length() > 0) {
            val recordObject = recordsArray.getJSONObject(0)

            val programa = Programa().apply {
                id = recordObject.optInt("id", 0)
                nome = recordObject.optString("nome", "N/A")
                descricao = recordObject.optString("descricao", "N/A")
                nome_agencia = recordObject.optString("nome_agencia", "N/A")
                nome_inteiro = recordObject.optString("nome_agencia_inteiro", "N/A")
                url_imagem = recordObject.optString("url_imagem", "")
            }

            programa.descricao = fromHtml(programa.descricao).toString()

            return programa
        } else {
            throw Exception("No records found in the response")
        }
    }

    private fun updateUI(programa: Programa) {
        Log.d("DetalhesFragment", "Updating UI with program details.")
        nome.text = programa.nome
        descricao.text = programa.descricao
        agencia.text = programa.nome_agencia
        agenciaInteiro.text = programa.nome_inteiro

        if (programa.url_imagem.isNotEmpty()) {
            Picasso.get()
                .load(programa.url_imagem)
                .into(imagem)
        } else {
            Log.e("DetalhesFragment", "Image URL is empty")
        }
    }

    private fun fetchMissaoData(programaId: String) {
        // Log the parameters you are going to send in the request
        Log.d("DetalhesFragment", "Sending API request with method: GET, programa_id: $programaId")

        // Create the API service
        val apiService = RetrofitClientAPI.createService(MissaoApi::class.java)

        // Create the request data to send in the body
        val requestData = MissaoRequestData()
        requestData.method = "GET"
        requestData.programa_id = programaId

        // Make the API request
        apiService.getMissaoData(requestData).enqueue(object : Callback<MissaoResponse> {
            override fun onResponse(call: Call<MissaoResponse>, response: Response<MissaoResponse>) {
                if (response.isSuccessful) {
                    // Log the response body to check what is being returned
                    Log.d("DetalhesFragment", "API Response: ${response.body()}")

                    val missaoResponse = response.body()
                    missaoResponse?.let {
                        // Clear the existing list and add new items
                        missaoList.clear()

                        if (it.records.isNotEmpty()) {
                            missaoList.addAll(it.records)
                        }

                        // Log the number of items fetched
                        Log.d("DetalhesFragment", "Mission data fetched: ${missaoList.size} items.")

                        // Check if the list has missions and update the RecyclerView
                        if (missaoList.isNotEmpty()) {
                            // Initialize the adapter if not already done
                            adapter = MissaoAdapter(missaoList)
                            recyclerView.adapter = adapter
                            Log.d("DetalhesFragment", "Adapter attached to RecyclerView.")
                        } else {
                            Log.d("DetalhesFragment", "No missions to display.")
                        }
                    }
                } else {
                    // Log error if the response is not successful
                    Log.e("DetalhesFragment", "Failed to load mission data. Response: ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<MissaoResponse>, t: Throwable) {
                // Log failure if the request fails
                Log.e("DetalhesFragment", "API call failed: ${t.message}")
            }
        })
    }

}