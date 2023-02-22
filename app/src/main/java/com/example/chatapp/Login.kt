package com.example.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.chatapp.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    private lateinit var mAuth :FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        mAuth =FirebaseAuth.getInstance()

        binding.btnSignup.setOnClickListener {
            var intent =Intent(this,SignUp::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnLogin.setOnClickListener {
            if (binding.etEmail.text.isEmpty() and binding.etPassword.text.isEmpty()){
                Toast.makeText(this, "Enter the Email and password ", Toast.LENGTH_SHORT).show()
            }
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            login(email,password)
        }
    }
    private fun login(email:String,password:String){
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    var intent =Intent(this@Login,MainActivity::class.java)
                    finish()
                    startActivity(intent)


                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this@Login, "User Does not Exist", Toast.LENGTH_SHORT).show()

                }
            }


    }
}