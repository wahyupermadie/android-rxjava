package com.godohdev.latihanrxjava

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.godohdev.latihanrxjava.data.network.ApiClient
import com.godohdev.latihanrxjava.data.network.ApiService
import kotlinx.android.synthetic.main.activity_main.rvDataTicket

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var apiService: ApiService
    private lateinit var tiketAdapter: TicketAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        apiService = ApiClient.getClient()!!
        viewModel = ViewModelProvider(this, MainViewModelFactory(apiService))[MainViewModel::class.java]
        tiketAdapter = TicketAdapter(this)
        rvDataTicket.apply {
            adapter = tiketAdapter
        }

        viewModel.ticketsFlights.observe(this, Observer {
            if (it != null){
                tiketAdapter.addData(it)
            }
        })

        viewModel.priceFlight.observe(this, Observer {
            if (it != null){
                for(i in it.keys){
                    tiketAdapter.addPrice(i, it[i]!!)
                }
            }
        })

        viewModel.fetchAllTickets(viewModel.fetchTicketList("DEL","HYD")!!)
    }
}

class MainViewModelFactory(private val apiService: ApiService) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(apiService) as T
    }
}
