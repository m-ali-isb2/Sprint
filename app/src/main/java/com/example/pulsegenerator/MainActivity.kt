package com.example.pulsegenerator

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.pulsegenerator.sprintUI.Model.LogItem
import com.example.pulsegenerator.sprintUI.Model.LogType
import com.example.pulsegenerator.sprintUI.components.LogPanel
import com.example.pulsegenerator.sprintUI.components.upperContainer
import com.example.pulsegenerator.sprintUI.utils.formatDate

class MainActivity : ComponentActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column(    modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
                verticalArrangement = Arrangement.spacedBy(16.dp)) {
                var logItemList by remember { mutableStateOf(ArrayList<LogItem>()) }
                val listState = rememberLazyListState()
                upperContainer(
                    onEvent = {
                        Log.d("LogItem","Log Time : ${formatDate(it.logTime)}\n"+
                                "Title : ${it.title}\n"+
                                "Message : ${it.message}\n"+
                                "Log type : ${it.logType.name}\n")
                        // Clear the log list if the log type is START
                        if (it.logType == LogType.START) {
                            logItemList = ArrayList() // Clear the list
                        }
                        logItemList = (logItemList + arrayListOf(it)) as ArrayList<LogItem>

                    },
                    clearList = {
                        logItemList = ArrayList()
                    }
                )
                LogPanel(logItemList,listState)


            }

        }
    }
}



