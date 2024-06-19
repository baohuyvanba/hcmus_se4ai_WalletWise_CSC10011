package com.finance.android.walletwise.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.finance.android.walletwise.ui.activity.Message
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import com.google.ai.client.generativeai.type.generationConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalTime
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class TextExtractionViewModel : ViewModel() {

    private val _messages = MutableLiveData<List<Message>>(listOf())
    val messages: LiveData<List<Message>> = _messages
    var amountExtracted: String = "0"
    var categoryExtracted: String = "0"
    var dateExtracted: LocalDate = LocalDate.now()
    var noteExtracted: String = "0"

    private val model: GenerativeModel = GenerativeModel(
        "gemini-1.5-flash",
        "AIzaSyCq40KK_pI3FM_-3wkCOw-86H6RERMbZ70",
        generationConfig = generationConfig {
            temperature = 1f
            topK = 64
            topP = 0.95f
            maxOutputTokens = 8192
            responseMimeType = "text/plain"
        },
        // safetySettings = Adjust safety settings
        // See https://ai.google.dev/gemini-api/docs/safety-settings
        systemInstruction = content { text("From the following message, extract the amount, category, date, and note. Categories include Food & Drink, Education, Transportation, Sport & Entertainment, Shopping, House & Utilities: {user_message}\nOutput format: \"{amount_extracted}\", \"{category_extracted}\", \"{date_extracted}\", \"{note_extracted}\"") },
    )

    private fun extractVariables(response: String)  {
        // Tách chuỗi response thành các giá trị tương ứng
        val values = response.split(", ")
        val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        if (values.size >= 4) {
            amountExtracted = values[0]
            categoryExtracted = values[1]
            dateExtracted = LocalDate.parse(values[2],dateFormatter)
            noteExtracted = values[3]
        }
    }
    fun extractText(userMessage: String){
        val newMessages = _messages.value.orEmpty() + Message(userMessage, true)
        _messages.value = newMessages

        viewModelScope.launch(Dispatchers.IO) {
            val chatHistory = listOf(
                content("user") {
                    text(userMessage)
                }
            )
            val chat = model.startChat(chatHistory)
            val response = chat.sendMessage(userMessage)
            val modelResponse = response.text ?: ""

            extractVariables(modelResponse)

        }
    }
}
