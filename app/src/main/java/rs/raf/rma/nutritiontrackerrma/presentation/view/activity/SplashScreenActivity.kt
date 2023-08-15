package rs.raf.rma.nutritiontrackerrma.presentation.view.activity

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.graphics.alpha
import rs.raf.rma.nutritiontrackerrma.R

class SplashScreenActivity() : AppCompatActivity() {


//    private val sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)


        supportActionBar?.hide()

        val handler = Handler(Looper.getMainLooper())
//        var isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)

        var isLoggedIn = false

        if (isLoggedIn){
            handler.postDelayed({
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }, 3000)

        }else{
            handler.postDelayed({
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }, 3000)
        }

    }
}