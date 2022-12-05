package com.example.appli_mobile_clemence_pierre.tasklist

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.appli_mobile_clemence_pierre.R
import com.example.appli_mobile_clemence_pierre.data.Api
import com.example.appli_mobile_clemence_pierre.detail.DetailActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch

class TaskListFragment : Fragment() {
    //    private var taskList = listOf("Task 1", "Task 2", "Task 3")
//    private var taskList = listOf(
//        Task(id = "id_1", title = "Task 1", description = "description 1"),
//        Task(id = "id_2", title = "Task 2"),
//        Task(id = "id_3", title = "Task 3")
//    )

    private val adapter = TaskListAdapter()

    val createTask =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            // dans cette callback on récupèrera la task et on l'ajoutera à la liste
            val task = result.data?.getSerializableExtra("task") as Task?
//        val newTask = Task(id = UUID.randomUUID().toString(), title = "Task ${taskList.size + 1}")
            viewModel.add(task!!)
//            taskList += task!!
//            adapter.submitList(taskList)
//            adapter.notifyItemChanged(taskList.size)
        }

    private val viewModel: TasksListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
//        adapter.currentList = taskList
//        adapter.submitList(taskList)
        return inflater.inflate(R.layout.fragment_task_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.adapter = adapter
        val floatingActionButton =
            view.findViewById<FloatingActionButton>(R.id.floatingActionButton)
        floatingActionButton.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java)
            createTask.launch(intent)

//            startActivity(intent)

//            val newTask = Task(id = UUID.randomUUID().toString(), title = "Task ${taskList.size + 1}")
//            taskList += newTask
//            adapter.submitList(taskList)
//            adapter.notifyItemChanged(taskList.size)
        }

        lifecycleScope.launch { // on lance une coroutine car `collect` est `suspend`
            viewModel.tasksStateFlow.collect { newList ->
                // cette lambda est executée à chaque fois que la liste est mise à jour dans le VM
                // -> ici, on met à jour la liste dans l'adapter
                adapter.submitList(newList)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.refresh()
        lifecycleScope.launch {
            mySuspendMethod()
        }
    }

    suspend fun mySuspendMethod() {
        val user = Api.userWebService.fetchUser().body()!!
        view?.findViewById<TextView>(R.id.userTextView)?.text = user.name
    }
}
