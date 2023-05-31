package com.novapay.novapaysdk

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.novapay.novapaysdk.ui.theme.NovaPaySdkTheme
import com.novapay.ui.core.platform.novaPayUiSdk
import com.novapay.ui.models.transactions.TransactionActivityOptions
import java.math.BigDecimal


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityResultLauncher: ActivityResultLauncher<Intent> =   registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) {

            Log.d("Login", "within register for results ${it.data?.data} ${it.resultCode}")
            if(it.resultCode != RESULT_CANCELED){
                val data = it.data
                if (data != null) {
                   val trn =  data.getStringExtra("trn")
                    if (trn != null) {
                        Log.d("data", trn)
                    }
                }

            }
        }
        setContent {
            NovaPaySdkTheme {
                Greeting(name = "Lewis",
                    activityResultLauncher = activityResultLauncher)
            }
        }
    }
}

private val TRN_CODE = 100
@Composable
fun Greeting(name: String,
             activityResultLauncher: ActivityResultLauncher<Intent>? = null,
) {
    val context = LocalContext.current
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column {
            Text(
                "Student Details",
                style = MaterialTheme.typography.h5.copy(
                    MaterialTheme.colors.onSurface,
                    fontWeight = FontWeight.Bold
                ),
            )
            Text(
                "Student Name: $name",
                style = MaterialTheme.typography.caption.copy(
                    MaterialTheme.colors.onSurface,
                    fontWeight = FontWeight.Bold
                ),
            )
            Text(
                "Class: Standard 6",
                style = MaterialTheme.typography.caption.copy(
                    MaterialTheme.colors.onSurface,
                    fontWeight = FontWeight.Bold
                ),
            )
            Button(onClick = {
                novaPayUiSdk.startGenerateTRNFlow(
                    context as Activity,
                    onError = { Log.i("here", "error") },
                    onSuccess = { Log.i("here", "success") },
                    options = TransactionActivityOptions("123455555", BigDecimal("0.00")),
                    resultCode = null,
                    activityResultLauncher = activityResultLauncher
                )
            }) {
                Text(text = "Generate Invoice for $name!")
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    NovaPaySdkTheme {
        Greeting("Lewis Msasa")
    }
}