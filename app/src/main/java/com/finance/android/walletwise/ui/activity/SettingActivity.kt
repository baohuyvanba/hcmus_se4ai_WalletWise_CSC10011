package com.finance.android.walletwise.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.finance.android.walletwise.R
import com.finance.android.walletwise.ui.theme.*



@Preview(showBackground = true)
@Composable
fun SettingScreen() {
    var theme by remember { mutableStateOf("") }
    var language by remember { mutableStateOf("English") }
    var currency by remember { mutableStateOf("VND") }
    var dateFormat by remember { mutableStateOf("dd/mm/yyyy") }
    var spendingAlert by remember { mutableStateOf(true) }
    var notificationTone by remember { mutableStateOf(true) }
    var enablePin by remember { mutableStateOf(true) }

    Column(modifier = Modifier.fillMaxSize().background(Color.White)) {

//        TopAppBar(
//            title = { Text("WalletWise", fontSize = 20.sp) },
//            navigationIcon = {
//                IconButton(onClick = { /* Handle menu click */ }) {
//                    Icon(imageVector = Icons.Filled.Menu, contentDescription = "Menu")
//                }
//            },
//            actions = {
//                IconButton(onClick = { /* Handle notifications click */ }) {
//                    Icon(imageVector = Icons.Filled.Notifications, contentDescription = "Notifications")
//                }
//            }
//

        Column(modifier = Modifier.padding(16.dp)) {

            Column(
                modifier = Modifier.padding(vertical = 8.dp).fillMaxWidth(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(R.drawable.application_logo),
                    contentDescription = "App Logo",
                    modifier = Modifier
                        .padding(bottom = 8.dp),
                )
            }

            Section(title = "Display") {
                SettingItem(title = "Theme", value = theme)
                SettingItem(title = "Select language", value = language)
                SettingItem(title = "Currency", value = currency)
                SettingItem(title = "Select date format", value = dateFormat)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Section(title = "Notification") {
                ToggleSettingItem(
                    title = "Receive alert based on spending",
                    checked = spendingAlert,
                    onCheckedChange = { spendingAlert = it }
                )
                ToggleSettingItem(
                    title = "Notification tone",
                    checked = notificationTone,
                    onCheckedChange = { notificationTone = it }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Section(title = "System") {
                SettingItem(title = "Setting profile")
                ToggleSettingItem(
                    title = "Enable PIN",
                    checked = enablePin,
                    onCheckedChange = { enablePin = it }
                )
                SettingItem(title = "Change PIN")
                SettingItem(title = "Change password")
                SettingItem(title = "Report bugs to developer")
                SettingItem(title = "Sign out", value = "nguyenvana.hcmus@gmail.com")
            }
        }
    }
}


@Composable
fun Section(title: String, content: @Composable () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = title,
            color = Color.Blue,
            fontSize = 18.sp,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        content()
    }
}

@Composable
fun SettingItem(title: String, value: String = "") {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
        Text(text = title, fontSize = 16.sp)
        if (value.isNotEmpty()) {
            Text(text = value, fontSize = 14.sp, color = Color.Gray)
        }
    }
}

@Composable
fun ToggleSettingItem(title: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = title, fontSize = 16.sp)
        Switch(checked = checked, onCheckedChange = onCheckedChange)
    }
}



// Temp Bottom app bar
//    @Composable
//    fun BottomNavigationBar() {
//        var selectedItem by remember { mutableStateOf(0) }
//        val items = listOf("Home", "Transactions", "Dashboard", "Settings")
//
//        BottomNavigation(
//            backgroundColor = Color(0xFFBBDEFB), // Change color as needed
//        ) {
//            items.forEachIndexed { index, item ->
//                BottomNavigationItem(
//                    icon = {
//                        when (item) {
//                            "Home" -> Icon(Icons.Filled.Home, contentDescription = item)
//                            "Transactions" -> Icon(Icons.Filled.Receipt, contentDescription = item)
//                            "Dashboard" -> Icon(Icons.Filled.Dashboard, contentDescription = item)
//                            "Settings" -> Icon(Icons.Filled.Settings, contentDescription = item)
//                        }
//                    },
//                    label = { Text(item) },
//                    selected = selectedItem == index,
//                    onClick = { selectedItem = index }
//                )
//            }
//        }
//    }


// Temp
//    Scaffold(
//        bottomBar = {
//            BottomNavigationBar()
//        }
//    ) { paddingValues ->
//        Column(
//            modifier = Modifier
//                .padding(paddingValues)
//                .fillMaxSize()
//                .background(Color.White)
//        ) {
//
//            Column(modifier = Modifier.padding(16.dp)) {
//
//                Column(
//                    modifier = Modifier
//                        .padding(vertical = 8.dp)
//                        .fillMaxWidth(),
//                    verticalArrangement = Arrangement.Top,
//                    horizontalAlignment = Alignment.CenterHorizontally
//                ) {
//                    Image(
//                        painter = painterResource(R.drawable.application_logo),
//                        contentDescription = "App Logo",
//                        modifier = Modifier.padding(bottom = 8.dp),
//                    )
//                }
//
//                Section(title = "Display") {
//                    SettingItem(title = "Theme", value = theme)
//                    SettingItem(title = "Select language", value = language)
//                    SettingItem(title = "Currency", value = currency)
//                    SettingItem(title = "Select date format", value = dateFormat)
//                }
//
//                Spacer(modifier = Modifier.height(16.dp))
//
//                Section(title = "Notification") {
//                    ToggleSettingItem(
//                        title = "Receive alert based on spending",
//                        checked = spendingAlert,
//                        onCheckedChange = { spendingAlert = it }
//                    )
//                    ToggleSettingItem(
//                        title = "Notification tone",
//                        checked = notificationTone,
//                        onCheckedChange = { notificationTone = it }
//                    )
//                }
//
//                Spacer(modifier = Modifier.height(16.dp))
//
//                Section(title = "System") {
//                    SettingItem(title = "Setting profile")
//                    ToggleSettingItem(
//                        title = "Enable PIN",
//                        checked = enablePin,
//                        onCheckedChange = { enablePin = it }
//                    )
//                    SettingItem(title = "Change PIN")
//                    SettingItem(title = "Change password")
//                    SettingItem(title = "Report bugs to developer")
//                    SettingItem(title = "Sign out", value = "nguyenvana.hcmus@gmail.com")
//                }
//            }
//        }
//    }
//}