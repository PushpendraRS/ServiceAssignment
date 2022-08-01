package com.example.serviceassignment

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.serviceassignment.databinding.ItemDownloadContentBinding
import com.example.serviceassignment.db.ProgressEntity

class DownloadAdapter(
    private var list     : ArrayList<ProgressEntity>,
    private val listener : IItemProgress
) : RecyclerView.Adapter<DownloadAdapter.ViewHolder>(){

     inner class ViewHolder(val binding : ItemDownloadContentBinding) : RecyclerView.ViewHolder(binding.root) {
     }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       return ViewHolder(
           ItemDownloadContentBinding.inflate(
           LayoutInflater.from(parent.context),
           parent,false
           )
       )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]

        holder.binding.textView.text        = "${item.currentProgress}%"
        holder.binding.progressBar.progress = item.currentProgress
        holder.binding.fileName.text        = item.fileName


        holder.binding.btnStart.setOnClickListener {
            holder.binding.btnStart.isEnabled = false
            listener.startService(item.id, item.currentProgress)
            Log.d(TAG, "start: ${item.id}")
        }

        holder.binding.btnCancel.setOnClickListener {
            holder.binding.btnStart.isEnabled = true
            listener.stopService(item.id)
            Log.d(TAG, "end: ${item.id}")
        }
    }

    override fun getItemCount() = list.size

    fun updateList(list : ArrayList<ProgressEntity>){
        this.list = list
        notifyDataSetChanged()
    }

}


interface IItemProgress {
    fun startService(id : Int,lastProgress: Int)
    fun stopService(id: Int)
    fun resetProgress(id: Int)
}