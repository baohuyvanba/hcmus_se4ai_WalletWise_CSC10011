package com.finance.android.walletwise.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.finance.android.walletwise.ui.fragment.NormalTextField
import com.finance.android.walletwise.ui.fragment.NormalButton
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.finance.android.walletwise.model.Expense
import androidx.navigation.NavController
import com.finance.android.walletwise.R
import com.finance.android.walletwise.ui.theme.*

const val EXPENSE_SCREEN_SUCCESS_CLICK_ITEM_TEST_TAG = "EXPENSE_SCREEN_SUCCESS_CLICK_ITEM_TEST_TAG"


//Click có vẻ fail-->fix
@Preview(showBackground = true)
@Composable
fun PreviewListExpenseScreen() {
    ListExpenseScreen(
        onExpenseClick = { expense ->
            // Điều này sẽ chỉ in ra một log khi một expense được click trong preview
            println("Expense clicked: $expense")
        }
    )
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListExpenseScreen(onExpenseClick: (expense: Expense) -> Unit) {
    val sampleExpenses = listOf(
        Expense(1, 70.0, "Shopping", "Groceries", "01/01/2022", "expense"),
        Expense(2, 150.0, "House", "Rent", "01/01/2022", "expense")
    )
    Scaffold(
        //Fix
        topBar = {
            TopAppBar(
                title = { Text(text = "View Expenses", fontWeight = FontWeight.Bold) },
            )
        },
        bottomBar = {
            BottomAppBar(
                contentColor = Color.Black
            ) {
                IconButton(onClick = { /* Handle home click */ }) {
                    Icon(imageVector = Icons.Default.Home, contentDescription = "Home")
                }
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = { /* Handle add click */ }) {
                    Icon(imageVector = Icons.Default.Settings, contentDescription = "Setting")
                }
            }
        }
    ) { innerPadding ->
        ExpenseList(expenses = sampleExpenses, modifier = Modifier.padding(innerPadding), onExpenseClick = onExpenseClick)
    }
}

@Composable
fun ExpenseList(expenses: List<Expense>,modifier: Modifier = Modifier,onExpenseClick: (expense: Expense) -> Unit) {
    LazyColumn(modifier = modifier) {
        items(expenses, key = { it.id }) { expense ->
            ExpenseCard(expense = expense, onExpenseClick = onExpenseClick)
        }
    }
}

@Composable
fun getIcon(iconName: String): ImageVector {
    return when (iconName) {
        "ShoppingCart" -> Icons.Default.ShoppingCart
        "Home" -> Icons.Default.Home
        else -> Icons.Default.ShoppingCart // default icon if none match
    }
}
@Composable
fun ExpenseCard(expense: Expense, onExpenseClick: (expense: Expense) -> Unit) {
    val category = expense.category
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable{
                onExpenseClick(expense)
            }.testTag(EXPENSE_SCREEN_SUCCESS_CLICK_ITEM_TEST_TAG.plus("_${expense.id}"))

    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            category?.let {
                Icon(
                    imageVector = getIcon(iconName = it.iconName),
                    contentDescription = null,
                    tint = Color(0xFF6A6AFF),
                    modifier = Modifier.size(40.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = expense.description,
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = expense.date,
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            }
            Text(
                text = expense.amount.toString(),
                color = Color.Black,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}


