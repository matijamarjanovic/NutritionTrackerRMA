package rs.raf.rma.nutritiontrackerrma.presentation.view.activity

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.w3c.dom.Text
import rs.raf.rma.nutritiontrackerrma.R


class LoginActivity : AppCompatActivity() {


//    private val sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val loginButton : Button = findViewById(R.id.loginButton)
        val username : TextView = findViewById(R.id.usernameEditText)
        val password : TextView = findViewById(R.id.passwordEditText)

/*        val editor : SharedPreferences.Editor = sharedPreferences.edit()

        if(username.text == "matija" && password.text == "1234"){
            editor.putBoolean("isLoggedIn", true)
            editor.apply()

            loginButton.setOnClickListener{
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }else{
            editor.putBoolean("isLoggedIn", false)
            editor.apply()

            loginButton.setOnClickListener{
                Toast.makeText(this, "Wrong credentials", Toast.LENGTH_LONG)
            }
        }*/

        loginButton.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }



    }
}