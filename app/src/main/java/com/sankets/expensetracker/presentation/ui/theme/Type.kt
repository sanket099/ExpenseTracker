package com.sankets.expensetracker.presentation.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.sankets.expensetracker.R

// Set of Material typography styles to start with

val fonts = FontFamily(
    Font(R.font.inter_regular, FontWeight.Normal),
    Font(R.font.inter_medium, weight = FontWeight.Bold),
    Font(R.font.inter_medium, weight = FontWeight.SemiBold),
    Font(R.font.inter_light, weight = FontWeight.Thin),
)
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Normal,
    ),
    body2 = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.SemiBold,
    ),

    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)