package pm.astraeus

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import pm.astraeus.adapter.GlossaryPagerAdapter

class GlossaryFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_glossary, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tabLayout: TabLayout = view.findViewById(R.id.tabGlossary)
        val viewPager: ViewPager2 = view.findViewById(R.id.viewPagerGlossary)

        // Preparação de ViewPager com PagerAdapter
        viewPager.adapter = GlossaryPagerAdapter(this)

        // População do TabLayout
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Tipo de Missão"
                1 -> tab.text = "Tipo de Corpo Celeste"
                2 -> tab.text = "Estado de Missão"
            }
        }.attach()
    }
}