package com.example.serviceassignment

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.serviceassignment.databinding.ActivityMainBinding
import com.example.serviceassignment.db.ProgressEntity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), IItemProgress {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: ProgressViewModel by viewModels()
    private lateinit var adapter: DownloadAdapter
    private var list = ArrayList<ProgressEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        getDownloadList()
        setClickListener()
        setAdapter()
    }

    private fun getDownloadList() {
        lifecycleScope.launch {
            viewModel.getProgress().collect {
                list = it as ArrayList<ProgressEntity>
                adapter.updateList(list)
            }
        }
    }

    private fun setClickListener() {
        binding.fab.setOnClickListener {
            lifecycleScope.launch {
                viewModel.insertProgress(
                    ProgressEntity(0,  "${System.currentTimeMillis()}.mp4", 0))
            }
        }
    }

    private fun setAdapter() {
        adapter = DownloadAdapter(list, this)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }

    override fun startService(id: Int,lastProgress: Int) {
        val intent = Intent(this, DownloaderService::class.java)
        intent.putExtra("lastProgress",lastProgress)
        intent.putExtra("startId",id)
        intent.putExtra("isStart",true)
        startService(intent)
    }

    override fun stopService(id: Int) {
        val intent = Intent(this, DownloaderService::class.java)
        intent.putExtra("startId",id)
        intent.putExtra("isStart",false)
        startService(intent)
    }

    override fun resetProgress(id: Int) {
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.updateProgress(0,id)
        }
    }
}