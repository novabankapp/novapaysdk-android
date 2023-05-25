package com.novapay.ui.features.transactions


import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.novapay.ui.core.platform.BaseActivity
import com.novapay.ui.themes.NovaPayUISdkTheme
import com.novapay.ui.ui.transactions.screens.GenerateTRNScreen
import com.novapay.ui.ui.transactions.sections.EnterAnimation
import com.novapay.ui.ui.transactions.sections.LoadingBox
import com.novapay.ui.viewModels.transactions.TransactionViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class TransactionActivity : BaseActivity() {

    private val transactionViewModel: TransactionViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NovaPayUISdkTheme{
                val navController = rememberNavController()
                EnterAnimation {

                    LoadingBox()
                }
                NavHost(navController = navController, startDestination = "generate") {
                    composable("generate") {
                        GenerateTRNScreen(transactionViewModel)
                    }

                }
            }

            }
        }

    companion object {
        fun callingIntent(from: Context): Intent = Intent(from, TransactionActivity::class.java)
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    NovaPayUISdkTheme {
        Greeting("Android")
    }
}