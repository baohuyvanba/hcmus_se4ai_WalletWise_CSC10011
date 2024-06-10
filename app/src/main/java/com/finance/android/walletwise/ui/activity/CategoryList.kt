package com.finance.android.walletwise.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.finance.android.walletwise.R
import com.finance.android.walletwise.WalletWiseTheme
import com.finance.android.walletwise.ui.fragment.BalanceSection
import com.finance.android.walletwise.ui.fragment.FAButton
import com.finance.android.walletwise.ui.fragment.WalletWiseBottomBar
import com.finance.android.walletwise.ui.fragment.WalletWiseTopAppBar

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CategoryList()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CategoryList() {
    WalletWiseTheme{
        Scaffold(
            // TOP APP BAR
            topBar = {
                WalletWiseTopAppBar(
                    title = "WalletWise",
                    useIconForTitle = true,
                    showNavigationButton = true,
                    showActionButton = true,
                    onNavigationClick = { /*TODO*/ },
                    onActionClick = { /*TODO*/ },
                )
            },
            // BOTTOM BAR
            bottomBar = {
                WalletWiseBottomBar(
                    selectedTab = 0,
                    onTabSelected = { /*TODO*/ },
                    onHomeClick = { /*TODO*/ },
                    onExpenseListClick = { /*TODO*/ },
                    onCategoryListClick = { /*TODO*/ },
                    onSettingsClick = {/* TODO */},
                )
            },
            // FAB
            floatingActionButton = {
                FAButton(
                    onClick = { /*TODO*/ },
                    icon = Icons.Default.Add,
                    contentDescription = "Add Transaction"
                )
            },
            floatingActionButtonPosition = FabPosition.End,
            // MAIN CONTENT
            content = { innerPadding ->
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .background(Color(0xFFF5F5F5))
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        BalanceSection(
                            title = "REMAINING BUDGET",
                            balance = "1500000",
                            currency = "VND")
                        LinearProgress()
                        Spacer(modifier = Modifier.height(16.dp))
                        BudgetProgressCard("Food & Drink", 30, 50, R.drawable.category_icon_food___drink, "Food & Drink")
                        BudgetProgressCard("Transportation", 50, 50, R.drawable.category_icon_transportation, "Transportation")
                        BudgetProgressCard("Education", 80, 50, R.drawable.category_icon_education, "Education")
                        BudgetProgressCard("House & Utilities", 30, 50, R.drawable.category_icon_home, "House & Utilities")
                        BudgetProgressCard("Health", 10, 50, R.drawable.category_icon_health, "Health")
                        BudgetProgressCard("Sport & Entertainment", 30, 50, R.drawable.category_icon_sport, "Sport & Entertainment")
                        BudgetProgressCard("Shopping", 40, 50, R.drawable.category_icon_shopping, "Shopping")
                        BudgetProgressCard("Gifts", 50, 50, R.drawable.category_icon_gift, "Gifts")
                        BudgetProgressCard("Self Development", 30, 50, R.drawable.category_icon_self_development, "Self Development")
                    }
                }
            }
        )
    }
}


@Composable
fun LinearProgress() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        LinearProgressIndicator(
            progress = 0.5f,
            color = Color(0xFF70AA72),
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp))
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "15/30 days", fontSize = 12.sp)
            Text(text = "1,500,000", fontSize = 12.sp)
            Text(text = "3,000,000", fontSize = 12.sp)
        }
    }
}

//
@Composable
fun BudgetProgress(category: String, spent: Int, total: Int, iconId: Int, contentDescription: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Image(
            painter = painterResource(id = iconId), // Thay thế bằng tài nguyên drawable của bạn
            contentDescription = contentDescription,
            modifier = Modifier
                .size(45.dp)
                .padding(end = 8.dp)
        )
        // Row chứa icon và text
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = category, modifier = Modifier.weight(20f), fontWeight = FontWeight.Medium)
                Text(text = "$spent%", fontSize = 12.sp, fontWeight = FontWeight.Medium)
            }
            Spacer(modifier = Modifier.height(4.dp))
            LinearProgressIndicator(
                progress = spent / 100f,
                color = Color(0xFF7C99EE),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .padding(top = 4.dp)
            )
        }
    }
}

@Composable
fun BudgetProgressCard(category: String, spent: Int, total: Int, iconId: Int, contentDescription: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(7.dp)
    ) {
        // Add padding inside the Card
        Box(modifier = Modifier.padding(4.dp)) {
            BudgetProgress(category, spent, total, iconId, contentDescription)
        }
    }
}

@Composable
fun FloatingActionButton() {
    FloatingActionButton(onClick = { /*TODO*/ }) {
        Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
    }
}
