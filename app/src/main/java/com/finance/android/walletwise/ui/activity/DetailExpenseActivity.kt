package com.finance.android.walletwise.ui.activity

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold

import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.finance.android.walletwise.ui.fragment.NormalTextField
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.finance.android.walletwise.R
import com.finance.android.walletwise.model.Expense



@Preview(showBackground = true)
@Composable
fun PreviewDetailExpenseScreen() {
    val sampleExpense = Expense(
        id = 1,
        amount = 90.000,
        categoryName = "House & Utilities",
        description = "Rent house",
        date = "15/05/2024",
        type = "Expense"
    )
    DetailExpenseScreen(expense = sampleExpense)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailExpenseScreen(
    expense: Expense,
    onBackClick: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Transaction detail", fontWeight = FontWeight.Bold) },
                actions = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.Close, contentDescription = "Back")
                    }
                }
            )
        },
        content = { innerPadding ->


            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(30.dp)
            ) {



                NormalTextField(
                    value = expense.amount.toString(),
                    onValueChange = {},
                    label = "Amount",
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    isPassword = false,
                    readOnly = true
                )
                NormalTextField(
                    value = expense.categoryName,
                    onValueChange = {},
                    label = "Category name",
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    isPassword = false,
                    readOnly = true
                )
                NormalTextField(
                    value = expense.date,
                    onValueChange = {},
                    label = "Date",
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    isPassword = false,
                    readOnly = true
                )
                NormalTextField(
                    value = expense.description,
                    onValueChange = {},
                    label = "Notes",
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    isPassword = false,
                    readOnly = true
                )
                NormalTextField(
                    value = expense.type,
                    onValueChange = {},
                    label = "Type",
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    isPassword = false,
                    readOnly = true
                )
            }
        }
    )
}

