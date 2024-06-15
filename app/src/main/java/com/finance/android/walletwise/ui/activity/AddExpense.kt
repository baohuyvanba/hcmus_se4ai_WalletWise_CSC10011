package com.finance.android.walletwise.ui.activity

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.InputChip
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.finance.android.walletwise.ui.fragment.NormalTextField
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.finance.android.walletwise.R
import com.finance.android.walletwise.model.Category.Category
import com.finance.android.walletwise.model.Category.CategoryUIState
import com.finance.android.walletwise.ui.fragment.NormalButton
import com.finance.android.walletwise.ui.viewmodel.ExpenseViewModel
import com.finance.android.walletwise.model.Transaction.TransactionUiState
import com.finance.android.walletwise.ui.AppViewModelProvider
import com.finance.android.walletwise.ui.viewmodel.CategoryViewModel


import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter


@Composable
fun ScreeneAddExpense(viewModel: ExpenseViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory= AppViewModelProvider.Factory), navigateBack: () -> Unit){
    AddExpenseSreen(transactionUiState = viewModel.transactionUiState,navigateBack=navigateBack)
}

@Composable
fun AddExpenseSreen(transactionUiState: TransactionUiState,
                    expenseviewModel: ExpenseViewModel= androidx.lifecycle.viewmodel.compose.viewModel(factory= AppViewModelProvider.Factory),

                    navigateBack: () -> Unit) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val coroutineScope = rememberCoroutineScope()

    val tabTitles = listOf("Manual", "Scan", "Message")
    // Thêm biến onCloseClick

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
                    text = "Add Transaction",
                    fontSize = 22.sp,
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .padding(end = 16.dp)
                )
                // Close Button
                IconButton(
                    onClick = { coroutineScope.launch {
                        navigateBack()
                    }},
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
            // Tabs
            TabRow(
                modifier = Modifier
                    .background(colorResource(R.color.md_theme_background)),
                contentColor = colorResource(id = R.color.md_theme_onBackground),
                selectedTabIndex = selectedTabIndex,
                containerColor = colorResource(id = R.color.md_theme_background),
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                        color = colorResource(id = R.color.md_theme_primary), // Thay màu ở đây
                    )
                }
            ) {
                tabTitles.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        selectedContentColor = colorResource(id = R.color.md_theme_primary),
                        unselectedContentColor = colorResource(id = R.color.md_theme_onSurface),
                        text = { Text(title) }
                    )
                }
            }

            // Content
            when (selectedTabIndex) {
                0 -> TabContent1(transactionUiState = expenseviewModel.transactionUiState,navigateBack=navigateBack, coroutineScope = coroutineScope)
                1 -> TabContent2()
                2 -> TabContent3()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TabContent1(transactionUiState: TransactionUiState,
                expenseViewModel: ExpenseViewModel= androidx.lifecycle.viewmodel.compose.viewModel(factory= AppViewModelProvider.Factory),

                navigateBack: () -> Unit,
                coroutineScope: CoroutineScope
                ) {

    var selectedChipIndex by remember { mutableStateOf(0) }
    val chipTitles = listOf("EXPENSE", "INCOME")

    Box(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.TopCenter)
                .padding(0.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {

            EditableAmountField(
                value = transactionUiState.amount,
                transactionUiState = transactionUiState,
                onValueChange=expenseViewModel::updateUiState

            )
            Row(
                modifier = Modifier
                    .padding(top = 30.dp)
                    .fillMaxWidth()
//                horizontalArrangement = Arrangement.SpaceEvenly

            ) {
                chipTitles.forEachIndexed { index, title ->
                    InputChip(
                        selected =  selectedChipIndex == index,
                        onClick = { selectedChipIndex = index },
                        border = InputChipDefaults.inputChipBorder(
                            disabledBorderColor = colorResource(R.color.md_theme_onBackground),
                            borderWidth = 0.dp
                        ),
                        colors = InputChipDefaults.inputChipColors(
                            selectedContainerColor = if (selectedChipIndex == index) colorResource(R.color.md_theme_primaryFixed) else colorResource(R.color.md_theme_background),
                            labelColor = colorResource(R.color.md_theme_onBackground)
                        ),
                        label = {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(title,
                                    color = colorResource(R.color.md_theme_onBackground))
                            }
                        },
                        modifier = Modifier
                            .padding(0.dp)
                            .background(
                                shape = RectangleShape,
                                color = if (selectedChipIndex == index) colorResource(R.color.md_theme_primaryFixed) else colorResource(
                                    R.color.md_theme_background
                                )
                            )
                            .size(180.dp, 40.dp),  // Tùy chỉnh kích thước
                    )
                }
            }

            // Content
            when (selectedChipIndex) {
             
                0 -> InputChipContent1(transactionUiState = expenseViewModel.transactionUiState,navigateBack=navigateBack, coroutineScope = coroutineScope)
                1 -> InputChipContent2(transactionUiState = expenseViewModel.transactionUiState,navigateBack=navigateBack, coroutineScope = coroutineScope)
            }

        }
    }
}

@Composable
fun InputChipContent1(transactionUiState: TransactionUiState,
                      expenseViewModel: ExpenseViewModel= androidx.lifecycle.viewmodel.compose.viewModel(factory= AppViewModelProvider.Factory),
                      categoryViewModel: CategoryViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory= AppViewModelProvider.Factory),
                      navigateBack: () -> Unit,
                      coroutineScope: CoroutineScope
) {
    var expanded by remember { mutableStateOf(false) } // Khởi tạo là false để menu không mở ban đầu
  //  val menuItems = listOf("Item 1", "Item 2", "Item 3")
    val selectedItem = remember { mutableStateOf("") }
    var noteExpense by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.TopCenter)
                .padding(top = 30.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {

            CategoryDropdown(transactionUiState = expenseViewModel.transactionUiState, categoryUiState = categoryViewModel.categoryUiState, onValueChange = expenseViewModel::updateUiState)


            datetimepicker(onValueChange=expenseViewModel::updateUiState, transactionUiState = transactionUiState)
            //Note
            DescriptionTextField(transactionUiState=transactionUiState,onValueChange=expenseViewModel::updateUiState)

        }
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally)
        {
            NormalButton(
                text = "Save",
                onClick = { coroutineScope.launch {
                    expenseViewModel.saveTransactionExpense()
                    navigateBack()
                }},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            )
        }
    }
}


@Composable
fun InputChipContent2(transactionUiState: TransactionUiState,
                      expenseViewModel: ExpenseViewModel= androidx.lifecycle.viewmodel.compose.viewModel(factory= AppViewModelProvider.Factory),
                      categoryViewModel: CategoryViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory= AppViewModelProvider.Factory),
                      navigateBack: () -> Unit,
                      coroutineScope: CoroutineScope
) {
    var expanded by remember { mutableStateOf(false) } // Khởi tạo là false để menu không mở ban đầu
    //  val menuItems = listOf("Item 1", "Item 2", "Item 3")
    val selectedItem = remember { mutableStateOf("") }
    var noteExpense by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.TopCenter)
                .padding(top = 30.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {

            CategoryIncomeDropdown(transactionUiState = expenseViewModel.transactionUiState, onValueChange = expenseViewModel::updateUiState)


            datetimepicker(onValueChange=expenseViewModel::updateUiState, transactionUiState = transactionUiState)
            //Note
            DescriptionTextField(transactionUiState=transactionUiState,onValueChange=expenseViewModel::updateUiState)

        }
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally)
        {
            NormalButton(
                text = "Save",
                onClick = { coroutineScope.launch {
                    expenseViewModel.saveTransactionIncome()
                    navigateBack()
                }},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            )
        }
    }
}


@Composable
fun TabContent2() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally)
        {
            Spacer(modifier = Modifier.height(100.dp))

            Text(
                text = "Record your transaction easily\n" +
                        "with the receipt scanning feature",
                fontSize = 20.sp,
                modifier = Modifier.padding(bottom = 16.dp))
        }
        //Button
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            NormalButton(
                text = "Save",
                onClick = { /* TODO */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            )
        }
    }
}

@Composable
fun TabContent3() {
    var message by remember { mutableStateOf("") }

    Box(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .align(Alignment.TopCenter) // Aligns the column at the top center
//                .padding(0.dp),
//            verticalArrangement = Arrangement.Top,
//            horizontalAlignment = Alignment.Start
//        ) {
//            Image(
//                painter = painterResource(id = R.drawable.message),
//                contentDescription = null,
//                modifier = Modifier
//                    .fillMaxSize() // Kích thước của hình ảnh
//            )
//        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(0.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            NormalTextField(
                value = message,
                onValueChange = { message = it },
                label = "Message",
                modifier = Modifier
                    .padding(bottom = 20.dp)
                    .fillMaxWidth()
            )
            NormalButton(
                text = "Send",
                onClick = { /* TODO */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            )
        }
    }
}

@Composable
fun EditableAmountField(
    value: String,
    transactionUiState: TransactionUiState,
    onValueChange: (TransactionUiState) -> Unit,
//    placeholder:String
) {

    NormalTextField(
        value = value,
        onValueChange = {onValueChange(transactionUiState.copy(amount = it))  },
        label = "Amount",
        modifier = Modifier
            .padding(top = 14.dp)
            .fillMaxWidth(),
        singleLine = true
    )
}

val categoryOptions = listOf("Shopping", "Food", "Entertainment", "Others")


@Composable
fun CategoryDropdown(
    transactionUiState: TransactionUiState,
    categoryUiState: CategoryUIState,
    categoryViewModel: CategoryViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory = AppViewModelProvider.Factory),
    onValueChange: (TransactionUiState) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        categoryViewModel.getAllCategories()
    }

    val categoryListState by categoryViewModel.expenseCategories.collectAsState()
    val selectedCategoryName = categoryListState.find { it.id == transactionUiState.idCategory }?.name ?: "Select a Category"
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Color.White)
                .border(
                    width = 1.5.dp,
                    color = Color(0xFFF1F1FA),
                    shape = RoundedCornerShape(10.dp)
                )
        ) {
            Text(
                text = selectedCategoryName,
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxWidth(),
                color = Color(0xFF91919F)
            )
            IconButton(
                onClick = { expanded = !expanded },
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Expand category dropdown",
                    tint = Color(0xFF91919F)
                )
            }
            DropdownMenu(

                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .background(Color(0xFFFCFCFC))
            ) {
                categoryListState.forEach { category ->
                    DropdownMenuItem(


                        onClick = {

                            onValueChange(transactionUiState.copy(idCategory = category.id))
                            expanded = false
                        },
                        text = {
                            Text(text = category.name, color = Color.Black)
                        }
                    )
                }
            }
        }
    }
}



//fun DropdownMenuItem(onClick: () -> Unit, interactionSource: () -> Unit) {
//
//}


@Composable
fun DescriptionTextField(transactionUiState: TransactionUiState,
                         onValueChange: (TransactionUiState) -> Unit = {}) {
    NormalTextField(
        value = transactionUiState.description,
        onValueChange = { onValueChange(transactionUiState.copy(description=it))},
        label = "Description",
        modifier = Modifier
            .padding(top = 30.dp)
            .fillMaxWidth()
    )
}


@Composable
fun datetimepicker(transactionUiState: TransactionUiState, onValueChange: (TransactionUiState) -> Unit) {
    var pickedDate by remember { mutableStateOf(transactionUiState.date) }
    var pickedTime by remember { mutableStateOf(transactionUiState.time) }

    val formattedDate by remember {
        derivedStateOf {
            DateTimeFormatter.ofPattern("MMM dd yyyy").format(pickedDate)
        }
    }
    val formattedTime by remember {
        derivedStateOf {
            DateTimeFormatter.ofPattern("hh:mm a").format(pickedTime)
        }
    }

    val context = LocalContext.current
    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            val newDate = LocalDate.of(year, month + 1, dayOfMonth)
            pickedDate = newDate
            onValueChange(transactionUiState.copy(date = newDate))
        },
        pickedDate.year,
        pickedDate.monthValue - 1,
        pickedDate.dayOfMonth
    )

    val timePickerDialog = TimePickerDialog(
        context,
        { _, hourOfDay, minute ->
            val newTime = LocalTime.of(hourOfDay, minute)
            pickedTime = newTime
            onValueChange(transactionUiState.copy(time = newTime))
        },
        pickedTime.hour,
        pickedTime.minute,
        false // false for 12 hour time, true for 24 hour time
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            colors = ButtonDefaults.buttonColors(),
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(10.dp)),
            onClick = { datePickerDialog.show() }
        ) {
            Text(text = formattedDate, style = TextStyle(color = Color.Black))
        }
        Spacer(modifier = Modifier.width(10.dp))
        Button(
            colors = ButtonDefaults.buttonColors(),
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(10.dp)),
            onClick = { timePickerDialog.show() }
        ) {
            Text(text = formattedTime, style = TextStyle(color = Color.Black))
        }
    }
}

@Composable
fun CategoryIncomeDropdown(
    transactionUiState: TransactionUiState,
    onValueChange: (TransactionUiState) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    val categoryList = listOf("INCOME")
    val selectedCategoryName = if (transactionUiState.idCategory != null) "INCOME" else "Select a Category"

    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Color.White)
                .border(
                    width = 1.5.dp,
                    color = Color(0xFFF1F1FA),
                    shape = RoundedCornerShape(10.dp)
                )
        ) {
            Text(
                text = selectedCategoryName,
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxWidth(),
                color = Color(0xFF91919F)
            )
            IconButton(
                onClick = { expanded = !expanded },
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Expand category dropdown",
                    tint = Color(0xFF91919F)
                )
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .background(Color(0xFFFCFCFC))
            ) {
                categoryList.forEach { category ->
                    DropdownMenuItem(
                        onClick = {
                            onValueChange(transactionUiState.copy(idCategory = 1)) // Assuming "INCOME" has id 1
                            expanded = false
                        },
                        text = {
                            Text(text = category, color = Color.Black)
                        }
                    )
                }
            }
        }
    }
}