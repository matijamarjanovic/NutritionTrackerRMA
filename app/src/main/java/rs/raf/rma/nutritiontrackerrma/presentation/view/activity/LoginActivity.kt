package rs.raf.rma.nutritiontrackerrma.presentation.view.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import rs.raf.rma.nutritiontrackerrma.R
import rs.raf.rma.nutritiontrackerrma.databinding.ActivityLoginBinding


class LoginActivity() : AppCompatActivity() {

    private lateinit var sharedPreferencesManager: SharedPreferencesManager
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferencesManager = SharedPreferencesManager(this)


        val loginButton : Button = findViewById(R.id.loginButton)
        //val username : TextView = findViewById(R.id.usernameEditText)
        //val password : TextView = findViewById(R.id.passwordEditText)


        loginButton.setOnClickListener{
            val username = binding.usernameEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            if(isValidCredentials(username,password)){
            println("AAAAAAA"+username+" "+password)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()}
            else{

            }
        }
    }
    private fun isValidCredentials(username: String, password: String): Boolean {

        return true
    }
}