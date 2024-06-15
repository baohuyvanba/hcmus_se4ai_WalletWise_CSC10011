package com.finance.android.walletwise

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BadgedBox
import androidx.compose.ui.Alignment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar

import androidx.compose.runtime.collectAsState

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush

import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.unit.sp


import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.finance.android.walletwise.model.Transaction.Transaction
import com.finance.android.walletwise.ui.AppViewModelProvider

import com.finance.android.walletwise.ui.activity.*

//Import UI file
import com.finance.android.walletwise.ui.theme.*

import com.finance.android.walletwise.ui.viewmodel.TransactionsScreenViewModel

data class BottomMenuContent(
    val title: String,
    val route:String,
    @DrawableRes val iconId: Int,
    val badgecount: Int=0
)

//Application start point
class WalletWiseActivity : ComponentActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContent {
            val navController= rememberNavController()

            Navigation(navController = navController)
        }
    }
}


@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Navigation(navController: NavHostController){
    NavHost(navController = navController, startDestination = "Home"){

        composable("Home"){
            var expanded by remember { mutableStateOf(false) }
            Scaffold(bottomBar = {
                bottommenu(items = listOf(
                    BottomMenuContent("Home","Home",R.drawable.ic_circle_filled),
                    BottomMenuContent("Transactions","Transactions",R.drawable.otherss),
                ), navController = navController, onItemClick = {
                    navController.navigate(it.route)
                }, bottomBarState = true)
            },
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = {
                            expanded = !expanded
                            //navController.navigate("ExpenseScreen")
                        },
                        modifier = Modifier.padding(bottom=5.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "Add Transaction",
                            tint = Color.White
                        )
                    }
                    if (expanded){
                        Column {
                            FloatingActionButton(
                                onClick = {
                                    navController.navigate("AddScreen")
                                },
                                modifier = Modifier.padding(bottom=15.dp)
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_remind_event),
                                    contentDescription = "Add Transaction",
                                )
                            }
//                            FloatingActionButton(
//                                onClick = {
//                                    navController.navigate("ExpenseScreen")
//                                },
//                                modifier = Modifier.padding(bottom=5.dp)
//                            ) {
//                                Image(
//                                    painter = painterResource(id = R.drawable.salary),
//                                    contentDescription = "Add Expense",
//                                )
//                            }

                        }

                    }

                },
                content = {
                    homescreen(navController = navController)
                }
            )

        }

        composable("Transactions"){

            Scaffold(topBar ={TransactionAppBar(
                title="Transactions",
                canNavigateBack =navController.previousBackStackEntry != null ,
                navigateUp = { navController.navigateUp() },
                topBarState = true)} ,
                bottomBar = {
                    bottommenu(items = listOf(
                        BottomMenuContent("Home","Home",R.drawable.ic_circle_filled),
                        BottomMenuContent("Transactions","Transactions",R.drawable.otherss),
                    ), navController = navController, onItemClick = {
                        navController.navigate(it.route)
                    }, bottomBarState = true)
                }){
                ListExpenseScreen(navController = navController)
            }

        }

        composable("AddScreen"){
            ScreeneAddExpense(navigateBack = { navController.popBackStack() })
        }

        composable(route = TransactionEditDestination.routeWithArgs,
            arguments = listOf(navArgument(TransactionEditDestination.transactionIdArg){
                type= NavType.IntType
            })
        ){
            Scaffold(
                topBar ={TransactionAppBar(
                title="Edit Transaction",
                canNavigateBack =navController.previousBackStackEntry != null ,
                navigateUp = { navController.navigateUp() },
                topBarState = true)}) {
                EditScreenExpense(navigateBack = { navController.popBackStack() })
            }
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun bottommenu(
    items: List<BottomMenuContent>,
    modifier: Modifier = Modifier,
    activeHighlightColor: Color = Color(0xFF7F3DFF),
    activeTextColor: Color = Color(0xFF7F3DFF),
    inactiveTextColor: Color = Color(0xFFC6C6C6),
    initialSelectedItemIndex: Int = 0,
    navController: NavController,
    onItemClick: (BottomMenuContent) -> Unit,
    bottomBarState: Boolean
) {
    val backstackEntry by navController.currentBackStackEntryAsState()
    AnimatedVisibility(visible = bottomBarState) {
        NavigationBar(
            modifier = modifier,
            containerColor = Color(0xFFFFF6F0),
            tonalElevation = 5.dp
        ) {
            items.forEach { item ->
                val selected = item.route == backstackEntry?.destination?.route
                NavigationBarItem(
                    selected = selected,
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = activeHighlightColor,
                        unselectedIconColor = inactiveTextColor,
                        selectedTextColor = activeTextColor,
                        unselectedTextColor = inactiveTextColor
                    ),
                    onClick = { onItemClick(item) },
                    icon = {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            if (item.badgecount > 0) {
                                BadgedBox(badge = {
                                    Text(text = item.badgecount.toString())
                                }) {
                                    Icon(
                                        painter = painterResource(id = item.iconId),
                                        contentDescription = item.title,
                                        tint = if (selected) activeTextColor else inactiveTextColor,
                                        modifier = Modifier.size(30.dp)
                                    )
                                }
                            } else {
                                Icon(
                                    painter = painterResource(id = item.iconId),
                                    contentDescription = item.title,
                                    tint = if (selected) activeTextColor else inactiveTextColor,
                                    modifier = Modifier.size(30.dp)
                                )
                            }
                            if (selected) {
                                Text(
                                    text = item.title,
                                    color = if (selected) activeTextColor else inactiveTextColor
                                )
                            }
                        }
                    }
                )
            }
        }
    }
}



@Composable
fun homescreen(
    viewModel: TransactionsScreenViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory= AppViewModelProvider.Factory),
    navController: NavController
) {
    val transactionScreenUiState by viewModel.transactionsScreenUiState.collectAsState()
    val transactionsScreenUiStateToday by viewModel.transactionsScreenUiStateToday.collectAsState()
    val transactionsScreenUiStateWeek by viewModel.transactionsScreenUiStateWeek.collectAsState()
    val transactionsScreenUiStateMonth by viewModel.transactionsScreenUiStateMonth.collectAsState()
    val transactionsScreenUiStateYear by viewModel.transactionsScreenUiStateYear.collectAsState()

    var totalIncome = 0.0
    var totalExpense = 0.0
    var time by remember { mutableStateOf("All") }

    when (time) {
        "All" -> transactionScreenUiState.transactionList.forEach { transaction ->
            if (transaction.type == "Expense") {
                totalExpense += transaction.amount
            } else {
                totalIncome += transaction.amount
            }
        }
        "Today" -> transactionsScreenUiStateToday.transactionList.forEach { transaction ->
            if (transaction.type == "Expense") {
                totalExpense += transaction.amount
            } else {
                totalIncome += transaction.amount
            }
        }
        "Week" -> transactionsScreenUiStateWeek.transactionList.forEach { transaction ->
            if (transaction.type == "Expense") {
                totalExpense += transaction.amount
            } else {
                totalIncome += transaction.amount
            }
        }
        "Month" -> transactionsScreenUiStateMonth.transactionListt.forEach { transaction ->
            if (transaction.type == "Expense") {
                totalExpense += transaction.amount
            } else {
                totalIncome += transaction.amount
            }
        }
        "Year" -> transactionsScreenUiStateYear.transactionList.forEach { transaction ->
            if (transaction.type == "Expense") {
                totalExpense += transaction.amount
            } else {
                totalIncome += transaction.amount
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF7F2))
    ) {
        Column {
            time = topbox(totalExpense = totalExpense, totalIncome = totalIncome, time = time, navController = navController)
            Spacer(modifier = Modifier.height(27.dp))
            val x = selecttime(items = listOf("Today", "Week", "Month", "Year"))
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
                    .padding(start = 20.dp, end = 15.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Recent Transaction",
                    fontSize = 18.sp,
                    color = Color(0xff292B2D),
                    fontWeight = FontWeight.SemiBold
                )
            }
            Spacer(modifier = Modifier.height(15.dp))
            Cardlistt(
                transactionList =
                when (x) {
                    0 -> transactionsScreenUiStateToday.transactionList
                    1 -> transactionsScreenUiStateWeek.transactionList
                    2 -> transactionsScreenUiStateMonth.transactionListt
                    else -> transactionsScreenUiStateYear.transactionList
                },
                navController = navController
            )
        }


    }
}
@Composable
fun Cardlistt(
    transactionList: List<Transaction>,
    navController: NavController
){
    LazyColumn(contentPadding = PaddingValues(bottom = 80.dp)){
        items(items = transactionList, key = { it.id }) { item ->
            TransactionCard(transaction=item, navController = navController)
        }
    }
}
@Composable
fun Cardlist() {
    LazyColumn(contentPadding = PaddingValues(bottom = 80.dp)) {
        items((1..5).toList()) {
            TransactionCardd()
        }
    }
}

@Composable
fun TransactionCardd() {
    Box(
        modifier = Modifier
            .padding(10.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Color(0xFFFFF6F0)),
    ) {
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
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .clip(RoundedCornerShape(15.dp))
                        .size(75.dp)
                        .background(Color(0xFFFCEED4))
                        .padding(5.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.gifts),
                        contentDescription = "Grocery",
                        tint = Color(0xFFFCAC12),
                        modifier = Modifier.size(55.dp)
                    )
                }
                Spacer(modifier = Modifier.width(5.dp))
                Column(modifier = Modifier.padding(10.dp)) {
                    Text(text = "Shopping", fontSize = 18.sp, color = Color(0xFF292B2D))
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(text = "Buy some grocery", fontSize = 15.sp, color = Color(0xFF91919F))
                }
            }
            Text(text = "Rs.120", fontSize = 18.sp, color = Color(0xFFFD3C4A))
        }
    }
}


@Composable
fun selecttime(items: List<String>, modifier: Modifier= Modifier,
               activeHighlightColor: Color = Color(0xFFFCEED4),
               activeTextColor: Color = Color(0xFFFCAC12),
               inactiveTextColor: Color = Color(0xFF91919F),
               initialSelectedItemIndex: Int=0): Int{
    var selecteditemindex by remember {
        mutableStateOf(initialSelectedItemIndex)
    }
    Row(horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
    ) {
        items.forEachIndexed{index,item->
            rangeselectoritem(item= item,
                isSelected = index==selecteditemindex,
                activeHighlightColor=activeHighlightColor,
                activeTextColor=activeTextColor,
                inactiveTextColor=inactiveTextColor){
                selecteditemindex=index
            }
        }

    }
    return selecteditemindex
}

@Composable
fun rangeselectoritem(
    item: String,
    isSelected: Boolean = false,
    activeHighlightColor: Color = Color(0xFFFCEED4),
    activeTextColor: Color = Color(0xFFFCAC12),
    inactiveTextColor: Color = Color(0xFFE3E5E5),
    onItemClick: () ->Unit
){
    Row(horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable {
            onItemClick()
        }) {
        Box(contentAlignment = Alignment.Center,
            modifier = Modifier
                .clip(RoundedCornerShape(15.dp))
                .background(
                    if (isSelected) activeHighlightColor
                    else Color.Transparent
                )
                .padding(10.dp)) {
            Text(
                text = item,
                color= if(isSelected) activeTextColor else inactiveTextColor,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun topbox(totalExpense:Double, totalIncome:Double,time: String,navController: NavController): String {
    var timee=""
    Box(
        modifier = Modifier
            .clip(
                RoundedCornerShape(
                    bottomStart = 32.dp,
                    bottomEnd = 32.dp
                )
            )
            .height(312.dp)
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFECD8B2),
                        Color(0x73F8EDD8)
                    )
                )
            )

    ) {
        Column(verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            timee=topnavigation(totalExpense=totalExpense, totalIncome=totalIncome, time=time)

            Text(
                text = "Account Balance",
                fontSize = 17.sp,
                color = Color(0xFF91919F)
            )
            Text(
                text = (totalIncome-totalExpense).toInt().toString(),
                fontSize = 48.sp,
                modifier = Modifier.padding(bottom = 5.dp),
                fontWeight = FontWeight.Bold,
                color = Color(0xFF161719)

            )


            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .padding(15.dp)
                    .fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .height(80.dp)
                        .width(155.dp)
                        .clip(RoundedCornerShape(25.dp))
                        .background(Color(0xFF00A86B))
                        .padding(6.dp)
                        .clickable {
                            navController.navigate("IncomeScreen")
                        }


                ) {
                    Row(
                        horizontalArrangement = Arrangement.Start
                    ) {

                        Image(
                            painter = painterResource(id = R.drawable.ic_analytics_filled),
                            contentDescription = "Profit Icon",
                            modifier = Modifier
                                .padding(10.dp)
                                .size(46.dp)
                        )

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "Income",
                                color = Color(0xFfFCFCFC),
                                fontSize = 18.sp,
                                modifier = Modifier.padding(top = 10.dp)
                            )
                            Text(
                                text = totalIncome.toInt().toString(),
                                color = Color(0xFfFCFCFC),
                                //fontSize = 22.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }


                    }
                }
                Box(
                    modifier = Modifier
                        .height(80.dp)
                        .width(155.dp)
                        .clip(RoundedCornerShape(25.dp))
                        .background(Color(0xFFFD3C4A))
                        .padding(6.dp)
                        .clickable {
                            navController.navigate("ExpenseScreen")
                        }


                ) {
                    Row(
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_remind_event),
                            contentDescription = "Loss Icon",
                            modifier = Modifier
                                .padding(10.dp)
                                .size(46.dp)
                        )

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "Expense",
                                color = Color(0xFfFCFCFC),
                                fontSize = 18.sp,
                                modifier = Modifier.padding(top = 10.dp)
                            )
                            Text(
                                text = totalExpense.toInt().toString(),
                                color = Color(0xFfFCFCFC),
                                //fontSize = 22.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                    }
                }
            }
        }
    }
    return timee
}

@Composable
fun topnavigation(totalExpense: Double,totalIncome: Double,time:String): String {
    var select_time: String=""
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.padding(top=16.dp)
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(25.dp)
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.application_logo),
                contentDescription = "Profile Photo",
                modifier = Modifier
                    .clip(CircleShape)
                    .size(35.dp)
            )

            var selectedCategory by remember { mutableStateOf("All") }

            Column {
                var selected_time= timetopboxdropdown(selectedCategory = selectedCategory) { category ->
                    selectedCategory = category
                }
                select_time=selected_time
            }
            //ShareButton(totalExpense =totalExpense , totalIncome = totalIncome , timee = time )
        }

    }
    return select_time
}

@Composable
fun timetopboxdropdown(selectedCategory: String, onCategorySelected: (String) -> Unit): String {
    var expanded by remember { mutableStateOf(false) }
    TextButton(
        onClick = { expanded = true }) {
//        Icon(
//            painter = painterResource(id = R.drawable.),
//            contentDescription = "Down Arrow",
//            tint = Color(0xFF7B61FF)
//        )
        Text(text = selectedCategory, fontSize = 18.sp, color = Color(0xFF212325))
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(Color(0xFCFCFCFC))
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
    return selectedCategory
}




val timeitems = listOf("All", "Today", "Week", "Month", "Year")


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionAppBar(
    title: String,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    topBarState: Boolean
)  {
    AnimatedVisibility(visible = topBarState) {
        TopAppBar(
            //backgroundColor =Color(colorAppBar),
            title = { Text(title, fontSize = 18.sp)},
            modifier = modifier,
            navigationIcon = {
                if (canNavigateBack) {
                    IconButton(onClick = navigateUp) {
                        Icon(painter = painterResource(id = R.drawable.ic_chat_finance), contentDescription = "Back")
                    }
                }
            }
        )
    }
}