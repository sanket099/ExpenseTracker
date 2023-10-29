package com.sankets.expensetracker.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.sankets.expensetracker.BuildConfig
import com.sankets.expensetracker.domain.util.Constants
import com.sankets.expensetracker.domain.util.Constants.BANKS
import com.sankets.expensetracker.presentation.ui.theme.Background
import com.sankets.expensetracker.presentation.ui.theme.PrimaryText
import com.sankets.expensetracker.presentation.ui.theme.SecondaryText
import com.sankets.expensetracker.presentation.ui.theme.fonts

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ExpenseHome(
    viewModel: TransactionViewModel,
    navController: NavController,
//    balance: String?
) {

    val options = viewModel.sharedPrefs.getBanks(BANKS)?.toMutableList()
    options?.add(Constants.ALL)
    var expanded by rememberSaveable { mutableStateOf(false) }
    var selectedOptionText by rememberSaveable {
        if(options!!.isNotEmpty()){
            mutableStateOf(Constants.ALL)
        }
        else{
            mutableStateOf("")
        }

    }

    Scaffold(
        backgroundColor = Background,
//        floatingActionButton = {
//            FloatingActionButton(
//                onClick = {
//                    navController.navigate(Screen.DetailTransactionScreen.route)
//                },
//                backgroundColor = Color.White,
//            ) {
//                Icon(Icons.Filled.Info, "")
//            }
//        }
    ) {

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Top
//                    .background(Background)

            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = "Transactions",
                        color = PrimaryText,
                        fontSize = 24.sp,
                        textAlign = TextAlign.Center,
                        fontFamily = fonts,
                        fontWeight = FontWeight.Bold,

                        )

                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = {
                            expanded = !expanded
                        },
                        modifier = Modifier
                            .width(150.dp)

                    ) {
                        TextField(
                            readOnly = true,
                            value = selectedOptionText,
                            onValueChange = { },
                            label = {
                                Text(
                                    text = "Select Bank",
                                    color = PrimaryText,
                                    fontFamily = fonts,
                                    fontWeight = FontWeight.Bold,
                                )
                            },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(
                                    expanded = expanded
                                )
                            },
                            colors = TextFieldDefaults.textFieldColors(
                                textColor = PrimaryText,
                                focusedIndicatorColor = PrimaryText,
                                unfocusedIndicatorColor = SecondaryText,
                            )
                        )
                        ExposedDropdownMenu(
                            expanded = expanded,
                            modifier = Modifier
                                .background(Background),
                            onDismissRequest = {
                                expanded = false
                            }
                        ) {
                            options?.forEach { selectionOption ->
                                DropdownMenuItem(
                                    onClick = {
                                        selectedOptionText = selectionOption
                                        expanded = false
                                        viewModel.loadTransactionInfo(bankState = selectedOptionText)

                                    }
                                ) {
                                    Text(
                                        text = selectionOption,
                                        color = PrimaryText,
                                        fontFamily = fonts,
                                        fontWeight = FontWeight.Normal,
                                    )
                                }
                            }
                        }
                    }
                }

                viewModel.state.listTransactions?.let {
                    if (it.isEmpty()) {
                        Text(
                            text = "No Transactions from this Bank",
                            color = PrimaryText,
                            fontSize = 18.sp,
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(40.dp)
                        )
                    } else {
                        LazyColumn(
                            modifier = Modifier
                                .weight(1f)
                                .padding(bottom = 8.dp, top = 8.dp),
                            content = {

                                items(it) { item ->
                                    ExpenseCard(item = item, navController = navController)
//                        Spacer(modifier = Modifier.height(8.dp))
                                }
                            })
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "v${BuildConfig.VERSION_NAME} Privacy Policy",
                    color = PrimaryText,
                    fontSize = 12.sp,
                    fontFamily = fonts,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(horizontal = 40.dp)
                        .clickable {
                            navController.navigate(Screen.PrivacyPolicyScreen.route)
                        },
                    style = TextStyle(textDecoration = TextDecoration.Underline),
                )

            }
            if (viewModel.state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            viewModel.state.error?.let { error ->
                Text(
                    text = error,
                    color = Color.Red,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.Center),
                    fontFamily = fonts,
                    fontWeight = FontWeight.Normal,
                )
            }
        }

    }


}




