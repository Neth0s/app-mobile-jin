package com.example.appli_mobile_clemence_pierre.detail

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.appli_mobile_clemence_pierre.detail.ui.theme.ApplimobileclemencepierreTheme
import com.example.appli_mobile_clemence_pierre.tasklist.Task
import java.util.UUID

class DetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ApplimobileclemencepierreTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Detail("Android") {
                        intent.putExtra("task", it)
                        setResult(RESULT_OK, intent)
                        finish()
                    }
                }
            }
        }
    }
}

@Composable
fun Detail(name: String, onValidate: (Task) -> Unit) {
    var taskTitle by remember { mutableStateOf( "TITLE") }
    val mContext = LocalContext.current
    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text(text = "Task detail: $name!", style = MaterialTheme.typography.h4)
        OutlinedTextField(value = taskTitle, onValueChange = { taskTitle = it }, label = { Text("Title") })
        Button(onClick = {
            val newTask = Task(id = UUID.randomUUID().toString(), title = taskTitle)
            Toast.makeText(mContext, "This is a Sample Toast", Toast.LENGTH_SHORT).show()
            onValidate(newTask)
        }) {
            Text(text = "BOOH THON")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ApplimobileclemencepierreTheme {
        Detail("Android") { }
    }
}