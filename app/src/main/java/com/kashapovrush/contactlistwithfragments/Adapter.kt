package com.kashapovrush.contactlistwithfragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class Adapter : ListAdapter<User, Adapter.ViewHolder>(DiffUtilCallback()) {

    var onClickChangedUserInfoListener: ((User) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.list_user_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = getItem(position)
        with(holder) {
            firstName.text = user.firstName
            lastName.text = user.lastName
            phoneNumber.text = user.phoneNumber
            imageUser.setImageURI(user.image)

            view.setOnClickListener {
                onClickChangedUserInfoListener?.invoke(user)
            }
        }


    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val imageUser = view.findViewById<ImageView>(R.id.user_photo)
        val firstName = view.findViewById<TextView>(R.id.first_name)
        val lastName = view.findViewById<TextView>(R.id.last_name)
        val phoneNumber = view.findViewById<TextView>(R.id.phone_number)
    }
}