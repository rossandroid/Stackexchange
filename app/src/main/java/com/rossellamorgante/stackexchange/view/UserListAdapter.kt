package com.rossellamorgante.stackexchange.view

import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rossellamorgante.stackexchange.R
import com.rossellamorgante.stackexchange.model.User
import kotlinx.android.synthetic.main.item_user.view.*

class UserListAdapter (var users: ArrayList<User>): RecyclerView.Adapter<UserListAdapter.UserViewHolder>() {

    class UserViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val userName = view.usr_name
        private val reputationUser = view.reputation_usr
        fun bind(user: User){
            userName.text = user.display_name
            reputationUser.text = user.reputation.toString()
            itemView.setOnClickListener {
                val intent = Intent(it.context, UserDetailsActivity::class.java).apply {
                    putExtra("USER", user)
                }
                it.context.startActivity(intent)
            }
        }


    }
    fun updateUsers(newUser: List<User>){
        users.clear()
        users.addAll(newUser)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = UserViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false))

    override fun getItemCount() = users.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(users[position])

    }
}