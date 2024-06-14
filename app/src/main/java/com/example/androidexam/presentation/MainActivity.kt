package com.example.androidexam.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidexam.adapter.PersonAdapter
import com.example.androidexam.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: PersonViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val personAdapter = PersonAdapter(PersonAdapter.PersonComparator)
        val linearLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = linearLayoutManager
        binding.recyclerView.adapter = personAdapter


        launchWhenCreated {
            viewModel.getPersons().collectLatest { pagingData ->
                personAdapter.submitData(pagingData)
                binding.swipe.isRefreshing = false
            }
        }

        launchWhenCreated {
            personAdapter.loadStateFlow.collect{
                val state = it.refresh
                binding.progressBar.isVisible = state is LoadState.Loading
            }
        }

        personAdapter.onItemClick = {
            val intent = Intent(this, PersonDetails::class.java)
            intent.putExtra("person", it)
            startActivity(intent)
        }

        // handle re-fetching of images on swipe
        binding.swipe.setOnRefreshListener {
            personAdapter.refresh()
        }
    }
}

fun LifecycleOwner.launchWhenCreated(block: suspend () -> Unit) {
    lifecycleScope.launch {
        lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
            block()
        }
    }
}