package com.finance.android.walletwise.ui.activity


import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme

import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf

import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.finance.android.walletwise.R
import com.finance.android.walletwise.WalletWiseDestination
import com.finance.android.walletwise.model.Category.CategoryUIState
import com.finance.android.walletwise.ui.AppViewModelProvider

import com.finance.android.walletwise.ui.viewmodel.TransactionsScreenViewModel
import com.finance.android.walletwise.model.Transaction.Transaction
import com.finance.android.walletwise.ui.viewmodel.CategoryViewModel


import java.time.format.DateTimeFormatter



//Click có vẻ fail-->fix
//@Preview(showBackground = true)
//@Composable
//fun PreviewListExpenseScreen() {
//    ListExpenseScreen(
//        onExpenseClick = { expense ->
//            // Điều này sẽ chỉ in ra một log khi một expense được click trong preview
//            println("Expense clicked: $expense")
//        }
//    )
//}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListExpenseScreen(viewModel: TransactionsScreenViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory= AppViewModelProvider.Factory),
                      navController: NavController) {
    val transactionScreenUiState by viewModel.transactionsScreenUiState.collectAsState()
    val transactionsScreenUiStateToday by viewModel.transactionsScreenUiStateToday.collectAsState()
    val transactionsScreenUiStateWeek by viewModel.transactionsScreenUiStateWeek.collectAsState()
    val transactionsScreenUiStateMonth by viewModel.transactionsScreenUiStateMonth.collectAsState()
    val transactionsScreenUiStateYear by viewModel.transactionsScreenUiStateYear.collectAsState()

    Box(modifier = Modifier
        .fillMaxSize()
        .background(
            Color(0xFFFFF7F2)
        )){
        Column() {
            var selectedCategory by remember { mutableStateOf("All") }
            Column {
                timedropdown(selectedCategory = selectedCategory) { category ->
                    selectedCategory = category
                }
            }

            Spacer(modifier = Modifier.height(1.dp))

            Text(text = selectedCategory, color = Color(0xFF0D0E0F), fontSize = 18.sp, fontWeight =FontWeight.SemiBold,
                modifier = Modifier.padding(20.dp))
            if (transactionScreenUiState.transactionList.isEmpty()) {
                Text(
                    text = "No Items",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(15.dp)
                )
            } else {
                ExpenseList(
                    transactionList =
                    if(selectedCategory.equals("Today")){
                        transactionsScreenUiStateToday.transactionList
                    }
                    else if(selectedCategory.equals("Week")){
                        transactionsScreenUiStateWeek.transactionList
                    }
                    else if(selectedCategory.equals("Month")){
                        transactionsScreenUiStateMonth.transactionListt
                    }
                    else if(selectedCategory.equals("Year")){
                        transactionsScreenUiStateYear.transactionList
                    }
                    else{
                        transactionScreenUiState.transactionList
                    },
                    navController = navController)
            }
        }
    }

}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun timedropdown(selectedCategory: String, onCategorySelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    Column {
        Box(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(0.35f)
                .border(
                    width = 2.5.dp,
                    color = Color(0xFFF1F1FA),
                    shape = RoundedCornerShape(25.dp)
                )
                .clip(RoundedCornerShape(25.dp)),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .fillMaxWidth()
                    .clickable { expanded = !expanded }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.img_9),
                    contentDescription = "DropDown",
                    tint = Color(0xFF7F3DFF)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = selectedCategory,
                    color = Color(0xFF212325)
                )
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    painter = painterResource(id =  R.drawable.img_9 ),
                    contentDescription = "DropDown",
                    tint = Color(0xFF7F3DFF)
                )
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.background(Color(0xFFFCFCFC))
            ) {
                timeitems.forEach { category ->
                    DropdownMenuItem(
                        onClick = {
                            onCategorySelected(category)
                            expanded = false
                        },
                        text = { Text(text = category, color = Color.Black) }
                    )
                }
            }
        }
    }
}
@Composable
fun ExpenseList(transactionList: List<Transaction>,navController: NavController) {
    LazyColumn(contentPadding = PaddingValues(bottom = 80.dp)) {
        items(items = transactionList, key = { it.id }) { item ->
            TransactionCard(transaction=item,navController = navController)
        }
    }
}

//@Composable
//fun getIcon(iconName: String): ImageVector {
//    return when (iconName) {
//        "ShoppingCart" -> Icons.Default.ShoppingCart
//        "Home" -> Icons.Default.Home
//        else -> Icons.Default.ShoppingCart // default icon if none match
//    }
//}
@Composable
fun TransactionCard(transaction: Transaction,
                    categoryViewModel: CategoryViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory = AppViewModelProvider.Factory),
                    navController: NavController
) {
    LaunchedEffect(Unit) {
        categoryViewModel.getAllCategories()
    }

    val categoryListState by categoryViewModel.expenseCategories.collectAsState()
    // Debug log to check category list state
    Log.d("TransactionCard", "Category List: ${categoryListState.joinToString { it.name }}")
    val selectedCategory = categoryListState.find { it.id == transaction.idCategory }
    // Debug log to check selected category
    Log.d("TransactionCard", "Selected Category: $selectedCategory")
    val selectedCategoryName = selectedCategory?.name ?: "Select a Category"



    val formattedDate by remember {
        derivedStateOf {
            DateTimeFormatter.ofPattern("MMM dd, yyyy").format(transaction.date)
        }
    }
    val formattedTime by remember {
        derivedStateOf {
            DateTimeFormatter.ofPattern("hh:mm").format(transaction.time)
        }
    }
    Box(
        modifier = Modifier
            .padding(10.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Color(0xFFFFF6F0))
            .clickable {
                if (transaction.type.equals("Expense")) {
                    navController.navigate("${TransactionEditDestination.route}/${transaction.id}")
                } else {
                    navController.navigate("${IncomeTransactionEditDestination.route}/${transaction.id}")
                }
            }
    )
    {
        if (transaction.type.equals("Expense")) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        contentAlignment = Alignment.Center, modifier = Modifier
                            .clip(RoundedCornerShape(15.dp))
                            .size(75.dp)
                            .background(
                                if (selectedCategoryName.equals("Shopping")) {
                                    Color(0xFFFCEED4)
                                } else if (selectedCategoryName.equals("Food")) {
                                    Color(0xFFFDD5D7)
                                } else if (selectedCategoryName.equals("Entertainment")) {
                                    Color(0xFFB6E7E7)
                                } else {
                                    Color(0xFFEEE5FF)
                                }
                            )
                            .padding(5.dp)
                    ) {
                        Icon(
                            painter = painterResource(
                                id =
                                if (selectedCategoryName.equals("Shopping")) {
                                    R.drawable.shopping
                                } else if (transaction.idCategory.equals("Food")) {
                                    R.drawable.img_11
                                } else if (transaction.idCategory.equals("Entertainment")) {
                                    R.drawable.entertain
                                } else {
                                    R.drawable.otherss
                                }
                            ), contentDescription = "Grocery",
                            modifier = Modifier.size(40.dp),
                            tint =
                            if (transaction.idCategory.equals("Shopping")) {
                                Color(0xFFFCAC12)
                            } else if (transaction.idCategory.equals("Food")) {
                                Color(0xFFFD3C4A)
                            } else if (transaction.idCategory.equals("Entertainment")) {
                                Color(0xFF11808F)
                            } else {
                                Color(0xFF7F3DFF)
                            }
                        )
                    }
                    Spacer(modifier = Modifier.width(5.dp))
                    Column(modifier = Modifier.padding(10.dp)) {
                        Text(
                            text = selectedCategoryName,
                            fontSize = 18.sp,
                            color = Color(0xFF292B2D)
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = transaction.description,
                            fontSize = 15.sp,
                            color = Color(0xFF91919F),
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1,
                            modifier=Modifier.fillMaxWidth(0.75f)
                        )
                    }
                }
                Column(
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = transaction.amount.toString(), fontSize = 18.sp,
                        color =
                        Color(0xFFFD3C4A)

                    )
                    Spacer(modifier = Modifier.height(3.dp))
                    Row() {
                        Text(text = formattedTime, fontSize = 13.sp, color = Color(0xFF91919F))
                    }
                }
            }
        } else {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        contentAlignment = Alignment.Center, modifier = Modifier
                            .clip(RoundedCornerShape(15.dp))
                            .size(75.dp)
                            .background(
                                if (transaction.idCategory.equals("Salary")) {
                                    Color(0xFFCFFAEA)
                                } else if (transaction.idCategory.equals("Gifts")) {
                                    Color(0xFFBDDCFF)
                                } else {
                                    Color(0xFFEEE5FF)
                                }
                            )
                            .padding(5.dp)
                    ) {
                        Icon(
                            painter = painterResource(
                                id =
                                if (transaction.idCategory.equals("Salary")) {
                                    R.drawable.salary
                                } else if (transaction.idCategory.equals("Gifts")) {
                                    R.drawable.gifts
                                } else {
                                    R.drawable.otherss
                                }

                            ), contentDescription = "Grocery",
                            modifier = Modifier.size(40.dp),
                            tint =
                            if (transaction.idCategory.equals("Salary")) {
                                Color(0xFF00A86B)
                            } else if (transaction.idCategory.equals("Gifts")) {
                                Color(0xFF0077FF)
                            } else {
                                Color(0xFF7F3DFF)
                            }
                        )
                    }
                    Spacer(modifier = Modifier.width(5.dp))
                    Column(modifier = Modifier.padding(10.dp)) {
                        Text(
                            text = selectedCategoryName,
                            fontSize = 18.sp,
                            color = Color(0xFF292B2D)
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = transaction.description,
                            fontSize = 15.sp,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1,
                            color = Color(0xFF91919F),
                            modifier=Modifier.fillMaxWidth(0.75f)
                        )
                    }
                }
                Column(
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = transaction.amount.toString(), fontSize = 18.sp,
                        color =
                        Color(0xFF00A86B)

                    )
                    Spacer(modifier = Modifier.height(3.dp))
                    Row() {
                        Text(text = formattedTime, fontSize = 13.sp, color = Color(0xFF91919F))
                    }
                }

            }

        }
    }
}



val timeitems = listOf("All", "Today", "Week", "Month", "Year")