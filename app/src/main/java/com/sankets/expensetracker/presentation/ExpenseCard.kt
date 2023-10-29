package com.sankets.expensetracker.presentation

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.sourceInformation
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.sankets.expensetracker.R
import com.sankets.expensetracker.data.Transaction
import com.sankets.expensetracker.presentation.ui.theme.*
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ExpenseCard(
    modifier: Modifier = Modifier,
    item: Transaction,
    navController : NavController
) {
    Card(
        backgroundColor = CardColor,
        shape = RoundedCornerShape(10.dp),
        modifier = modifier
            .padding(8.dp)
            .selectable(
                selected = true,
                onClick = {
                    val body  = item.msgBody.replace("/","\\")
                    val date = getDate(item.date).replace("/", "\\")
                    val source = item.sourceName.replace("/", "\\")
                    navController.navigate(Screen.SmsScreen.withArgs(source, body, date))
                })
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = item.amount,
                    color = PrimaryText,
                    fontSize = 18.sp,
                    fontFamily = fonts,
                    fontWeight = FontWeight.Normal,
                )
                if (item.isCredited) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_round_trending_up_24),
                        contentDescription = null,
                        modifier = Modifier.size(40.dp),
                        tint = Accent
                    )
                } else {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_round_trending_down_24),
                        contentDescription = null,
                        modifier = Modifier.size(40.dp),
                        tint = AccentSecondary
                    )
                }
            }

            Spacer(modifier = modifier.height(8.dp))
            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = item.sourceName,
                    color = SecondaryText,
                    style = MaterialTheme.typography.body2,
                    fontFamily = fonts,
                    fontWeight = FontWeight.Thin,
                )
                Text(
                    text = getDate(item.date),
                    color = SecondaryText,
                    style = MaterialTheme.typography.body2,
                    fontFamily = fonts,
                    fontWeight = FontWeight.Thin,
                )


            }
        }
    }


}

private fun getDate(dateLong: Long): String {
    val dateVal = Date(dateLong)
    val format = SimpleDateFormat("dd/MM/yy")
    return format.format(dateVal)
}