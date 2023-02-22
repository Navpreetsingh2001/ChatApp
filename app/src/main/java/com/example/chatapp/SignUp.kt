package com.example.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.chatapp.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class SignUp : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var mAuth: FirebaseAuth
    private  lateinit var mdbRef:DatabaseReference




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAuth = FirebaseAuth.getInstance()
        supportActionBar?.hide()


        binding.btnSignup.setOnClickListener {
            val name =binding.etName.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            signup(name,email,password)
        }
    }
    private fun signup(name:String ,email:String ,password:String){
        //login logic
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    //add user to database
                    addUserToDatabase(name,email,mAuth.currentUser?.uid!!)
                    // Sign in success, update UI with the signed-in user's information
                    val intent =Intent(this@SignUp,MainActivity::class.java)
                    finish()
                    startActivity(intent)


                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this@SignUp, "Some error occured", Toast.LENGTH_SHORT).show()

                }
            }

    }
    private fun addUserToDatabase(name:String ,email:String,uid:String){
        mdbRef =FirebaseDatabase.getInstance().getReference()

        mdbRef.child("user").child(uid).setValue(User(name,email,uid))

    }
}