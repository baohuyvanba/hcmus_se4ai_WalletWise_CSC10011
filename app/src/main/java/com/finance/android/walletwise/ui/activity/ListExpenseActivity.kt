package com.finance.android.walletwise.ui.activity


import androidx.compose.foundation.background
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
import androidx.compose.material3.ExperimentalMaterial3Api

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme

import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue

import androidx.compose.runtime.remember

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
import com.finance.android.walletwise.ui.AppViewModelProvider

import com.finance.android.walletwise.ui.viewmodel.TransactionsScreenViewModel
import com.finance.android.walletwise.model.Transaction.Transaction


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
object TransactionEditDestination : WalletWiseDestination {
    override val route = "transaction_edit"

    override val icon: ImageVector = Icons.Default.Home
    const val transactionIdArg = "transactionId"
    val routeWithArgs = "$route/{$transactionIdArg}"
}
object IncomeTransactionEditDestination : WalletWiseDestination {
    override val route = "transaction_editincome"
    override val icon: ImageVector = Icons.Default.Home
    const val transactionIdArg = "transactionId"
    val routeWithArgs = "$route/{$transactionIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListExpenseScreen(viewModel: TransactionsScreenViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory= AppViewModelProvider.Factory),
                      navController: NavController) {
    val transactionScreenUiState by viewModel.transactionsScreenUiState.collectAsState()
    val transactionsScreenUiStateToday by viewModel.transactionsScreenUiStateToday.collectAsState()
    val transactionsScreenUiStateWeek by viewModel.transactionsScreenUiStateWeek.collectAsState()
    val transactionsScreenUiStateMonth by viewModel.transactionsScreenUiStateMonth.collectAsState()
    val transactionsScreenUiStateYear by viewModel.transactionsScreenUiStateYear.collectAsState()

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
        if (transactionScreenUiState.transactionList.isEmpty()) {
            Text(
                text = "No Items",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(15.dp)
            )
        } else
        ExpenseList(transactionList = transactionsScreenUiStateWeek.transactionList,
            navController = navController,
            modifier = Modifier.padding(innerPadding))
    }
}

@Composable
fun ExpenseList(transactionList: List<Transaction>,navController: NavController,modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        items(items = transactionList, key = { it.id }) { item ->
            TransactionCard(transaction=item,navController = navController)
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
fun TransactionCard(transaction: Transaction,
                    navController: NavController
) {

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
                                if (transaction.category.equals("Shopping")) {
                                    Color(0xFFFCEED4)
                                } else if (transaction.category.equals("Food")) {
                                    Color(0xFFFDD5D7)
                                } else if (transaction.category.equals("Entertainment")) {
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
                                if (transaction.category.equals("Shopping")) {
                                    R.drawable.shopping
                                } else if (transaction.category.equals("Food")) {
                                    R.drawable.img_11
                                } else if (transaction.category.equals("Entertainment")) {
                                    R.drawable.entertain
                                } else {
                                    R.drawable.otherss
                                }
                            ), contentDescription = "Grocery",
                            modifier = Modifier.size(40.dp),
                            tint =
                            if (transaction.category.equals("Shopping")) {
                                Color(0xFFFCAC12)
                            } else if (transaction.category.equals("Food")) {
                                Color(0xFFFD3C4A)
                            } else if (transaction.category.equals("Entertainment")) {
                                Color(0xFF11808F)
                            } else {
                                Color(0xFF7F3DFF)
                            }
                        )
                    }
                    Spacer(modifier = Modifier.width(5.dp))
                    Column(modifier = Modifier.padding(10.dp)) {
                        Text(
                            text = transaction.category,
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
                                if (transaction.category.equals("Salary")) {
                                    Color(0xFFCFFAEA)
                                } else if (transaction.category.equals("Gifts")) {
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
                                if (transaction.category.equals("Salary")) {
                                    R.drawable.salary
                                } else if (transaction.category.equals("Gifts")) {
                                    R.drawable.gifts
                                } else {
                                    R.drawable.otherss
                                }

                            ), contentDescription = "Grocery",
                            modifier = Modifier.size(40.dp),
                            tint =
                            if (transaction.category.equals("Salary")) {
                                Color(0xFF00A86B)
                            } else if (transaction.category.equals("Gifts")) {
                                Color(0xFF0077FF)
                            } else {
                                Color(0xFF7F3DFF)
                            }
                        )
                    }
                    Spacer(modifier = Modifier.width(5.dp))
                    Column(modifier = Modifier.padding(10.dp)) {
                        Text(
                            text = transaction.category,
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