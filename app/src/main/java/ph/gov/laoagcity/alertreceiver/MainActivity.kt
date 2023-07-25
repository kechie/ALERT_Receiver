package ph.gov.laoagcity.alertreceiver

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.text.BasicTextField
//import androidx.compose.foundation.text.KeyboardActions
//import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.platform.LocalSoftwareKeyboardController
//import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import ph.gov.laoagcity.alertreceiver.ui.theme.ALERTReceiverTheme

class MainActivity : ComponentActivity() {
    companion object {
        private const val REQUEST_SMS_PERMISSION = 101
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            setContent {
                MyApp {
                    // Check if SMS permissions are granted
                    if (ContextCompat.checkSelfPermission(
                            this@MainActivity,
                            Manifest.permission.RECEIVE_SMS
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        requestSmsPermissions()
                    }
                    // Your UI content goes here
                    // For simplicity, we will show a text field
                    MainContent()
                }
            }
        }
    }
    private fun requestSmsPermissions() {
        if (shouldShowRequestPermissionRationale(Manifest.permission.RECEIVE_SMS)) {
            // Show an explanation to the user why the permission is needed (optional).
            // You can display a dialog or a custom message explaining why the permission is necessary.
            Log.d("D:","TODO: show rationale or some such")
        }

        // Request the permission using the registerForActivityResult API.
        ActivityCompat.requestPermissions(
            this@MainActivity,
            arrayOf(Manifest.permission.RECEIVE_SMS),
            REQUEST_SMS_PERMISSION
        )
    }
}

@Composable
fun MyApp(content: @Composable () -> Unit) {
//    MaterialTheme {
    ALERTReceiverTheme{
        Surface(color = MaterialTheme.colorScheme.background) {
            content()
        }
    }
}

@Composable
fun MainContent() {
//    val context = LocalContext.current
//    val keyboardController = LocalSoftwareKeyboardController.current
//    val messageState = remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
/*
        BasicTextField(
            value = messageState.value,
            onValueChange = { messageState.value = it },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {
                    // Do something when the user presses Done on the keyboard
                    keyboardController?.hide()
                    // For demonstration purposes, we'll just print the message
                    println("Typed message: ${messageState.value}")
                    messageState.value = ""
                }
            ),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
*/
        Text(text="ALERT App SMS Receiver and Logger App", textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
    }
}

@Preview
@Composable
fun DefaultPreview() {
    MyApp {
        MainContent()
    }
}