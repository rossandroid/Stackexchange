package com.rossellamorgante.stackexchange.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.rossellamorgante.stackexchange.R
import com.rossellamorgante.stackexchange.getCreationDate
import com.rossellamorgante.stackexchange.loadImage
import com.rossellamorgante.stackexchange.getProgressDrawable
import com.rossellamorgante.stackexchange.viewmodel.UserDetailViewModel
import kotlinx.android.synthetic.main.activity_user_details.*
import java.text.SimpleDateFormat

class UserDetailsActivity : AppCompatActivity() {

    lateinit var viewModel: UserDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_details)

        supportActionBar!!.title = "User"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        viewModel = ViewModelProviders.of(this).get(UserDetailViewModel::class.java)
        viewModel.getUser(intent)
        onViewModel()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun onViewModel() {
        viewModel.user?.let {
            user_name.text = it.display_name
            reputation.text = it.reputation.toString()
            badge.text = it.badge_counts?.printBadge()
            location.text = it.location
            creation_date.text = getCreationDate(it.creation_date)
            image_user.loadImage(it.profile_image, getProgressDrawable(this))

        }
    }
}
