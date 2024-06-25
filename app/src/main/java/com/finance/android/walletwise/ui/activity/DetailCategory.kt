package com.finance.android.walletwise.ui.activity

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Divider
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.finance.android.walletwise.R
import com.finance.android.walletwise.ui.fragment.BalanceSection
import com.finance.android.walletwise.ui.fragment.DetailedBalanceSection_2
import com.finance.android.walletwise.ui.fragment.NormalSwitch
import com.finance.android.walletwise.ui.viewmodel.CategoryViewModel
import com.finance.android.walletwise.util.categoryIconsList
import androidx.lifecycle.viewmodel.compose.viewModel
import com.finance.android.walletwise.WalletWiseDestination
import com.finance.android.walletwise.model.Category.CategoryUIState
import com.finance.android.walletwise.ui.AppViewModelProvider



//@Composable
//fun DetailCategoryScreen(
//    categoryId: Int,
//    viewModel: CategoryViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory = AppViewModelProvider.Factory), navigateBack: () -> Unit)
//) {
//    val categoryUIState by viewModel.getCategoryUiStateById(categoryId).collectAsState(initial = null)
//    categoryUIState?.let { state ->
//        BudgetDetailScreen(
//            categoryUIState = state,
//            onEditClick = { /* Implement edit action */ },
//            navigateBack = navigateBack
//        )
//    }
//}

//object DetailCategoryDestination : WalletWiseDestination {
//    override val route = "DetailCategory"
//
//    override val icon: ImageVector = Icons.Default.Home
//    const val tIdArg = "transactionId"
//    val routeWithArgs = "$route/{$transactionIdArg}"
//}

@Composable
fun BudgetDetailScreen(
    categoryUIState: CategoryUIState,
    onEditClick: () -> Unit,
    navigateBack: () -> Unit
) {
    var isChecked by remember { mutableStateOf(true) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Detail",
                style = MaterialTheme.typography.headlineSmall
            )

            IconButton(onClick = navigateBack) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close"
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = categoryIconsList[categoryUIState.icon] ?: R.drawable.ic_category),
                contentDescription = "Category Icon",
                modifier = Modifier.size(39.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = categoryUIState.name,
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        Divider(
            modifier = Modifier
                .padding(start = 8.dp, end = 8.dp)
                .padding(top = 16.dp, bottom = 16.dp),
            color = Color.Gray.copy(alpha = 0.5f),
            thickness = 1.dp
        )

        BalanceSection(
            title = "BUDGET",
            balance = categoryUIState.amount,
            currency = "VND",
            showTitle = true,
            showCurrencyBackground = true,
            currencyBackgroundColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
        )

        Spacer(modifier = Modifier.height(16.dp))

        DetailedBalanceSection_2(
            incomeAmount = "0",  // Example value, replace with actual data
            outcomeAmount = categoryUIState.amount,  // Example value, replace with actual data
            onIncomeClick = {},
            onOutcomeClick = {}
        )

        Spacer(modifier = Modifier.height(16.dp))

        NormalSwitch(
            text = "Repeat this budget category",
            isChecked = isChecked,
            onCheckedChange = { isChecked = it },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        IconButton(
            onClick = onEditClick,
            modifier = Modifier
                .align(Alignment.End)
                .background(Color(0xFFE3F2FD), CircleShape)
        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Edit"
            )
        }
    }
}
