package rs.raf.rma.nutritiontrackerrma.presentation.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import rs.raf.rma.nutritiontrackerrma.R
import rs.raf.rma.nutritiontrackerrma.presentation.view.adapters.TabbedPagerAdapter


class FourthFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tabbed_4, container, false)

        val tabLayout = view.findViewById<TabLayout>(R.id.tabLayout)
        val viewPager = view.findViewById<ViewPager2>(R.id.viewPager)

        val tabTitles = arrayOf("Meal Statistics", "Calorie Calculator", "Tab 3") // Replace with your tab titles

        val adapter = TabbedPagerAdapter(childFragmentManager, lifecycle)
        viewPager.adapter = adapter
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()

        return view
    }
}

