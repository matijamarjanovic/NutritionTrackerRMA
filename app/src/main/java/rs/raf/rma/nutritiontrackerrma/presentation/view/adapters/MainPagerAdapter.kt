package rs.raf.rma.nutritiontrackerrma.presentation.view.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import rs.raf.rma.nutritiontrackerrma.R
import rs.raf.rma.nutritiontrackerrma.presentation.view.fragments.FilterFragment
import rs.raf.rma.nutritiontrackerrma.presentation.view.fragments.HomepageFragment


class MainPagerAdapter(
    fragmentManager: FragmentManager,
    private val context: Context
) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    companion object {
        private const val ITEM_COUNT = 5
        const val FRAGMENT_1 = 0
        const val FRAGMENT_2 = 1
        const val FRAGMENT_3 = 2
        const val FRAGMENT_4 = 3
        const val FRAGMENT_5 = 4

    }

    override fun getItem(position: Int): Fragment {
        return when(position) {
            FRAGMENT_1 -> HomepageFragment()
            else -> FilterFragment()
        }
    }

    override fun getCount(): Int {
        return ITEM_COUNT
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position) {
            FRAGMENT_1 -> context.getString(R.string.homepage)
            else -> context.getString(R.string.filter)
        }
    }

}