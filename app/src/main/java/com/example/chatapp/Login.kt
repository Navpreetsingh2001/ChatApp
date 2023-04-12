package com.example.chatapp

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.example.chatapp.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    private lateinit var mAuth :FirebaseAuth
    private lateinit var emailtext:EditText
    private lateinit var passwordtext:EditText
    private lateinit var sdf:SharedPreferences
    private lateinit var Editor:SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        mAuth =FirebaseAuth.getInstance()

         emailtext =binding.etEmail
        passwordtext=binding.etPassword
        sdf =getSharedPreferences("my_sf", MODE_PRIVATE)
        Editor=sdf.edit()

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

    override fun onPause() {
        super.onPause()
        val email =emailtext.text.toString()
        val password =passwordtext.text.toString()
        Editor.apply {
            putString("sf_name",email)
            putString("sf_pass",password)
            commit()
        }
    }

    override fun onPostResume() {
        super.onPostResume()

        val email =sdf.getString("sf_name",null)
        val pass =sdf.getString("sf_pass",null)
        emailtext.setText(email)
        passwordtext.setText(pass.toString())
    }

}