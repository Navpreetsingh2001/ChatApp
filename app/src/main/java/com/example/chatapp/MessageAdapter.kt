package com.example.chatapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.firebase.auth.FirebaseAuth


class MessageAdapter(val Context:Context , val messageList:ArrayList<Message>) :RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val ITEM_RECEIVE =1
    val ITEM_SENT =2


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (viewType == 1){
            //inflate Receive
            val view :View = LayoutInflater.from(Context).inflate(R.layout.receive,parent,false)
            return ReceiveViewHolder(view)

        }else{
            //inflate send
            val view :View =LayoutInflater.from(Context).inflate(R.layout.sent,parent,false)
            return SentViewHolder(view)
        }


    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentMessage =messageList[position]
        if (holder.javaClass ==SentViewHolder::class.java){


            val viewHolder =holder as SentViewHolder
            holder.SentMessage.text = currentMessage.message
        }
        else{
            val viewHolder =holder as ReceiveViewHolder
            holder.ReceiveMessage.text =currentMessage.message
        }

    }

    override fun getItemViewType(position: Int): Int {
        val currentMessage =messageList[position]
        if (FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.senderId)){
            return ITEM_SENT
        }
        else{
            return  ITEM_RECEIVE
        }
    }

    class SentViewHolder(itemView: View) :ViewHolder(itemView){
        val SentMessage =itemView.findViewById<TextView>(R.id.txt_sendMessage)

    }
    class ReceiveViewHolder(itemView: View) :ViewHolder(itemView){
        val ReceiveMessage =itemView.findViewById<TextView>(R.id.txt_ReceveMessage)

    }
}