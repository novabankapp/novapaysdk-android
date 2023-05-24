package com.novapay.ui.ui.transactions.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.Dp

@Composable
fun CustomTextField(
    value: TextFieldValue,
    placeholder: String,
    hasError: Boolean = false,
    onChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    enabled : Boolean = true,
    width: Dp,
    padding: Dp,
    shape: Shape,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    onFocused: () -> Unit = { print("focused") },
    onUnFocused: () -> Unit = { print("focused") }
) {

    TextField(
        value = value,

        textStyle = TextStyle(color = MaterialTheme.colors.onSurface,
            textDirection = TextDirection.Ltr),
        onValueChange = onChange,
        colors = TextFieldDefaults.textFieldColors(
            textColor = MaterialTheme.colors.onSurface,
            backgroundColor = Color.White.copy(alpha = 0.15f),
            cursorColor = MaterialTheme.colors.primary,
            focusedIndicatorColor = MaterialTheme.colors.primary,
            unfocusedIndicatorColor = MaterialTheme.colors.primary.copy(alpha = 0.2f),
            errorLabelColor = Color.Red,
            errorTrailingIconColor = Color.Red,
            errorLeadingIconColor = Color.Red,


            ),
        enabled = enabled,
        isError = hasError,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        placeholder = { Text(placeholder) },
        modifier = modifier
            .width(width)
            //.border(width = 1.dp,color = MaterialTheme.colors.primary.copy(0.8f))
            .onFocusChanged {
                if (it.isFocused) {
                    onFocused()
                } else {
                    onUnFocused()
                }


            }
            .padding(horizontal = padding)
            .clip(shape = shape)
    )


}