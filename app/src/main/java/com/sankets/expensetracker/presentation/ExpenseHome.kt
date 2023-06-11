package com.sankets.expensetracker.presentation

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.sankets.expensetracker.domain.util.Constants.BANKS
import com.sankets.expensetracker.presentation.ui.theme.Background
import com.sankets.expensetracker.presentation.ui.theme.PrimaryText
import com.sankets.expensetracker.presentation.ui.theme.SecondaryText

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ExpenseHome(
    viewModel: TransactionViewModel,
    navController: NavController,
//    balance: String?
) {
    println("HELLO " + viewModel.sharedPrefs.getBanks(BANKS))

    val options = viewModel.sharedPrefs.getBanks(BANKS)?.toMutableList()
    options?.add("ALL")
    var expanded by rememberSaveable { mutableStateOf(false) }
    var selectedOptionText by rememberSaveable {
        if(options!!.isNotEmpty()){
            mutableStateOf("ALL")
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
                    .fillMaxSize()
//                    .background(Background)

            ) {
                Row(modifier = Modifier.fillMaxWidth().padding(start = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {

                    Text(
                        text = "Transactions",
                        color = PrimaryText,
                        fontSize = 24.sp,
                        textAlign = TextAlign.Center,

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

//                    colors = ExposedDropdownMenuDefaults.textFieldColors()


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
                                        println("Hello select $selectedOptionText")
                                        viewModel.loadTransactionInfo(bankState = selectedOptionText)

                                    }
                                ) {
                                    Text(
                                        text = selectionOption,
                                        color = PrimaryText
                                    )
                                }
                            }
                        }
                    }
                }


//            Text(
//                text = "Balance : Rs ${viewModel.sharedPrefs.getBalance(AVAILABLE_BALANCE)}",
//                textAlign = TextAlign.Center,
//                color = PrimaryText,
//                style = MaterialTheme.typography.body2,
//                fontSize = 20.sp,
//                modifier = Modifier.padding(PaddingValues(start = 16.dp, top =  16.dp))
//            )
//                Spacer(modifier = Modifier.height(16.dp))
                viewModel.state.listTransactions?.let {
                    if (it.isEmpty()) {
                        Text(
                            text = "No Transactions from this Bank",
                            color = PrimaryText,
                            fontSize = 18.sp,
                            modifier = Modifier.align(Alignment.CenterHorizontally).padding(40.dp)
                        )
                    } else {
                        LazyColumn(content = {
                            Log.d("TAG", "onCreate: ${it}")
                            items(it) { item ->
                                ExpenseCard(item = item, navController = navController)
//                        Spacer(modifier = Modifier.height(8.dp))
                            }
                        })
                    }

                }

                Spacer(modifier = Modifier.height(16.dp))

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
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }

    }


}




