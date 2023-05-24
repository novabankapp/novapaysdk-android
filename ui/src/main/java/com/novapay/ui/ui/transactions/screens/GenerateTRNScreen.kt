package com.novapay.ui.ui.transactions.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.R
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.novapay.ui.events.transactions.TransactionEvent
import com.novapay.ui.states.transactions.TransactionState
import com.novapay.ui.themes.buttonHeight
import com.novapay.ui.themes.commonPadding
import com.novapay.ui.ui.transactions.components.CustomTextField
import com.novapay.ui.ui.transactions.sections.LoadingBoxWithParameters
import com.novapay.ui.viewModels.transactions.TransactionViewModel
import kotlinx.coroutines.launch

@Composable
fun GenerateTRNScreen(
    transactionViewModel: TransactionViewModel
){
    val state by transactionViewModel.uiState.collectAsState()
    generateTRN(
        state,
        transactionViewModel::handleTransactionEvent,
    )
}
@Composable
private fun generateTRN(
    state : TransactionState,
    events: (event: TransactionEvent) -> Unit,
){
        val snackBarHostState = remember { SnackbarHostState() }
        val scope = rememberCoroutineScope()

        Surface(
            color = MaterialTheme.colors.surface,
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()

        ) {
            BoxWithConstraints() {
                val screenWidth = maxWidth
                val screenHeight = maxHeight
                val scrollState = rememberScrollState(0)

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.TopCenter)
                        .verticalScroll(scrollState)
                )
                {

                    Text(
                        "Generate TRN",
                        textAlign = TextAlign.Start,
                        style = MaterialTheme.typography.h5.copy(
                            MaterialTheme.colors.onSurface,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier
                            .align(alignment = Alignment.Start)
                            .padding(start = commonPadding)
                    )
                    Spacer(modifier = Modifier.padding(12.dp))
                    var customerRefState by remember { mutableStateOf(TextFieldValue(state.customerRef)) }
                    CustomTextField(
                        value = customerRefState,
                        placeholder = "Reference Number",
                        onChange = {
                            events(TransactionEvent.ChangeCustomerRef(it.text))
                            customerRefState = it
                        },
                        width = screenWidth,
                        padding = commonPadding,
                        shape = RoundedCornerShape(10),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email
                        )
                    )

                    Spacer(modifier = Modifier.padding(8.dp))
                    var amountState by remember { mutableStateOf(TextFieldValue(state.amount.toString())) }

                    CustomTextField(
                        value = amountState,
                        placeholder = "Amount",
                        onChange = {
                            events(TransactionEvent.ChangeAmount(it.text.toBigDecimal()))
                            amountState = it
                        },
                        width = screenWidth,
                        padding = commonPadding,
                        shape = RoundedCornerShape(10),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Decimal,
                            imeAction = ImeAction.Next,
                        )


                    )
                    Spacer(modifier = Modifier.padding(8.dp))
                    var metaDataState by remember { mutableStateOf(TextFieldValue(state.metadata.toString())) }

                    CustomTextField(
                        value = metaDataState,
                        placeholder = "Description",
                        onChange = {
                            events(TransactionEvent.ChangeMetadata(it.text))
                            metaDataState = it
                        },
                        width = screenWidth,
                        padding = commonPadding,
                        modifier = Modifier.height(100.dp),
                        shape = RoundedCornerShape(10),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Done,
                        )


                    )

                    Spacer(modifier = Modifier.padding(8.dp))
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(horizontal = commonPadding)
                    ) {
                        Button(
                            shape = MaterialTheme.shapes.medium,
                            enabled = state.isGenerateTRNContentValid,
                            colors = ButtonDefaults.buttonColors(
                                disabledBackgroundColor = MaterialTheme.colors.primary.copy(
                                    alpha = 0.2f
                                )
                            ),
                            modifier = Modifier
                                .width(screenWidth)
                                .height(buttonHeight),
                            //.padding(horizontal = 12.dp),

                            onClick = {
                                events(TransactionEvent.Generate)
                            }) {

                            Text(
                                "Generate",
                                color = MaterialTheme.colors.surface
                            )


                        }

                        Spacer(modifier = Modifier.padding(8.dp))


                        Spacer(modifier = Modifier.padding(12.dp))





                    }
                    Spacer(modifier = Modifier.padding(commonPadding))
                    if (!state.errorMessage.isNullOrEmpty()) {
                        scope.launch {
                            var result = snackBarHostState.showSnackbar(
                                message = state.errorMessage
                            )

                        }
                    }

                }
                if (state.isLoading) {
                    LoadingBoxWithParameters(
                        modifier = Modifier
                            .fillMaxSize()
                            .align(Alignment.TopCenter)
                            .background(color = MaterialTheme.colors.background.copy(alpha = 0.5f))
                    )
                }


            }
        }

}