package com.novapay.novapaysdk

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.novapay.novapaysdk.ui.theme.NovaPaySdkTheme
import com.novapay.ui.core.platform.novaPayUiSdk

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            NovaPaySdkTheme {
                Greeting(name = "Lewis")
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    val context = LocalContext.current
    Button(onClick = {
        novaPayUiSdk.startGenerateTRNFlow(context  as Activity,
            onError = { Log.i("here", "error")},
            onSuccess = { Log.i("here", "success")})
    }) {
        Text(text = "Hello $name!")
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    NovaPaySdkTheme {
        Greeting("Android")
    }
}