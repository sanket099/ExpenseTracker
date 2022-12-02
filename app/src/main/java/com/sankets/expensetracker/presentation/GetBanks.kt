package com.sankets.expensetracker.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.sankets.expensetracker.data.Banks
import com.sankets.expensetracker.domain.util.Constants.BANKS
import com.sankets.expensetracker.presentation.ui.theme.CardColor
import com.sankets.expensetracker.presentation.ui.theme.PrimaryText


@OptIn(ExperimentalFoundationApi::class, ExperimentalGlideComposeApi::class)
@Composable
fun GetBanks(
    viewModel: TransactionViewModel,
    navController: NavController
) {

    var items by remember {
        mutableStateOf(
            listOf(
                Banks(
                    id = 0,
                    name = "HDFC",
                    isSelected = false,
                    icon = "https://w7.pngwing.com/pngs/636/81/png-transparent-hdfc-thumbnail-bank-logos.png"
                ),

                Banks(
                    id = 1,
                    name = "SBI",
                    isSelected = false,
                    icon = "https://upload.wikimedia.org/wikipedia/commons/thumb/c/cc/SBI-logo.svg/2048px-SBI-logo.svg.png"
                ),
                Banks(
                    id = 2,
                    name = "ICICI",
                    isSelected = false,
                    icon = "https://i.pinimg.com/originals/ff/d5/31/ffd531a6a78464512a97848e14506738.png"
                ),
                Banks(
                    id = 3,
                    name = "KOTAK",
                    isSelected = false,
                    icon = "https://companieslogo.com/img/orig/KOTAKBANK.NS-36440c5e.png"
                ),
                Banks(
                    id = 4,
                    name = "AXIS",
                    isSelected = false,
                    icon = "https://i0.wp.com/www.logotaglines.com/wp-content/uploads/2016/08/Axis-Bank-Logo.png"
                ),
                Banks(
                    id = 5,
                    name = "PAYTM",
                    isSelected = false,
                    icon = "https://aniportalimages.s3.amazonaws.com/media/details/pauyau_PlnW2rW.jpg"
                ),
            )
        )
    }

    val banks = items.filter { it.isSelected }
    val set = mutableSetOf<String>()
    banks.forEach { bank ->
        set.add(bank.name)
    }

    Scaffold(
        backgroundColor = backgroundColor,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
//                    viewModel.sharedPrefs.putBalance(AVAILABLE_BALANCE, text.toDouble())
                    viewModel.sharedPrefs.putBanks(BANKS, set)
                    navController.navigate(Screen.HomeScreen.route)
                },
                backgroundColor = Color.White,
            ) {
                Icon(Icons.Filled.Done, "")
            }
        }) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)

        ) {

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Choose Banks",
                color = PrimaryText,
                fontSize = 24.sp,
                textAlign = TextAlign.Start,
            )
            Spacer(modifier = Modifier.height(8.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                content = {
                    items(items.size) { i ->
                        Card(
                            backgroundColor = CardColor,
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.padding(top = 8.dp, end = 8.dp),
                            border = BorderStroke(if(items[i].isSelected) 2.dp else 0.dp, Color.White)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        items = items.mapIndexed { j, item ->
                                            if (i == j) {
                                                item.copy(isSelected = !item.isSelected)
                                            } else item
                                        }
                                    },
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = items[i].name,
                                    modifier = Modifier.align(Alignment.CenterHorizontally),
                                    color = Color.White
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                GlideImage(
                                    model = items[i].icon,
                                    contentDescription = "image",
                                    modifier = Modifier
                                        .size(60.dp)
                                        .align(Alignment.CenterHorizontally)
                                        .padding(10.dp)
                                )
                                Spacer(modifier = Modifier.height(8.dp))
//                            Image(
//                                painter = painterResource(id = items[i].icon),
//                                contentDescription = null,
//                                modifier = Modifier.width(200.dp)
//                            )
                                if (items[i].isSelected) {
                                }

                            }
                        }
                    }

                }
            )

        }

//        LazyColumn(
//            modifier = Modifier
//                .fillMaxSize()
//        ) {
//            items(items.size) { i ->
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .clickable {
//                            items = items.mapIndexed { j, item ->
//                                if (i == j) {
//                                    item.copy(isSelected = !item.isSelected)
//                                } else item
//                            }
//                        }
//                        .padding(16.dp),
//                    horizontalArrangement = Arrangement.SpaceBetween,
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Text(text = items[i].name)
//                    if (items[i].isSelected) {
//                        Icon(
//                            imageVector = Icons.Default.Check,
//                            contentDescription = "Selected",
//                            tint = Color.Green,
//                            modifier = Modifier.size(20.dp)
//                        )
//                    }
//                }
//            }
//        }

    }


    //navController.navigate(Screen.HomeScreen.route)
}