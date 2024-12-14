package pm.astraeus

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast

class UserFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_user, container, false)

        // botão logout
        val logoutButton: Button = view.findViewById(R.id.btnLogout)
        logoutButton.setOnClickListener { doLogout() }

        return view
    }

    private fun doLogout() {
        // limpa shared preferences
        requireActivity().getSharedPreferences("pmLogin", Context.MODE_PRIVATE)
            .edit()
            .putBoolean("login", false)
            .putString("user", "")
            .putString("jwt", "")
            .apply()

        Toast.makeText(requireContext(), "Logout feito", Toast.LENGTH_SHORT).show()


        // inicia atividade login
        val intent = Intent(requireActivity(), LoginActivity::class.java)
        startActivity(intent)

        // finaliza a atividade main e sai do fragmento
        requireActivity().finish()
    }
}