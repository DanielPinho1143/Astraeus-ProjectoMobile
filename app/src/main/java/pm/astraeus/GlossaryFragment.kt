package pm.astraeus

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayout

class GlossaryFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_glossary, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val root: ViewGroup = view.findViewById(R.id.glossaryInclude)
        val inflater = LayoutInflater.from(requireContext())
        val tabs: TabLayout = view.findViewById(R.id.tabGlossary)

        tabs.addTab(tabs.newTab().setText("Tab 1"))
        tabs.addTab(tabs.newTab().setText("Tab 2"))
        tabs.addTab(tabs.newTab().setText("Tab 3"))
        tabs.tabGravity = TabLayout.GRAVITY_FILL

        tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                root.removeAllViews()
                when (tabs.selectedTabPosition) {
                    0 -> inflater.inflate(R.layout.tab_tipo_missao, root, true)
                    1 -> inflater.inflate(R.layout.tab_tipo_corpo, root, true)
                    2 -> inflater.inflate(R.layout.tab_estado_missao, root, true)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // Optionally handle tab unselected
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // Optionally handle tab reselected
            }
        })
    }
}
