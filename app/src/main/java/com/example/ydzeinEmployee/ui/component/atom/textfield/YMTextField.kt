package com.ydzmobile.employee.ui.component.atom.textfield

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.ydzeinemployeemonitoring.R
import com.ydzmobile.employee.ui.theme.darkJungleGreen
import com.ydzmobile.employee.ui.theme.poppinsFont

@Composable
fun YMTextField(
    value: String,
    onValueChange: (String) -> Unit,
    hint: String = "",
    cornerRadius: Dp = 16.dp,
    foregroundColor: Color = darkJungleGreen,
    backgroundColor: Color = Color.White,
    borderColor: Color = darkJungleGreen,
    isPasswordField: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Email,
) {
    var isShowingPassword by remember {
        mutableStateOf(false)
    }
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    TextField(
        value = value,
        onValueChange = onValueChange,
        maxLines = 1,
        singleLine = true,
        placeholder = {
            Text(
                text = hint,
                style = poppinsFont(size = 16, color = foregroundColor, fontWeight = 700)
            )
        },
        textStyle = poppinsFont(size = 16, color = foregroundColor, fontWeight = 700),
        shape = RoundedCornerShape(cornerRadius),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            cursorColor = Color.Black
        ),
        visualTransformation = if (isPasswordField && !isShowingPassword) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = KeyboardOptions(keyboardType = if (isPasswordField) KeyboardType.Password else keyboardType),
        suffix = {
            if (isFocused && isPasswordField) {
                IconButton(onClick = {
                    isShowingPassword = !isShowingPassword
                }) {
                    Image(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(id = if (isShowingPassword) R.drawable.ic_slashed_eye else R.drawable.ic_eye),
                        contentDescription = null,
                        contentScale = ContentScale.Fit
                    )
                }
            }
        },
        interactionSource = interactionSource,
        modifier = Modifier
            .fillMaxWidth(1f)
            .clip(RoundedCornerShape(cornerRadius))
            .border(2.dp, color = borderColor, shape = RoundedCornerShape(cornerRadius))
            .background(backgroundColor),
    )
}

@Preview(showBackground = true)
@Composable
private fun YMTextFieldPreview() {
    YMTextField(value = "das", onValueChange = {}, isPasswordField = true)
}