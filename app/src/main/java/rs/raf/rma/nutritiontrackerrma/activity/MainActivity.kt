package rs.raf.rma.nutritiontrackerrma.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import rs.raf.rma.nutritiontrackerrma.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    private fun init(){
        initNavigation()
        initTimber()
        initKotlin()
    }

    private fun initKotlin() {
       // TODO("Not yet implemented")
    }

    private fun initTimber() {
        //TODO("Not yet implemented")
    }

    private fun initNavigation() {
        //TODO("Not yet implemented")
    }
}