package rs.raf.rma.nutritiontrackerrma.presentation.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import rs.raf.rma.nutritiontrackerrma.databinding.ActivityMainBinding
import rs.raf.rma.nutritiontrackerrma.presentation.view.adapters.MainPagerAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

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
//        binding.tabLayout.setupWithViewPager(binding.viewPager)
    }

}