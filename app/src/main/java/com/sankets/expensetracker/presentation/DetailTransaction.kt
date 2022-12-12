package com.sankets.expensetracker.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.sankets.expensetracker.presentation.ui.theme.Accent
import com.sankets.expensetracker.presentation.ui.theme.Background
import com.sankets.expensetracker.presentation.ui.theme.PrimaryText

@OptIn(
    ExperimentalFoundationApi::class, ExperimentalGlideComposeApi::class,
    ExperimentalMaterialApi::class
)
@Composable
fun DetailTransactions(
    viewModel: TransactionViewModel,
    navController: NavController
) {
    Scaffold(
        backgroundColor = Background,
        modifier = Modifier
            .fillMaxWidth()

    ) {

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
        ) {

            var text by remember { mutableStateOf("") }

            Text(
                text = text,
                color = PrimaryText,
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                )

            Card(
                backgroundColor = Background,
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.padding(10.dp)
            ) {
//            LineChart(
//                data = viewModel.getDataForGraph(),
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(30.dp)
//
//            )

                var maxDate = viewModel.state.listTransactions?.maxOf { it.date }
                var minDate = viewModel.state.listTransactions?.minOf { it.date }
                var sliderPosition by remember { mutableStateOf(minDate!!.toFloat().rangeTo(maxDate!!.toFloat())) }

                RangeSlider(

                    values = sliderPosition,
                    onValueChange = { sliderPosition = it },
                    valueRange = 0f..100f,
                    onValueChangeFinished = {
                        text = viewModel.calculateAmount().toString()
                        // launch soe business logic update with the state you hold
                        // viewModel.updateSelectedSliderValue(sliderPosition)
                    },
                    colors = SliderDefaults.colors(
                        thumbColor = Accent,
                        activeTrackColor = Accent,


                    )
                )


            }

        }

    }

    // TODO //
//  1. Create Figma Designs
//  2. Change Date to Long
//  3. Create Date Conversion Functions
//  4. Filter slider using Long
//  5. Fix Line Graph


    // Features
    // 1. Get List of Expenses of differeent Banks
    // 2. Get Net value between dates for different Banks
    // 3. Get Graph of Expenses of different Banks : Date vs Amount
    // 4. Get pie chart of different bank Amounts monthly, weekly, yearly, today

    // There will have to be a stats section


}

