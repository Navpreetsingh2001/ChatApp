package com.example.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatapp.databinding.ActivityChatBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding

    private lateinit var messageAdapter:MessageAdapter
    private lateinit var messageList:ArrayList<Message>
    private lateinit var mDbRef:DatabaseReference

    var receiverRoom:String? =null
    var senderRoom:String? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)



        val name =intent.getStringExtra("name")
        val receiverUid =intent.getStringExtra("uid")

        val senderUid =FirebaseAuth.getInstance().currentUser?.uid
        mDbRef =FirebaseDatabase.getInstance().getReference()


        senderRoom =receiverUid+senderUid
        receiverRoom=senderUid+receiverUid

        supportActionBar?.title =name

        messageList = ArrayList()
        messageAdapter = MessageAdapter(this,messageList)

        binding.chatRv.layoutManager =LinearLayoutManager(this)
        binding.chatRv.adapter =messageAdapter

        //Logic for adding data to recyclerView
        mDbRef.child("chats").child(senderRoom!!).child("message")
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {

                    messageList.clear()

                    for (postSnapshot in snapshot.children){
                        val message =postSnapshot.getValue(Message::class.java)
                        messageList.add(message!!)
                    }
                    messageAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })

        //adding the message to database
        binding.btnSent.setOnClickListener {
            if (binding.messageBox.text.toString() == ""){
                Toast.makeText(this, "Enter text", Toast.LENGTH_SHORT).show()
            }
            val message = binding.messageBox.text.toString()
            val messageObject =Message(message,senderUid)

            mDbRef.child("chats").child(senderRoom!!).child("message").push()
                .setValue(messageObject).addOnSuccessListener {
                    mDbRef.child("chats").child(receiverRoom!!).child("message").push()
                        .setValue(messageObject)
                }
            binding.messageBox.setText("")
        }
    }
}