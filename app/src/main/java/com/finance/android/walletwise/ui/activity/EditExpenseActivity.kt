package com.finance.android.walletwise.ui.activity

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.finance.android.walletwise.R
import com.finance.android.walletwise.model.Expense
import com.finance.android.walletwise.model.ExpenseCategory
import com.finance.android.walletwise.ui.fragment.NormalTextField

@Preview(showBackground = true)
@Composable
fun EditExpenseScreen(expenseToEdit: Expense? = null,
                      categoryList: List<ExpenseCategory> = emptyList(),
                      editExpenseAndNavigateBack: (Expense) -> Unit) {

    var amount by rememberSaveable { mutableStateOf(expenseToEdit?.amount ?: 0.0) }
    var expanded by rememberSaveable { mutableStateOf(false) }
    val categorySelected = rememberSaveable { mutableStateOf(expenseToEdit?.category?.name ?: "Select a category") }
    var noteExpense by rememberSaveable { mutableStateOf(expenseToEdit?.description ?: "") }
    val menuItems = listOf("Item 1", "Item 2", "Item 3")
    var typeOfTransaction by rememberSaveable { mutableStateOf("")}
    var expenseCategory by remember { mutableStateOf(expenseToEdit?.category?.name ?: "") }



    val onCloseClick: () -> Unit = {
        // Xử lý sự kiện onClick tại đây
        println("Close button clicked!")
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.md_theme_background))
    ) {


        // First Column at the top
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter) // Aligns the column at the top center
                .padding(0.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {

            // Row to hold the title and close button
            Row(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Title
                Text(
                    text = "Transaction detail",
                    fontSize = 22.sp,
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .padding(end = 16.dp)
                )
                // Close Button
                IconButton(
                    onClick = onCloseClick,
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .size(30.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Close Icon"
                    )
                }
            }
            // divider
            Column(
                verticalArrangement = Arrangement.Center,
                modifier =  Modifier
                    .padding(1.dp)
                    .fillMaxWidth(),
            ) {
                Divider(
                    color = Color(0xffc4c6d0),
                    modifier = Modifier
                        .fillMaxWidth())
            }
            EditExpense()

        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditExpense() {
    var amount by rememberSaveable { mutableStateOf("") }
    var expanded by rememberSaveable { mutableStateOf(false) }
    val selectedItem = rememberSaveable { mutableStateOf("") }
    var noteExpense by rememberSaveable { mutableStateOf("") }
    val menuItems = listOf("Item 1", "Item 2", "Item 3")
    var typeOfTransaction by rememberSaveable { mutableStateOf("")}


    Box(modifier = Modifier
        .fillMaxSize()
        .padding(10.dp)) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.TopCenter)
                .padding(top = 0.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            NormalTextField(
                value = amount,
                onValueChange = { amount = it },
                label = "Amount",
                modifier = Modifier
                    .padding(top = 30.dp)
                    .fillMaxWidth()
            )
            OutlinedTextField(
                value = selectedItem.value,
                onValueChange = { },
                placeholder = { Text("Category") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp)
                    .clickable { expanded = true },
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                menuItems.forEach { item ->
                    DropdownMenuItem(onClick = {
                        selectedItem.value = item
                        expanded = false
                    }) {
                        Text(text = item)
                    }
                }
            }

            // DatePicker
            NormalTextField(
                value = noteExpense,
                onValueChange = { noteExpense = it },
                label = "Date",
                modifier = Modifier
                    .padding(top = 30.dp)
                    .fillMaxWidth()
            )
            //Note
            NormalTextField(
                value = noteExpense,
                onValueChange = { noteExpense = it },
                label = "Description",
                modifier = Modifier
                    .padding(top = 30.dp)
                    .fillMaxWidth()
            )

            //Type
            NormalTextField(
                value = noteExpense,
                onValueChange = { noteExpense = it },
                label = "Expense",
                modifier = Modifier
                    .padding(top = 30.dp)
                    .fillMaxWidth()
            )


        }
    }
}


fun DropdownMenuItem(onClick: () -> Unit, interactionSource: @Composable () -> Unit) {

}


