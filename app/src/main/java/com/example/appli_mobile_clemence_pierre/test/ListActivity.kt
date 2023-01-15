package com.example.appli_mobile_clemence_pierre.test

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.appli_mobile_clemence_pierre.test.ui.theme.ApplimobileclemencepierreTheme

class ListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ApplimobileclemencepierreTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        val mContext = LocalContext.current
                        FloatingActionButton(
                            onClick = {
                                mContext.startActivity(
                                    Intent(
                                        mContext,
                                        TestActivity::class.java
                                    )
                                )
                            },
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(20.dp),
                            shape = CircleShape,
                            backgroundColor = Color.Red
                        ) {
                            Icon(Icons.Filled.ArrowBack, "See list")
                        }
                    }
                    Column(modifier = Modifier.fillMaxSize()) {
                        profiles
                            .filter { !it.swiped }
                            .forEach { profile ->
                                key(profile) {
                                    Box(
                                        modifier = Modifier
                                            .border(BorderStroke(2.dp, Color.Red))
                                            .padding(5.dp)
                                    ) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Text(text = profile.name, textAlign = TextAlign.End)
                                            Spacer(modifier = Modifier.size(10.dp))
                                            Text(
                                                text = profile.description,
//                                                modifier = Modifier.fillMaxWidth(),
                                                softWrap = false,
//                                                textAlign = TextAlign.End
                                            )
                                        }
                                    }
                                }
                            }
                    }
                }
            }
        }
    }
}
