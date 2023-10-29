package com.sankets.expensetracker.presentation

import android.text.format.DateFormat
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.sankets.expensetracker.presentation.ui.theme.Accent
import com.sankets.expensetracker.presentation.ui.theme.Background
import com.sankets.expensetracker.presentation.ui.theme.PrimaryText
import java.util.*

@OptIn(
    ExperimentalFoundationApi::class, ExperimentalGlideComposeApi::class,
    ExperimentalMaterialApi::class, ExperimentalTextApi::class
)
@Composable
fun DetailTransactions(
    viewModel: TransactionViewModel,
    navController: NavController
) {
//    Scaffold(
//        backgroundColor = Background,
//        modifier = Modifier
//            .fillMaxWidth()
//
//    ) {
//
//        Column(
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally,
//            modifier = Modifier
//                .fillMaxWidth()
//        ) {
//
//            val maxDate = viewModel.state.listTransactions?.maxOf { it.date }
//            val minDate = viewModel.state.listTransactions?.minOf { it.date }
//            Log.d("TAG", "DetailTransactions: $maxDate $minDate")
//            val maxDateFloat = maxDate!!.toFloat()
//            val minDateFloat = minDate!!.toFloat()
//            Log.d("TAG", "DetailTransactions: $maxDateFloat $minDateFloat")
//            var sliderPosition by remember { mutableStateOf(minDateFloat.rangeTo(maxDateFloat)) }
//
//            var text by remember { mutableStateOf(viewModel.calculateAmountBetweenDates(minDate, maxDate).toString()) }
//            var textStartDate by remember { mutableStateOf(timeStampToDate(minDate)) }
//            var textEndDate by remember { mutableStateOf(timeStampToDate(maxDate)) }
//
//
//            Card(
//                backgroundColor = Background,
//                shape = RoundedCornerShape(10.dp),
//                modifier = Modifier.padding(10.dp)
//            ) {
//
//                RangeSlider(
//                    steps = (maxDateFloat - minDateFloat).toInt()/86400000,
//                    values = sliderPosition,
//                    onValueChange = { sliderPosition = it },
//                    valueRange = minDateFloat..maxDateFloat,
//                    onValueChangeFinished = {
//                        Log.d("onValueChangeFinished", "DetailTransactions: ${sliderPosition.start} ${sliderPosition.endInclusive}")
//                        text = viewModel.calculateAmountBetweenDates(sliderPosition.start.toLong(), sliderPosition.endInclusive.toLong()).toString()
//                        textStartDate = timeStampToDate(sliderPosition.start.toLong())
//                        textEndDate = timeStampToDate(sliderPosition.endInclusive.toLong())
//                        // launch soe business logic update with the state you hold
//                        // viewModel.updateSelectedSliderValue(sliderPosition)
//                    },
//                    colors = SliderDefaults.colors(
//                        thumbColor = Accent,
//                        activeTrackColor = Accent,
//                        )
//                )
//
//            }
//
//            Text(
//
//                buildAnnotatedString {
//                    append("The net amount is Rs.")
//                    withStyle(
//                        style = SpanStyle(color = Color.Cyan)
//                    ) {
//                        append(text)
//                    }
//                    append("\nbetween\n")
//
//                    withStyle(
//                        style = SpanStyle(color = Color.Cyan)
//                    ) {
//                        append(textStartDate)
//                    }
//                    append(" and ")
//                    withStyle(
//                        style = SpanStyle(color = Color.Cyan)
//                    ) {
//                        append(textEndDate)
//                    }
//                },
//                color = PrimaryText,
//                fontSize = 24.sp,
//                textAlign = TextAlign.Center,
//            )
//
//            LineChart(
//                data = viewModel.getDataForGraph(),
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(30.dp)
//
//            )
//
//
//        }



    // TODO //
//  1. Create Figma Designs
//  2. Change Date to Long
//  3. Create Date Conversion Functions
//  4. Filter slider using Long
//  5. Fix Line Graph


    // Features
    // 1. Get List of Expenses of different Banks -- done
    // 2. Get Net value between dates for different Banks
    // 3. Get Graph of Expenses of different Banks : Date vs Amount
    // 4. Get pie chart of different bank Amounts monthly, weekly, yearly, today

    // There will have to be a stats section


}

fun timeStampToDate(timeStamp: Long): String {
    val calendar = Calendar.getInstance(Locale.ENGLISH)
    calendar.timeInMillis = timeStamp
    val date = DateFormat.format("dd-MM-yyyy", calendar).toString()
    return date
}

