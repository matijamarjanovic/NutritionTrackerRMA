package rs.raf.rma.nutritiontrackerrma.presentation.view.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rs.raf.rma.nutritiontrackerrma.R
import rs.raf.rma.nutritiontrackerrma.data.models.user.User
import rs.raf.rma.nutritiontrackerrma.data.repositories.user.UserRepository
import rs.raf.rma.nutritiontrackerrma.data.repositories.user.UserRepositoryImpl
import rs.raf.rma.nutritiontrackerrma.databinding.ActivityLoginBinding
import rs.raf.rma.nutritiontrackerrma.presentation.SharedPreferencesManager
import rs.raf.rma.nutritiontrackerrma.presentation.contracts.MealsContract
import rs.raf.rma.nutritiontrackerrma.presentation.contracts.UserContract
import rs.raf.rma.nutritiontrackerrma.presentation.viewmodels.LoginViewModel
import rs.raf.rma.nutritiontrackerrma.presentation.viewmodels.MealsViewModel


class LoginActivity : AppCompatActivity(R.layout.activity_login) {

    private lateinit var sharedPreferencesManager: SharedPreferencesManager
    private lateinit var binding: ActivityLoginBinding
    private lateinit var a:LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferencesManager = SharedPreferencesManager(this)
        val loginButton: Button = findViewById(R.id.loginButton)
        val CheckBox: CheckBox = findViewById(R.id.rememberMeCheckbox)


        //println(viewModel.getUser("user1","user1"))

        // Check if the user is already logged in
        if (sharedPreferencesManager.isLoggedIn()) {
            redirectToMainActivity()
            return
        }

        loginButton.setOnClickListener {
            val username = binding.usernameEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            if (isValidCredentials(username, password)) {
                // Save the login status in SharedPreferences if "Remember Me" is checked
                if (CheckBox.isChecked) {
                    sharedPreferencesManager.setLoggedIn(true)
                }

                // Redirect to the MainActivity
                redirectToMainActivity()
            } else {
                // Handle invalid credentials
            }
        }
    }

    private fun isValidCredentials(username: String, password: String): Boolean {
        // Implement your logic to validate the credentials here
        // Return true if the credentials are valid, false otherwise
        return true
    }

    private fun redirectToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}