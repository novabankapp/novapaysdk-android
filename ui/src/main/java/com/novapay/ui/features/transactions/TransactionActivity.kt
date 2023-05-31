package com.novapay.ui.features.transactions


import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.novapay.ui.core.platform.BaseActivity
import com.novapay.ui.events.transactions.TransactionEvent
import com.novapay.ui.models.transactions.TransactionActivityOptions
import com.novapay.ui.themes.NovaPayUISdkTheme
import com.novapay.ui.ui.transactions.screens.GenerateTRNScreen
import com.novapay.ui.ui.transactions.sections.EnterAnimation
import com.novapay.ui.ui.transactions.sections.LoadingBox
import com.novapay.ui.viewModels.transactions.TransactionViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.Serializable


class TransactionActivity : BaseActivity() {

    private val transactionViewModel: TransactionViewModel by viewModel()


    private fun close(trn : String?){
        trn?.let { Log.d("trn iyi", it) }
        val resultIntent = Intent()
        resultIntent.putExtra("trn", trn)
        setResult(RESULT_OK, resultIntent)
        finish()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NovaPayUISdkTheme{
                val navController = rememberNavController()
                val options = intent.serializable<TransactionActivityOptions>("options")
                if(options != null){
                    val events = transactionViewModel::handleTransactionEvent
                    events(
                        TransactionEvent.LoadOptions(
                          options = options
                    ))

                }
                EnterAnimation {

                    LoadingBox()
                }
                NavHost(navController = navController, startDestination = "generate") {
                    composable("generate") {
                        GenerateTRNScreen(transactionViewModel, { trn ->  close(trn) })
                    }

                }
            }

            }
        }

    inline fun <reified T : Serializable> Bundle.serializable(key: String): T? = when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getSerializable(key, T::class.java)
        else -> @Suppress("DEPRECATION") getSerializable(key) as? T
    }

    inline fun <reified T : Serializable> Intent.serializable(key: String): T? = when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getSerializableExtra(key, T::class.java)
        else -> @Suppress("DEPRECATION") getSerializableExtra(key) as? T
    }

    companion object {


        fun callingIntent(from: Context, options : TransactionActivityOptions?): Intent {

            val intent = Intent(from, TransactionActivity::class.java)
            if (options != null) {
                intent.putExtra("options", options)

            }
            return intent
        }
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