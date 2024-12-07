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

        // Set the onClickListener programmatically
        val logoutButton: Button = view.findViewById(R.id.btnLogout)
        logoutButton.setOnClickListener { doLogout() }

        return view
    }

    private fun doLogout() {
        // Clear the shared preferences for logout
        requireActivity().getSharedPreferences("pmLogin", Context.MODE_PRIVATE)
            .edit()
            .putBoolean("login", false)
            .putString("user", "")
            .putString("jwt", "")
            .apply()

        // Show a Toast message
        Toast.makeText(requireContext(), "Logout feito", Toast.LENGTH_SHORT).show()


        // Start MainActivity
        val intent = Intent(requireActivity(), LoginActivity::class.java)
        startActivity(intent)

        // Finish the current Activity (this will pop the fragment and go back to the main activity)
        requireActivity().finish()
    }
}