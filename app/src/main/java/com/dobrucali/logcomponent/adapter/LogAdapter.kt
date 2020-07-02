package com.dobrucali.logcomponent.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dobrucali.logcomponent.databinding.ListItemLogBinding

class LogAdapter : ListAdapter<LogAdapter.CustomLog, LogAdapter.ViewHolder>(
    LogDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(
            parent
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val log = getItem(position)
        holder.bind(log)
    }

    class ViewHolder private constructor(val binding: ListItemLogBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(customLog: CustomLog) {
            binding.customLog = customLog
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemLogBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(
                    binding
                )
            }
        }
    }

    class LogDiffCallback : DiffUtil.ItemCallback<CustomLog>() {
        override fun areItemsTheSame(oldItem: CustomLog, newItem: CustomLog): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CustomLog, newItem: CustomLog): Boolean {
            return oldItem == newItem
        }
    }

    data class CustomLog(
        val id: Int,
        val message: String,
        val type: Int
    )

}

