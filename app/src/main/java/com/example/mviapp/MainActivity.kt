package com.example.mviapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mviapp.data.api.ApiConnectorSingelton
import com.example.mviapp.data.model.UniversityDataModel
import com.example.mviapp.databinding.ActivityMainBinding
import com.example.mviapp.ui.main.adapter.MainAdapter
import com.example.mviapp.ui.main.intent.MainIntent
import com.example.mviapp.ui.main.viewmodel.MainViewModel
import com.example.mviapp.ui.main.viewstate.MainState
import com.example.mviapp.util.MainViewModelFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var mainViewModelFactory: MainViewModelFactory
    private lateinit var mainAdapter: MainAdapter

    private lateinit var binding: ActivityMainBinding

    companion object {
        const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        mainAdapter = MainAdapter(emptyList())
        setupUI(mainAdapter)
        setupViewModel()
        observeViewModel()
        setupClicks()
    }

    private fun setupClicks() {
        binding.countrySubmitButton.setOnClickListener {
            lifecycleScope.launch {
                val countryName: String = binding.searchBar.text.toString()
                if (countryName.isNotBlank() && countryName.isNotEmpty()) {
                    mainViewModel.userIntent.send(MainIntent.FetchUniversityData(countryName))
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        "Please enter valid Country name",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            mainViewModel.state.collect {
                when (it) {
                    is MainState.Idle -> {

                    }

                    is MainState.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.recyclerView.visibility = View.GONE
                    }

                    is MainState.UniData -> {
                        binding.progressBar.visibility = View.GONE
                        binding.recyclerView.visibility = View.VISIBLE

                        renderData(it.uniDataList)
                    }
                    is MainState.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(this@MainActivity, it.error, Toast.LENGTH_LONG).show()
                        Log.e(TAG, it.error!!)
                    }
                }
            }
        }
    }

    private fun renderData(uniData: List<UniversityDataModel>) {
        binding.recyclerView.visibility = View.VISIBLE
        uniData.let {
            it.let {
                mainAdapter.addData(it)
            }
        }
        mainAdapter.notifyDataSetChanged()
    }

    private fun setupViewModel() {
        mainViewModelFactory = MainViewModelFactory(ApiConnectorSingelton)
        mainViewModel = ViewModelProvider(this, mainViewModelFactory).get(MainViewModel::class.java)
    }

    private fun setupUI(mainAdapter: MainAdapter) {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.run {
            addItemDecoration(
                DividerItemDecoration(
                    binding.recyclerView.context,
                    (binding.recyclerView.layoutManager as LinearLayoutManager).orientation
                )
            )
        }
        binding.recyclerView.adapter = mainAdapter
    }
}