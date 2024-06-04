package com.finance.android.walletwise.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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

import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.finance.android.walletwise.R
import com.finance.android.walletwise.ui.theme.*

//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            ChatAppTheme {
//                ChatScreen()
//            }
//        }
//    }
//}

@Preview
@Composable
fun ChatScreen() {
    var messages by remember { mutableStateOf(listOf<Message>()) }
    var input by remember { mutableStateOf(TextFieldValue("")) }

    Column(modifier = Modifier.fillMaxSize().background(Color.White)) {
//        TopAppBar(
//            title = { Text("WalletWise", fontSize = 20.sp) },
//            navigationIcon = {
//                IconButton(onClick = { /* Handle menu click */ }) {
//                    Icon(painter = painterResource(id = R.drawable.ic_menu), contentDescription = "Menu")
//                }
//            },
//            actions = {
//                IconButton(onClick = { /* Handle notifications click */ }) {
//                    Icon(painter = painterResource(id = R.drawable.ic_notifications), contentDescription = "Notifications")
//                }
//            }
//        )
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

        LazyColumn(
            modifier = Modifier.weight(1f).padding(8.dp),
            reverseLayout = true
        ) {
            items(messages) { message ->
                MessageItem(message)
            }
        }

        Row(modifier = Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
//            TextField(
//                value = input,
//                onValueChange = { input = it },
//                modifier = Modifier.weight(1f).padding(end = 8.dp),
//                placeholder = { Text("Type a message...") }
//            )
            OutlinedTextField(
                value = input,
                onValueChange =  {input = it },
                label = { Text("Message Chatbot") },
                modifier = Modifier.weight(1f).padding(end = 8.dp),
            )
            IconButton(onClick = {
                if (input.text.isNotEmpty()) {
                    messages = messages + Message(input.text, true)
                    input = TextFieldValue("")
                }
            }) {
                Icon(painter = painterResource(id = R.drawable.ic_password_show), contentDescription = "Send")
            }
        }
        // Remind
        Text(
            text = stringResource(id = R.string.chatbot_warning),
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            color = Color.Black,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
        )
    }
}

@Composable
fun MessageItem(message: Message) {
    val backgroundColor = if (message.isUser) Color(0xFFE0F7FA) else Color(0xFFBBDEFB)
    val alignment = if (message.isUser) Alignment.End else Alignment.Start

    Box(
        //contentAlignment = alignment,
        modifier = Modifier.fillMaxWidth().padding(4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.background(backgroundColor, shape = CircleShape).padding(12.dp)
        ) {
            if (!message.isUser) {
                Image(
                    painter = painterResource(id = R.drawable.ic_password_show),
                    contentDescription = "Assistant",
                    modifier = Modifier.size(24.dp).clip(CircleShape)
                )
            }
            Text(message.text, fontSize = 16.sp)
        }
    }
}

data class Message(val text: String, val isUser: Boolean)
