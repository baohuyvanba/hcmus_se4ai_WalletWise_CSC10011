package com.finance.android.walletwise.ui.activity

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Divider
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.InputChip
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Switch
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import com.finance.android.walletwise.ui.fragment.NormalTextField
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.finance.android.walletwise.R
import com.finance.android.walletwise.ui.fragment.NormalButton
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import com.finance.android.walletwise.WalletWiseTheme
import com.finance.android.walletwise.model.Category.CategoryUIState
import com.finance.android.walletwise.model.Transaction.TransactionUiState
import com.finance.android.walletwise.ui.AppViewModelProvider
import com.finance.android.walletwise.ui.fragment.NormalSwitch
import com.finance.android.walletwise.ui.viewmodel.CategoryViewModel
import com.finance.android.walletwise.ui.viewmodel.ExpenseViewModel
import kotlinx.coroutines.launch


//@Preview(showBackground = true)
//@Composable
//fun AddCategoryScreenPreview() {
//    WalletWiseTheme {
//        AddCategoryScreen(
//            text = "Repeat this budget category",
//            isChecked = true,
//            onCheckedChange = {}
//        )
//    }
//}
@Composable
fun ScreeneAddCategory(viewModel: CategoryViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory= AppViewModelProvider.Factory), navigateBack: () -> Unit){
    AddCategoryScreen(categoryUIState = viewModel.categoryUiState,navigateBack=navigateBack)
}
@Composable
fun AddCategoryScreen(categoryUIState: CategoryUIState,
                      viewModel: CategoryViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory= AppViewModelProvider.Factory),
                      navigateBack: () -> Unit) {
    var categoryName by remember { mutableStateOf("") }
    var budget by remember { mutableStateOf("") }
    var isRepeat by remember { mutableStateOf(true) }
    var isChecked by remember { mutableStateOf(true)}
    val coroutineScope = rememberCoroutineScope()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Category icon:", fontSize = 20.sp, modifier = Modifier.padding(bottom
        = 8.dp))

        IconButton(
            onClick = { /* Handle icon selection */ },
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(Color.Gray.copy(alpha = 0.2f))
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add Icon", tint = Color.Gray)
        }

        Spacer(modifier = Modifier.height(16.dp))

        NameCategoryTextField(categoryUIState=viewModel.categoryUiState,onValueChange=viewModel::updateUiState)


        Spacer(modifier = Modifier.height(16.dp))
        BudgetTextField(categoryUIState=viewModel.categoryUiState,onValueChange=viewModel::updateUiState)

//        OutlinedTextField(
//            value = budget,
//            onValueChange = { budget = it },
//            label = { Text("Enter budget") },
//            singleLine = true,
//            trailingIcon = {
//                if (budget.isNotEmpty()) {
//                    IconButton(onClick = { budget = "" }) {
//                        Icon(Icons.Default.Clear, contentDescription = "Clear")
//                    }
//                }
//            },
//            modifier = Modifier.fillMaxWidth()
//        )

        Spacer(modifier = Modifier.height(16.dp))

//        Row(
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Text("Repeat this budget category")
//            Spacer(modifier = Modifier.weight(1f))
//            Switch(
//                checked = isRepeat,
//                onCheckedChange = { isRepeat = it }
//            )
//        }

        NormalSwitch(text = "Repeat this budget category",
            isChecked = isChecked,
            onCheckedChange = { isChecked = it },
            modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.height(32.dp))

        NormalButton(
            text = "Save",
            onClick = { coroutineScope.launch {
                viewModel.saveCategory()
                navigateBack()
            }},
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        )
    }
}


@Composable
fun NameCategoryTextField(categoryUIState: CategoryUIState,
                         onValueChange: (CategoryUIState) -> Unit = {}) {
    OutlinedTextField(
        value = categoryUIState.name,
        onValueChange = { onValueChange(categoryUIState.copy(name=it))},
        label = { Text("Name") },
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )
}
@Composable
fun BudgetTextField(categoryUIState: CategoryUIState,
                          onValueChange: (CategoryUIState) -> Unit = {})
{
    OutlinedTextField(
        value = categoryUIState.amount,
        onValueChange = { onValueChange(categoryUIState.copy(amount=it))},
        label = { Text("Budget") },
        singleLine = true,
//        trailingIcon = {
//            if (budget.isNotEmpty()) {
//                IconButton(onClick = { budget = "" }) {
//                    Icon(Icons.Default.Clear, contentDescription = "Clear")
//                }
//            }
//        },
        modifier = Modifier.fillMaxWidth()
    )
}

