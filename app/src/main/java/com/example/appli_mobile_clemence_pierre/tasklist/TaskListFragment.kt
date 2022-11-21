package com.example.appli_mobile_clemence_pierre.tasklist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.appli_mobile_clemence_pierre.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.UUID

class TaskListFragment : Fragment() {
//    private var taskList = listOf("Task 1", "Task 2", "Task 3")
    private var taskList = listOf(
        Task(id = "id_1", title = "Task 1", description = "description 1"),
        Task(id = "id_2", title = "Task 2"),
        Task(id = "id_3", title = "Task 3")
    )

    private val adapter = TaskListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        adapter.currentList = taskList
        return inflater.inflate(R.layout.fragment_task_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.adapter = adapter
        val floatingActionButton = view.findViewById<FloatingActionButton>(R.id.floatingActionButton)
        floatingActionButton.setOnClickListener {
            val newTask = Task(id = UUID.randomUUID().toString(), title = "Task ${taskList.size + 1}")
            taskList += newTask
            adapter.currentList = taskList
            adapter.notifyItemChanged(taskList.size)
//            adapter.notifyDataSetChanged()
        }
    }
}
