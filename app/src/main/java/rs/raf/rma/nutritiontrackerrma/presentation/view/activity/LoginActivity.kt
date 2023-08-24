package rs.raf.rma.nutritiontrackerrma.presentation.view.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import io.reactivex.schedulers.Schedulers
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
import org.koin.androidx.viewmodel.ext.android.viewModel


class LoginActivity : AppCompatActivity(R.layout.activity_login) {

    private lateinit var sharedPreferencesManager: SharedPreferencesManager
    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferencesManager = SharedPreferencesManager(this)
        val loginButton: Button = findViewById(R.id.loginButton)
        val CheckBox: CheckBox = findViewById(R.id.rememberMeCheckbox)
        //viewModel.addUser(User("asda","asda"))
        //viewModel.getUser("asda","AAAA")
        val user1 = User("matija", "sifra1")
        val user2 = User("milos", "sifra")

        viewModel.addUser(user1)
        viewModel.addUser(user2)


        //viewModel.getUser("matija", "sifra1")
//        if(viewModel.checkUserExists("milos","sifra1")==1){
//            println("1SSSSSSSSSSSSS")
//        }

        // Check if the user is already logged in
        if (sharedPreferencesManager.isLoggedIn()) {
            redirectToMainActivity()
            return
        }

        loginButton.setOnClickListener {



            val username = binding.usernameEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            val checkFlag=viewModel.checkUser(username)

            if(checkFlag == 1){

                val user=viewModel.getUser(username)
                if (isValidCredentials(user,username, password)) {
                    sharedPreferencesManager.saveUsername(username)
                    if (CheckBox.isChecked) {
                        sharedPreferencesManager.setLoggedIn(true)
                    }
                    redirectToMainActivity()
                } else {

                }

            }else{

            }

            //skipuje proveru
            redirectToMainActivity()
        }
    }

    private fun isValidCredentials(user:User,username: String, password: String): Boolean {
        if(user.username==username && user.password==password){
            return true
        }else{
            return false
        }
    }

    private fun redirectToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}