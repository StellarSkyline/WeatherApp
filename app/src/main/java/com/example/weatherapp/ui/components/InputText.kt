package com.example.weatherapp.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.data.ProjectColors


@Composable
fun InputText(
    modifier: Modifier = Modifier,
    value:String = "",
    enabled: Boolean,
    onAction: () -> Unit,
    onValueChanged: (String) -> Unit,

) {
    val keyboardController = LocalSoftwareKeyboardController.current
    OutlinedTextField(
        modifier = modifier
            .padding(top = 5.dp, bottom = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = ProjectColors.secondary_bg,
            unfocusedContainerColor = ProjectColors.secondary_bg
        ),
        value = value,
        onValueChange = { onValueChanged(it) },
        label = { Text(text = "Search Location", color = ProjectColors.secondary_txt) },
        trailingIcon = {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "Search Icon",
                tint = Color.Black
            )
        },
        singleLine = true,
        textStyle = TextStyle(
            fontSize = 18.sp,
            color = ProjectColors.black
        ),
        enabled = enabled,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions {
            onAction()
            keyboardController?.hide()
        }
    )

}