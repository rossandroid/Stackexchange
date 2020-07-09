package com.rossellamorgante.stackexchange.viewmodel

import android.content.Intent
import androidx.lifecycle.ViewModel
import com.rossellamorgante.stackexchange.model.User

class UserDetailViewModel : ViewModel(){
    lateinit var user: User

    fun getUser(intent: Intent) {
        user = intent.extras!!.get("USER") as User
    }
}