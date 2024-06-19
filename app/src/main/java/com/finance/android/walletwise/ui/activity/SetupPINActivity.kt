package com.finance.android.walletwise.ui.activity

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.finance.android.walletwise.R
import com.finance.android.walletwise.WalletWiseTheme
import com.finance.android.walletwise.ui.fragment.NormalButton
import com.finance.android.walletwise.ui.fragment.PinField
import com.finance.android.walletwise.ui.viewmodel.user.PinViewModel

@Preview(showBackground = true)
@Composable
fun PreviewSetupPinScreen()
{
    WalletWiseTheme {
        SetupPinScreen(
            onNavigateHome = { /*TODO*/ }
        )
    }
}

@Composable
fun SetupPinScreen(
    pinViewModel: PinViewModel? = null,
    onNavigateHome: () -> Unit,)
{
    val pinUiState = pinViewModel?.pinUiState

    var pin by remember { mutableStateOf("") }
    var confirmPin by remember { mutableStateOf("") }
//    var useFingerprint by remember { mutableStateOf(false) }

    val configuration = LocalConfiguration.current
    val screenHeight  = configuration.screenHeightDp

    Box(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background), )
    {
        //LOGO
        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.TopCenter)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start)
        {
            Image(
                painter = painterResource(R.drawable.application_logo),
                contentDescription = "Application Logo",)
        }

        /**
         * PIN FORM ================================================================================
         */
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally)
        {
            //Setup PIN text -----------------------------------------------------------------------
            Text(
                text = "Setup PIN",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(bottom = 16.dp), )

            Spacer(modifier = Modifier.height((screenHeight * 0.01).dp))

            //Enter Pin field ---------------------------------------------------------------------
            Text(
                text = "Enter PIN",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
            )
            PinField(
                value = pinUiState?.pin ?: "",
                mask = true,
                onValueChange = { pinViewModel?.onPinChanged(it) },
                onPinEntered = {},
                isError = false,
            )

            Spacer(modifier = Modifier.fillMaxHeight(0.04f))

            //Confirm Pin field --------------------------------------------------------------------
            Text(
                text = "Confirm PIN",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
            )
            PinField(
                value = pinUiState?.confirmPin ?: "",
                mask = true,
                onValueChange = { pinViewModel?.onConfirmPinChanged(it) },
                onPinEntered = {},
                isError = false,
            )

            //Error message
            if (pinUiState?.pin != pinUiState?.confirmPin) {
                Text(
                    text = "PINs do not match",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.fillMaxHeight(0.05f))

//            NormalSwitch(
//                text = "Use Fingerprint",
//                isChecked = useFingerprint,
//                onCheckedChange = { useFingerprint = it },
//                modifier = Modifier
//                    .width((50*4+16*3).dp), )

            Spacer(modifier = Modifier.fillMaxHeight(0.15f))
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
                .padding(16.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally)
        {
            NormalButton(
                text = "Next",
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    pinViewModel?.createPin()
                },
                enabled = (pinUiState?.pin != "") && (pinUiState?.confirmPin != "") && (pinUiState?.pin == pinUiState?.confirmPin), )
        }

        LaunchedEffect(key1 = pinUiState?.isPinCreated)
        {
            if (pinUiState?.isPinCreated == true)
            {
                onNavigateHome()
            }
        }

    }
}

