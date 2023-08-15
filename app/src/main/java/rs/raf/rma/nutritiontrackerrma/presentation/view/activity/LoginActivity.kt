package rs.raf.rma.nutritiontrackerrma.presentation.view.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import rs.raf.rma.nutritiontrackerrma.R


class LoginActivity() : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val loginButton : Button = findViewById(R.id.loginButton)
        val username : TextView = findViewById(R.id.usernameEditText)
        val password : TextView = findViewById(R.id.passwordEditText)


    /*

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