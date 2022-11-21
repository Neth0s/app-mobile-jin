package com.example.appli_mobile_clemence_pierre.tasklist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.appli_mobile_clemence_pierre.R

class TaskListAdapter : ListAdapter<Task, TaskListAdapter.TaskViewHolder>(ItemsDiffCallback) {
    //    var currentList: List<Task> = emptyList()

    object ItemsDiffCallback : DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(oldItem: Task, newItem: Task) : Boolean {
            return oldItem.id == newItem.id // comparison: are they the same "entity" ? (usually same id)
        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task) : Boolean {
            return oldItem == newItem // comparison: are they the same "content" ? (simplified for data class)
        }
    }

    // on utilise `inner` ici afin d'avoir accès aux propriétés de l'adapter directement
    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(taskTitle: Task) {
            // on affichera les données ici
            val textView = itemView.findViewById<TextView>(R.id.task_title)
            textView.text = taskTitle.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

//    override fun getItemCount(): Int {
//        return currentList.size
//    }
}
