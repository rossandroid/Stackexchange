package com.rossellamorgante.stackexchange.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.SearchView
import com.rossellamorgante.stackexchange.R
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.rossellamorgante.stackexchange.isNetworkAvailable
import com.rossellamorgante.stackexchange.viewmodel.ListViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: ListViewModel
    private val usersAdapter = UserListAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)

        userlist.apply{
            layoutManager = LinearLayoutManager(context)
            adapter = usersAdapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.searching, menu)

        val searchView = menu?.findItem(R.id.app_bar_search)?.actionView as SearchView

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextSubmit(name: String?): Boolean {
                name?.let{
                    viewModel.refresh(it)
                    observeViewModel()
                }
                return false
            }

        })
        return super.onCreateOptionsMenu(menu)
    }

    fun observeViewModel(){
        viewModel.users.observe(this, Observer{users ->
            users?.let{
                smile.visibility = View.GONE
                userlist.visibility = View.VISIBLE
                usersAdapter.updateUsers(it)}
        })
        viewModel.userLoadError.observe(this, Observer{ isError ->
            isError?.let{
                smile.visibility = View.GONE
                list_error.visibility = if(it) View.VISIBLE else View.GONE
                if(!isNetworkAvailable(this)){
                    list_error.text=getString(R.string.no_data)
                }
            }
        })
        viewModel.loading.observe(this, Observer{ isLoading ->
            isLoading?.let{
                smile.visibility = View.GONE
                loading.visibility = if(it) View.VISIBLE else View.GONE
                if(it){
                    list_error.visibility = View.GONE
                    userlist.visibility = View.GONE
                }}
        })
    }


}
