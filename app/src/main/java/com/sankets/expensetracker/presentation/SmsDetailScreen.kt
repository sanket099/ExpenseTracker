package com.sankets.expensetracker.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sankets.expensetracker.R
import com.sankets.expensetracker.presentation.ui.theme.Background
import com.sankets.expensetracker.presentation.ui.theme.CardColor
import com.sankets.expensetracker.presentation.ui.theme.PrimaryText
import com.sankets.expensetracker.presentation.ui.theme.SecondaryText
import com.sankets.expensetracker.presentation.ui.theme.fonts
import kotlin.random.Random


@Composable
fun SmsDetailScreen(
    smsSource: String?,
    smsBody: String?,
    smsDate: String?,
    modifier: Modifier = Modifier
) {
    Scaffold(backgroundColor = Background) {

        Column(
            modifier = modifier
                .fillMaxWidth()
        ) {

            Spacer(modifier = modifier.height(16.dp))
            Text(
                text = "SMS",
                color = PrimaryText,
                fontSize = 24.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                fontFamily = fonts,
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = modifier.height(16.dp))

            //Card
            Card(
                backgroundColor = CardColor,
                shape = RoundedCornerShape(10.dp),
                modifier = modifier
                    .padding(16.dp),

                ) {
                Column(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(30.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = smsSource!!,
                        color = PrimaryText,
                        fontSize = 20.sp,
                        modifier = Modifier.align(Alignment.Start),
                        fontFamily = fonts,
                        fontWeight = FontWeight.Bold,
                    )
                    Spacer(modifier = modifier.height(16.dp))
                    Text(
                        text = smsBody!!,
                        color = PrimaryText,
                        fontSize = 16.sp,
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        fontFamily = fonts,
                        fontWeight = FontWeight.Normal,
                    )
                    Spacer(modifier = modifier.height(16.dp))
                    Text(
                        text = smsDate!!,
                        color = SecondaryText,
                        fontSize = 16.sp,
                        modifier = Modifier.align(Alignment.End),
                        fontFamily = fonts,
                        fontWeight = FontWeight.Light,
                    )

                }

            }

            Spacer(modifier = modifier.height(16.dp))

            Image(
                painterResource(getRandomDoodle()),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize().padding(32.dp)
            )

        }

    }

}
private fun getRandomDoodle() : Int{
    val doodleList = listOf<Int>(R.drawable.doodle, R.drawable.doodle2, R.drawable.doodle3, R.drawable.doodle4, R.drawable.doodle5)
    val index = Random.nextInt(0, doodleList.size)
    return doodleList[index]
}

