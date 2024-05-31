package com.finance.android.walletwise.ui.activity

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.InputChip
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import com.finance.android.walletwise.ui.fragment.NormalTextField
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.finance.android.walletwise.R
import com.finance.android.walletwise.ui.fragment.NormalButton

@Preview(showBackground = true)
@Composable
fun MyTabLayout() {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabTitles = listOf("Manual", "Scan", "Message")
    // Thêm biến onCloseClick
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
                    text = "Add Transaction",
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
                0 -> TabContent1()
                1 -> TabContent2()
                2 -> TabContent3()
            }
        }
    }



}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TabContent1() {
    var amount by remember { mutableStateOf("") }
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
            NormalTextField(
                value = amount,
                onValueChange = { amount = it },
                label = "Amount",
                modifier = Modifier
                    .padding(top = 14.dp)
                    .fillMaxWidth()
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
                0 -> InputChipContent1()
                1 -> InputChipContent2()
            }

//            NormalButton(
//                text = "Save",
//                onClick = { /* TODO */ },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(50.dp)
//            )
        }
    }
}

@Composable
fun InputChipContent1() {
    var expanded by remember { mutableStateOf(false) } // Khởi tạo là false để menu không mở ban đầu
    val menuItems = listOf("Item 1", "Item 2", "Item 3")
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
            OutlinedTextField(
                value = selectedItem.value,
                onValueChange = { },
                placeholder = { Text("Category") },
                modifier = Modifier
                    .fillMaxWidth()
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
            //Button
        }
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally)
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

fun DropdownMenuItem(onClick: () -> Unit, interactionSource: @Composable () -> Unit) {

}

@Composable
fun InputChipContent2() {
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        Text(text = "This is content for Chip 2")
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
