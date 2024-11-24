package com.example.pulsegenerator.sprintUI.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pulsegenerator.sprintUI.Model.LogItem
import com.example.pulsegenerator.sprintUI.Model.LogType
import com.example.pulsegenerator.sprintUI.utils.formatDate


@Composable
fun LogPanel(logItemList: List<LogItem>, listState: LazyListState){
    LazyColumn (state = listState){
        items(logItemList.size) {
            LogItemView(logItemList[it])

        }
    }

    // Launch effect to scroll only if not on the last item
    LaunchedEffect(logItemList.size) {
        if (listState.canScrollForward) {
            // Animate scroll to the latest message
            if(!logItemList.isNullOrEmpty()) {
                listState.animateScrollToItem(logItemList.size - 1)
            }
        }
    }
}

//
//@Preview(showBackground = true)
//@Composable
//fun LogItemView(logItem: LogItem?=null){
//    val backgroundColor = when (logItem?.logType) {
//        LogType.START -> Color(0xFFE3F2FD) // Light Blue
//        LogType.ROUND -> Color(0xFFFFF8E1) // Light Yellow
//        LogType.LAST_ROUND -> Color(0xFFFFEBEE) // Light Red
//        LogType.PAUSE -> Color(0xFFF3E5F5) // Light Purple
//        LogType.RESUME -> Color(0xFFE8F5E9) // Light Green
//        LogType.STOP -> Color(0xFFFFF3E0) // Light Orange
//        LogType.UPDATE -> Color(0xFFE0F7FA) // Light Cyan
//        LogType.EXCEPTION -> Color(0xFFFFEBEE) // Light Red (for error-like logs)
//        else -> Color(0xFFF5F5F5) // Default Light Grey
//    }
//
//    CardViewContainer(
//        modifier = Modifier
//            .fillMaxWidth()
//            .wrapContentHeight()
//            .padding(horizontal=16.dp, vertical = 8.dp)
//    ){
//        Row (modifier = Modifier
//            .fillMaxWidth()
//            .wrapContentHeight()){
//           /* Box(modifier = Modifier
//                .width(5.dp)
//                .height()
//                .background(Color.Red)
//            ) {
//
//            }*/
//            Column(modifier = Modifier.wrapContentHeight()) {
//                TextTitle(logItem?.title?:"")
//                logItem?.logTime?.let{
//                    Text(formatDate(it)?:"")
//                }
//                logItem?.message?.let{
//                    Text(it)
//                }
//                logItem?.logType?.let{
//                    Text(it.name)
//                }
//
//            }
//        }
///*        Log.d("LogItem","Log Time : ${formatDate(it.logTime)}\n"+
//                "Title : ${it.title}\n"+
//                "Message : ${it.message}\n"+
//                "Log type : ${it.logType.name}\n")*/
//
//    }
//}

@Preview(showBackground = true)
@Composable
fun LogItemView(logItem: LogItem? = null) {
//    val backgroundColor = when (logItem?.logType) {
//        LogType.START -> Color(0xFFD1C4E9) // Light Lavender
//        LogType.ROUND -> Color(0xFFFFF9C4) // Soft Lemon Yellow
//        LogType.LAST_ROUND -> Color(0xFFFFCDD2) // Warm Pink
//        LogType.PAUSE -> Color(0xFFB2EBF2) // Cool Light Cyan
//        LogType.RESUME -> Color(0xFFC8E6C9) // Fresh Mint Green
//        LogType.STOP -> Color(0xFFFFE0B2) // Warm Apricot Orange
//        LogType.UPDATE -> Color(0xFFDCEDC8) // Soft Lime Green
//        LogType.EXCEPTION -> Color(0xFFFFAB91) // Bright Coral
//        else -> Color(0xFFECEFF1) // Neutral Soft Blue-Grey
//    }

//    val backgroundColor = when (logItem?.logType) {
//        LogType.START -> Color(0xFFD8BFD8) // Light Lavender
//        LogType.ROUND -> Color(0xFFFFF9C4) // Soft Lemon Yellow
//        LogType.LAST_ROUND -> Color(0xFFFFE4E1) // Very Light Pink
//        LogType.PAUSE -> Color(0xFFB2EBF2) // Cool Light Cyan
//        LogType.RESUME -> Color(0xFFC8E6C9) // Light Mint Green
//        LogType.STOP -> Color(0xFFFFE0B2) // Soft Apricot Orange
//        LogType.UPDATE -> Color(0xFFE1F5FE) // Very Light Cyan
//        LogType.EXCEPTION -> Color(0xFFFFD1D1) // Light Coral
//        else -> Color(0xFFF5F5F5) // Neutral Light Grey
//    }

    val backgroundColor = when (logItem?.logType) {
        LogType.START -> Color(0xFFF1E1F2) // Very Light Lavender
        LogType.ROUND -> Color(0xFFFFF8E1) // Very Soft Lemon Yellow
        LogType.LAST_ROUND -> Color(0xFFFFF0F1) // Ultra Light Pink
        LogType.PAUSE -> Color(0xFFE1F5F7) // Very Soft Cyan
        LogType.RESUME -> Color(0xFFE1F8E6) // Very Light Mint Green
        LogType.STOP -> Color(0xFFFFF1E0) // Ultra Light Apricot
        LogType.UPDATE -> Color(0xFFE1F8FF) // Lightest Cyan
        LogType.EXCEPTION -> Color(0xFFFFF1F1) // Very Light Coral
        else -> Color(0xFFF9F9F9) // Almost White Grey
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor) // Set background color
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                TextTitle(logItem?.title ?: "")
                logItem?.logTime?.let {
                    StyledText(formatDate(it) ?: "", style = Typography().bodySmall)
                }
                logItem?.message?.let {
                    StyledText(it, style = Typography().bodyMedium)
                }
                logItem?.logType?.let {
                    StyledText("Type: ${it.name}", style = Typography().bodySmall)
                }
            }
        }
    }
}

@Composable
fun TextTitle(title: String) {
    Text(
        text = title,
        style = Typography().titleLarge.copy(fontWeight = FontWeight.Bold, color = Color.DarkGray),
        textAlign = TextAlign.Start,
        modifier = Modifier.padding(bottom = 8.dp)
    )
}

@Composable
fun StyledText(text: String, style: TextStyle) {
    Text(
        text = text,
        style = style.copy(color = Color.Gray),
        modifier = Modifier.padding(vertical = 2.dp)
    )
}


//@Composable
//fun TextTitle(title:String){
//    Text(text = title,
//        style = Typography().titleLarge,
//        textAlign = TextAlign.Center
//    )
//}