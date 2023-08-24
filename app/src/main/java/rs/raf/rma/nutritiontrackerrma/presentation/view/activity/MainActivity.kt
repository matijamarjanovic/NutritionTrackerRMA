package rs.raf.rma.nutritiontrackerrma.presentation.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import rs.raf.rma.nutritiontrackerrma.R
import rs.raf.rma.nutritiontrackerrma.databinding.ActivityMainBinding
import rs.raf.rma.nutritiontrackerrma.presentation.view.adapters.MainPagerAdapter
import rs.raf.rma.nutritiontrackerrma.presentation.view.fragments.FourthFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var currentFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        initUi()
    }

    private fun initUi() {
        binding.viewPager.adapter =
            MainPagerAdapter(
                supportFragmentManager,
                this
            )

        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homepage -> {
                    binding.viewPager.currentItem = MainPagerAdapter.FRAGMENT_1
                    true
                }
                R.id.filter -> {
                    binding.viewPager.currentItem = MainPagerAdapter.FRAGMENT_2
                    true
                }
                R.id.mealList -> {
                    binding.viewPager.currentItem = MainPagerAdapter.FRAGMENT_3
                    true
                }
                R.id.stats -> {
                    binding.viewPager.currentItem = MainPagerAdapter.FRAGMENT_4
                    true
                }
               R.id.plan -> {
                    binding.viewPager.currentItem = MainPagerAdapter.FRAGMENT_5
                    true
                }
                // Add more cases for additional menu items
                else -> false
            }
        }
    }



}