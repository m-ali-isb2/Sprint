package com.example.pulsegenerator.sprintUI.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pulsegenerator.sprintUI.Model.LogItem
import com.example.pulsegenerator.sprintUI.Model.LogType
import com.formula.sprint.Sprint


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    upperContainer()
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun upperContainer(
    onEvent: ((
        logItem: LogItem,
    ) -> Unit)? = null,
    clearList: (()
    -> Unit)? = null
) {
    val backgroundColor = Color(0xFFE3F2FD) // Light blue for a fresh and calming look
    var sprint = Sprint.getInstance()
    var sprintStatus by rememberSaveable { mutableStateOf(sprint.getSprintStatus()) }
    val buttonBackgroundColor = Color(0xFFBBDEFB) // Light blue for buttons

    var round: (timeMillis: Long, round: Long, roundType: Sprint.RoundType) -> Unit =
        { timeMillis: Long, round: Long, roundType: Sprint.RoundType ->
            val message =
                "ElapsedTime : $timeMillis\nThread ${Thread.currentThread().name}\nRoundType : ${roundType.name}"
            Log.d("SPRINT_TAG", message)
            val logItem = LogItem(
                logTime = System.currentTimeMillis(),
                title = "Round : $round",
                message = message,
                logType = when (roundType) {
                    Sprint.RoundType.START_ROUND -> {
                        sprintStatus = sprint.getSprintStatus()
                        LogType.ROUND
                    }

                    Sprint.RoundType.INTERMEDIATE_ROUND -> {
                        LogType.ROUND
                    }

                    else -> {
                        sprintStatus = sprint.getSprintStatus()
                        LogType.LAST_ROUND
                    }
                }
            )
            onEvent?.invoke(logItem)
        }

    var intervalValue by rememberSaveable { mutableStateOf("2000") }
    var iterationsValue by rememberSaveable { mutableStateOf("") }
    var isCountdownEnabled by rememberSaveable { mutableStateOf(false) }
    var isDispatchOnMainEnabled by rememberSaveable { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(16.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            // Stylish Title
            Text(
                text = "Sprint Config", // The title of your section
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1976D2), // Darker color for better contrast
                    letterSpacing = 1.5.sp
                ),
                modifier = Modifier.padding(bottom = 16.dp) // Padding to give space after the title
            )
            // Interval Input
            StyledTextField(
                value = intervalValue,
                onValueChange = { intervalValue = it },
                label = "Interval in millis"
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Iterations Input
            StyledTextField(
                value = iterationsValue,
                onValueChange = { iterationsValue = it },
                label = "Iterations (keep empty for Indeterminate)"
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Toggle Buttons
            StyledToggleButton(
                checked = isDispatchOnMainEnabled,
                onCheckedChange = { isDispatchOnMainEnabled = it },
                text = "Dispatch on Main"
            )
            if (sprintStatus == Sprint.SprintStatus.STOP) {
                Spacer(modifier = Modifier.height(8.dp))
                StyledToggleButton(
                    checked = isCountdownEnabled,
                    onCheckedChange = { isCountdownEnabled = it },
                    text = "Countdown"
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                if (sprintStatus != Sprint.SprintStatus.STOP) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f, false),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {

                        SingleButton(
                            text = if (sprintStatus == Sprint.SprintStatus.PAUSE) "Resume" else "Pause",
                            onClick = {
                                if (sprint.isSprintPaused()) {
                                    if (sprint.resume()) {
                                        Log.d("SPRINT_TAG", "Sprint resume successful")
                                        val logItem = LogItem(
                                            logTime = System.currentTimeMillis(),
                                            title = "Sprint ${LogType.RESUME.name}",
                                            message = null,
                                            logType = LogType.RESUME
                                        )
                                        onEvent?.invoke(logItem)
                                    } else {
                                        Log.d("SPRINT_TAG", "Sprint not resumed")
                                    }
                                } else {
                                    if (
                                        sprint.pause()
                                    ) {
                                        Log.d("SPRINT_TAG", "Sprint pause successful")
                                        val logItem = LogItem(
                                            logTime = System.currentTimeMillis(),
                                            title = "Sprint ${LogType.PAUSE.name}",
                                            message = "Pause At ${sprint.pauseAt()}",
                                            logType = LogType.PAUSE
                                        )
                                        onEvent?.invoke(logItem)
                                    } else {
                                        Log.d("SPRINT_TAG", "Sprint not paused")
                                    }
                                }
                                sprintStatus = sprint.getSprintStatus()
                            }

                        )
                        SingleButton(text = "Stop", onClick = {
                            if (sprint.stop()) {
                                Log.d("SPRINT_TAG", "Sprint Stopped!")
                                val logItem = LogItem(
                                    logTime = System.currentTimeMillis(),
                                    title = "Sprint ${LogType.STOP.name}",
                                    message = null,
                                    logType = LogType.STOP
                                )
                                onEvent?.invoke(logItem)

                            }
                            sprintStatus = sprint.getSprintStatus()
                        })
                        SingleButton(text = "Update", onClick = {

                            try {
                                if (intervalValue.isNullOrEmpty()) {
                                    var message = "Cannot Update Interval"
                                    val logItem = LogItem(
                                        logTime = System.currentTimeMillis(),
                                        title = LogType.UPDATE.name,
                                        message = message,
                                        logType = LogType.EXCEPTION
                                    )
                                    onEvent?.invoke(logItem)
                                    return@SingleButton
                                } else {
                                    if (sprint.updateInterval(intervalValue.toLong())) {
                                        Log.d(
                                            "SPRINT_TAG",
                                            "onUpdateClick update Interval : ${intervalValue.toLong()}"
                                        )
                                        var message = "New Interval : ${intervalValue.toLong()}"
                                        val logItem = LogItem(
                                            logTime = System.currentTimeMillis(),
                                            title = LogType.UPDATE.name,
                                            message = message,
                                            logType = LogType.UPDATE
                                        )
                                        onEvent?.invoke(logItem)
                                    }
                                    /*else{
                                        var message = "Cannot Update Interval : ${intervalValue.toLong()}"
                                        val logItem = LogItem(
                                            logTime = System.currentTimeMillis(),
                                            title = LogType.UPDATE.name,
                                            message = message,
                                            logType = LogType.UPDATE
                                        )
                                        onEvent?.invoke(logItem)
                                    }*/
                                }
                            } catch (e: Exception) {
                                Log.d("SPRINT_TAG", "onUpdateInterval Exception : ${e.message}")
                                var message = "Exception on update Interval : ${e.message}"
                                val logItem = LogItem(
                                    logTime = System.currentTimeMillis(),
                                    title = LogType.EXCEPTION.name,
                                    message = message,
                                    logType = LogType.EXCEPTION
                                )
                                onEvent?.invoke(logItem)

                            }
                            try {
                                val iterationVal: Long? = if (iterationsValue.isNullOrEmpty()) {
                                    null
                                } else {
                                    iterationsValue.toLong()
                                }
                                if (sprint.updateIterations(iterationVal)) {
                                    Log.d(
                                        "SPRINT_TAG",
                                        "onUpdateClick update Iterations : ${iterationVal}"
                                    )
                                    var message = "New Iteration : ${iterationVal}"
                                    val logItem = LogItem(
                                        logTime = System.currentTimeMillis(),
                                        title = LogType.UPDATE.name,
                                        message = message,
                                        logType = LogType.UPDATE
                                    )
                                    onEvent?.invoke(logItem)
                                }/*else{
                                    var message = "Cannot Update Iteration : ${iterationVal}"
                                    val logItem = LogItem(
                                        logTime = System.currentTimeMillis(),
                                        title = LogType.UPDATE.name,
                                        message = message,
                                        logType = LogType.UPDATE
                                    )
                                    onEvent?.invoke(logItem)
                                }*/
                            } catch (e: Exception) {
                                Log.d("SPRINT_TAG", "onUpdateIteration Exception : ${e.message}")
                                var message = "Exception on update Iteration : ${e.message}"
                                val logItem = LogItem(
                                    logTime = System.currentTimeMillis(),
                                    title = LogType.EXCEPTION.name,
                                    message = message,
                                    logType = LogType.EXCEPTION
                                )
                                onEvent?.invoke(logItem)
                            }
                            if (sprint.updateDispatch(isDispatchOnMainEnabled)) {
                                Log.d("SPRINT_TAG", "dispatcher change successful")
                                var message = "Dispatch on Main : $isDispatchOnMainEnabled"
                                val logItem = LogItem(
                                    logTime = System.currentTimeMillis(),
                                    title = LogType.UPDATE.name,
                                    message = message,
                                    logType = LogType.UPDATE
                                )
                                onEvent?.invoke(logItem)
                            }
                            sprintStatus = sprint.getSprintStatus()
                        })


                    }
                } else {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f, false),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        SingleButton(text = "Start", onClick = {

                            try {
                                if (intervalValue.isNullOrEmpty()) {
                                    var message = "Cannot Start Interval is Empty"
                                    val logItem = LogItem(
                                        logTime = System.currentTimeMillis(),
                                        title = LogType.START.name,
                                        message = message,
                                        logType = LogType.EXCEPTION
                                    )
                                    onEvent?.invoke(logItem)
                                    return@SingleButton
                                } else {
                                    val iterationVal: Long? = if (iterationsValue.isNullOrEmpty()) {
                                        null
                                    } else {
                                        iterationsValue.toLong()
                                    }
                                    if (sprint.start(
                                            Sprint.Config(
                                                intervalValue.toLong(),
                                                iterationVal,
                                                isCountdownEnabled,
                                                isDispatchOnMainEnabled
                                            ), onRoundStart = round
                                        )
                                    ) {
                                        sprintStatus = sprint.getSprintStatus()
                                        Log.d("SPRINT_TAG", "Sprint Start successful")
                                        val logItem = LogItem(
                                            logTime = System.currentTimeMillis(),
                                            title = "Sprint ${LogType.START.name}",
                                            message = null,
                                            logType = LogType.START
                                        )
                                        onEvent?.invoke(logItem)
                                    }
                                }

                            } catch (e: Exception) {
                                Log.d("SPRINT_TAG", "onStartClick Exception : ${e.message}")
                                var message = "Exception on Start : ${e.message}"
                                val logItem = LogItem(
                                    System.currentTimeMillis(),
                                    LogType.EXCEPTION.name,
                                    message,
                                    LogType.EXCEPTION
                                )
                                onEvent?.invoke(logItem)

                            }
                        })


                        SingleButton(text = "Clear List", onClick = {
                            clearList?.invoke()
                        })
                    }
                }
            }
        }
    }
}

//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun upperContainer(
//    onEvent: ((logItem: LogItem) -> Unit)? = null
//) {
//    val sprint = Sprint.getInstance()
//    var sprintStatus by rememberSaveable { mutableStateOf(sprint.getSprintStatus()) }
//    var round: (timeMillis: Long, round: Long, roundType: Sprint.RoundType) -> Unit =
//        { timeMillis: Long, round: Long, roundType: Sprint.RoundType ->
//            val message =
//                "ElapseTime : $timeMillis\nThread ${Thread.currentThread().name}\nRoundType : ${roundType.name}"
//            Log.d("SPRINT_TAG", message)
//            val logItem = LogItem(
//                logTime = System.currentTimeMillis(),
//                title = "Round : $round",
//                message = message,
//                logType = when (roundType) {
//                    Sprint.RoundType.START_ROUND -> {
//                        sprintStatus = sprint.getSprintStatus()
//                        LogType.ROUND
//                    }
//
//                    Sprint.RoundType.INTERMEDIATE_ROUND -> {
//                        LogType.ROUND
//                    }
//
//                    else -> {
//                        sprintStatus = sprint.getSprintStatus()
//                        LogType.LAST_ROUND
//                    }
//                }
//            )
//            onEvent?.invoke(logItem)
//        }
//
//    var intervalValue by rememberSaveable { mutableStateOf("2000") }
//    var iterationsValue by rememberSaveable { mutableStateOf("10") }
//    var isCountdownEnabled by rememberSaveable { mutableStateOf(false) }
//    var isDispatchOnMainEnabled by rememberSaveable { mutableStateOf(false) }
//
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color(0xFFE3F2FD)) // Light Blue
//            .padding(16.dp)
//    ) {
//        Card(
//            modifier = Modifier
//                .fillMaxWidth()
//                .wrapContentHeight()
//                .padding(16.dp),
//            shape = RoundedCornerShape(16.dp),
//            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
//            colors = CardDefaults.cardColors(containerColor = Color.White)
//        ) {
//            Column(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(16.dp)
//            ) {
//                Text(
//                    text = "Configure Sprint",
//                    style = MaterialTheme.typography.titleLarge.copy(
//                        fontWeight = FontWeight.Bold,
//                        color = Color.DarkGray
//                    ),
//                    modifier = Modifier.padding(bottom = 8.dp)
//                )
//
//                TextField(
//                    value = intervalValue,
//                    onValueChange = { intervalValue = it },
//                    label = { Text("Interval in millis") },
//                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(bottom = 8.dp),
//                    colors = TextFieldDefaults.textFieldColors(
//                        containerColor = Color.White,
//                        focusedIndicatorColor = Color.Blue,
//                        unfocusedIndicatorColor = Color.LightGray
//                    )
//                )
//
//                TextField(
//                    value = iterationsValue,
//                    onValueChange = { iterationsValue = it },
//                    label = { Text("Iterations (leave empty for indeterminate)") },
//                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(bottom = 8.dp),
//                    colors = TextFieldDefaults.textFieldColors(
//                        containerColor = Color.White,
//                        focusedIndicatorColor = Color.Blue,
//                        unfocusedIndicatorColor = Color.LightGray
//                    )
//                )
//
//                Spacer(modifier = Modifier.height(8.dp))
//
//                ToggleButton(
//                    checked = isDispatchOnMainEnabled,
//                    onCheckedChange = { isDispatchOnMainEnabled = it },
//                    text = { Text("Dispatch on Main") }
//                )
//
//                if (sprintStatus == Sprint.SprintStatus.STOP) {
//                    Spacer(modifier = Modifier.height(8.dp))
//                    ToggleButton(
//                        checked = isCountdownEnabled,
//                        onCheckedChange = { isCountdownEnabled = it },
//                        text = { Text("Enable Countdown") }
//                    )
//                }
//
//                Spacer(modifier = Modifier.height(16.dp))
//
//                Row(
//                    modifier = Modifier.fillMaxWidth(),
//                    horizontalArrangement = Arrangement.SpaceBetween
//                ) {
//                    if (sprintStatus != Sprint.SprintStatus.STOP) {
//                        ActionButtons(
//                            text = if (sprintStatus == Sprint.SprintStatus.PAUSE) "Resume" else "Pause",
//                            onClick = {
//                                if (sprint.isSprintPaused()) {
//                                    if (sprint.resume()) {
//                                        Log.d("SPRINT_TAG", "Sprint resume successful")
//                                        val logItem = LogItem(
//                                            logTime = System.currentTimeMillis(),
//                                            title = "Sprint ${LogType.RESUME.name}",
//                                            message = null,
//                                            logType = LogType.RESUME
//                                        )
//                                        onEvent?.invoke(logItem)
//                                    } else {
//                                        Log.d("SPRINT_TAG", "Sprint not resumed")
//                                    }
//                                } else {
//                                    if (
//                                        sprint.pause()
//                                    ) {
//                                        Log.d("SPRINT_TAG", "Sprint pause successful")
//                                        val logItem = LogItem(
//                                            logTime = System.currentTimeMillis(),
//                                            title = "Sprint ${LogType.PAUSE.name}",
//                                            message = "Pause At ${sprint.pauseAt()}",
//                                            logType = LogType.PAUSE
//                                        )
//                                        onEvent?.invoke(logItem)
//                                    } else {
//                                        Log.d("SPRINT_TAG", "Sprint not paused")
//                                    }
//                                }
//                                sprintStatus = sprint.getSprintStatus()
//                            }
//                        )
//                        ActionButtons(
//                            text = "Stop",
//                            onClick = {
//                        if (sprint.stop()) {
//                            Log.d("SPRINT_TAG", "Sprint Stopped!")
//                            val logItem = LogItem(
//                                logTime = System.currentTimeMillis(),
//                                title = "Sprint ${LogType.STOP.name}",
//                                message = null,
//                                logType = LogType.STOP
//                            )
//                            onEvent?.invoke(logItem)
//
//                        }
//                        sprintStatus = sprint.getSprintStatus()
//                    }
//                        )
//                        ActionButtons(
//                            text = "Update",
//                            onClick = {
//
//                            try {
//                                if (intervalValue.isNullOrEmpty()) {
//                                    var message = "Cannot Update Interval"
//                                    val logItem = LogItem(
//                                        logTime = System.currentTimeMillis(),
//                                        title = LogType.UPDATE.name,
//                                        message = message,
//                                        logType = LogType.EXCEPTION
//                                    )
//                                    onEvent?.invoke(logItem)
//                                    return@ActionButtons
//                                }else{
//                                if (sprint.updateInterval(intervalValue.toLong())) {
//                                    Log.d(
//                                        "SPRINT_TAG",
//                                        "onUpdateClick update Interval : ${intervalValue.toLong()}"
//                                    )
//                                    var message = "New Interval : ${intervalValue.toLong()}"
//                                    val logItem = LogItem(
//                                        logTime = System.currentTimeMillis(),
//                                        title = LogType.UPDATE.name,
//                                        message = message,
//                                        logType = LogType.UPDATE
//                                    )
//                                    onEvent?.invoke(logItem)
//                                }
//                                /*else{
//                                    var message = "Cannot Update Interval : ${intervalValue.toLong()}"
//                                    val logItem = LogItem(
//                                        logTime = System.currentTimeMillis(),
//                                        title = LogType.UPDATE.name,
//                                        message = message,
//                                        logType = LogType.UPDATE
//                                    )
//                                    onEvent?.invoke(logItem)
//                                }*/
//                                }
//                            } catch (e: Exception) {
//                                Log.d("SPRINT_TAG", "onUpdateInterval Exception : ${e.message}")
//                                var message = "Exception on update Interval : ${e.message}"
//                                val logItem = LogItem(
//                                    logTime = System.currentTimeMillis(),
//                                    title = LogType.EXCEPTION.name,
//                                    message = message,
//                                    logType = LogType.EXCEPTION
//                                )
//                                onEvent?.invoke(logItem)
//
//                            }
//                            try {
//                                val iterationVal:Long? = if(iterationsValue.isNullOrEmpty()){
//                                    null
//                                }else{
//                                    iterationsValue.toLong()
//                                }
//                                if (sprint.updateIterations(iterationVal)) {
//                                    Log.d(
//                                        "SPRINT_TAG",
//                                        "onUpdateClick update Iterations : ${iterationVal}"
//                                    )
//                                    var message = "New Iteration : ${iterationVal}"
//                                    val logItem = LogItem(
//                                        logTime = System.currentTimeMillis(),
//                                        title = LogType.UPDATE.name,
//                                        message = message,
//                                        logType = LogType.UPDATE
//                                    )
//                                    onEvent?.invoke(logItem)
//                                }/*else{
//                                    var message = "Cannot Update Iteration : ${iterationVal}"
//                                    val logItem = LogItem(
//                                        logTime = System.currentTimeMillis(),
//                                        title = LogType.UPDATE.name,
//                                        message = message,
//                                        logType = LogType.UPDATE
//                                    )
//                                    onEvent?.invoke(logItem)
//                                }*/
//                            } catch (e: Exception) {
//                                Log.d("SPRINT_TAG", "onUpdateIteration Exception : ${e.message}")
//                                var message = "Exception on update Iteration : ${e.message}"
//                                val logItem = LogItem(
//                                    logTime = System.currentTimeMillis(),
//                                    title = LogType.EXCEPTION.name,
//                                    message = message,
//                                    logType = LogType.EXCEPTION
//                                )
//                                onEvent?.invoke(logItem)
//                            }
//                            if (sprint.updateDispatch(isDispatchOnMainEnabled)) {
//                                Log.d("SPRINT_TAG", "dispatcher change successful")
//                                var message = "Dispatch on Main : $isDispatchOnMainEnabled"
//                                val logItem = LogItem(
//                                    logTime = System.currentTimeMillis(),
//                                    title = LogType.UPDATE.name,
//                                    message = message,
//                                    logType = LogType.UPDATE
//                                )
//                                onEvent?.invoke(logItem)
//                            }
//                            sprintStatus = sprint.getSprintStatus()
//                        }
//                        )
//                    } else {
//                        ActionButtons(
//                            text = "Start",
//                            onClick = {
//
//                                try {
//                                    if (intervalValue.isNullOrEmpty()) {
//                                        var message = "Cannot Start Interval is Empty"
//                                        val logItem = LogItem(
//                                            logTime = System.currentTimeMillis(),
//                                            title = LogType.START.name,
//                                            message = message,
//                                            logType = LogType.EXCEPTION
//                                        )
//                                        onEvent?.invoke(logItem)
//                                        return@ActionButtons
//                                    } else {
//                                        val iterationVal: Long? =
//                                            if (iterationsValue.isNullOrEmpty()) {
//                                                null
//                                            } else {
//                                                iterationsValue.toLong()
//                                            }
//                                        if (sprint.start(
//                                                Sprint.Config(
//                                                    intervalValue.toLong(),
//                                                    iterationVal,
//                                                    isCountdownEnabled,
//                                                    isDispatchOnMainEnabled
//                                                ), onRoundStart = round
//                                            )
//                                        ) {
//                                            sprintStatus = sprint.getSprintStatus()
//                                            Log.d("SPRINT_TAG", "Sprint Start successful")
//                                            val logItem = LogItem(
//                                                logTime = System.currentTimeMillis(),
//                                                title = "Sprint ${LogType.START.name}",
//                                                message = null,
//                                                logType = LogType.START
//                                            )
//                                            onEvent?.invoke(logItem)
//                                        }
//                                    }
//
//                                } catch (e: Exception) {
//                                    Log.d("SPRINT_TAG", "onStartClick Exception : ${e.message}")
//                                    var message = "Exception on Start : ${e.message}"
//                                    val logItem = LogItem(
//                                        System.currentTimeMillis(),
//                                        LogType.EXCEPTION.name,
//                                        message,
//                                        LogType.EXCEPTION
//                                    )
//                                    onEvent?.invoke(logItem)
//
//                                }
//                            }
//                        )
//                    }
//                }
//            }
//        }
//    }
//}

@Composable
fun ActionButtons(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .padding(4.dp)
            .height(48.dp)
            .widthIn(min = 120.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFBBDEFB)) // Light Blue
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        )
    }
}


@Composable
fun CardViewContainer(
    modifier: Modifier = Modifier,
    content: @Composable (Modifier) -> Unit
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        )
    ) {
        Box(
            modifier = Modifier
                .wrapContentHeight()
                .padding(8.dp)
        ) {
            content(Modifier.fillMaxSize())
        }
    }
}

//
//@Composable
//fun ToggleButton(
//    checked: Boolean,
//    onCheckedChange: (Boolean) -> Unit,
//    text: @Composable () -> Unit
//) {
//    Row(
//        verticalAlignment = Alignment.CenterVertically,
//        horizontalArrangement = Arrangement.spacedBy(4.dp)
//    ) {
//        Switch(
//            checked = checked,
//            onCheckedChange = { onCheckedChange(it) },
//            modifier = Modifier.alignByBaseline()
//        )
//        text()
//    }
//}
//
//
//@Composable
//fun SingleButton(
//    text: String,
//    onClick: () -> Unit
//) {
//    Button(
//        onClick = onClick,
//        modifier = Modifier
//            .padding(horizontal = 2.dp)
//
//    ) {
//        Text(text)
//    }
//}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StyledTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(
                label,
                style = MaterialTheme.typography.bodyMedium.copy(color = Color(0xFF1976D2))
            ) // Medium blue for the label
        },
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color(0xFFE3F2FD), // Light blueish background for the text field
            focusedIndicatorColor = Color(0xFF1976D2), // Medium blue for focus
            unfocusedIndicatorColor = Color(0xFF64B5F6), // Softer blue for unfocused
            cursorColor = Color(0xFF1976D2) // Medium blue for the cursor
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun StyledToggleButton(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    text: String
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color(0xFF1976D2), // Medium blue for the switch thumb
                uncheckedThumbColor = Color(0xFF64B5F6), // Softer blue for unchecked
                checkedTrackColor = Color(0xFFBBDEFB), // Light blue track
                uncheckedTrackColor = Color(0xFFE0E0E0) // Gray for the track when unchecked
            )
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge.copy(
                color = Color(0xFF1976D2), // Medium blue text for toggle
                fontWeight = FontWeight.SemiBold
            )
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SingleButton(
    text: String,
    onClick: () -> Unit,
    backgroundColor: Color = Color(0xFF64B5F6), // Softer blue for buttons
    textColor: Color = Color.White
) {
    val keyboardController = LocalSoftwareKeyboardController.current // Get the keyboard controller

    Button(
        onClick = {
            // Hide the keyboard before executing the onClick action
            keyboardController?.hide()
            onClick() // Call the provided onClick action
        },
        colors = ButtonDefaults.buttonColors(containerColor = backgroundColor),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.padding(8.dp)
    ) {
        Text(
            text,
            color = textColor,
            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
        )
    }
}



