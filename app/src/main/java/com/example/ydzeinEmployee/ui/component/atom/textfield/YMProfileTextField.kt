package com.ydzmobile.employee.ui.component.atom.textfield

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
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
import com.ydzmobile.employee.ui.theme.littleBoyBlue
import com.ydzmobile.employee.ui.theme.poppinsFont

@Composable
fun YMProfileTextField(
    title: String,
    titleColor: Color = Color.Black,
    value: String,
    onValueChange: (String) -> Unit,
    hint: String = "",
    cornerRadius: Dp = 10.dp,
    foregroundColor: Color = darkJungleGreen,
    backgroundColor: Color = Color.White,
    keyboardType: KeyboardType = KeyboardType.Text,
    isEnable: Boolean = true,
    isPasswordField: Boolean = false,
    error: Boolean = false,
    errorMessage: String = "Error",
    icon: Painter? = null,
    isWithIcon: Boolean = icon != null
) {
    var isShowingPassword by remember {
        mutableStateOf(false)
    }
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    Column(
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(
            text = title,
            style = poppinsFont(size = 12, fontWeight = 600, color = titleColor),
        )
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            maxLines = 1,
            singleLine = true,
            textStyle = poppinsFont(size = 11, color = foregroundColor, fontWeight = 400),
            enabled = isEnable,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            interactionSource = interactionSource,
            visualTransformation = if (isPasswordField && !isShowingPassword) PasswordVisualTransformation() else VisualTransformation.None,

            modifier = Modifier
                .height(40.dp)
                .fillMaxWidth(1f)
                .clip(RoundedCornerShape(cornerRadius))
                .border(1.dp, littleBoyBlue, RoundedCornerShape(cornerRadius))
                .background(backgroundColor),
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        if (isWithIcon) {
                            Spacer(
                                modifier = Modifier.alpha(0f).size(14.dp),
                            )
                        }

                        if (value.isEmpty()) {
                            Text(
                                text = hint,
                                style = poppinsFont(
                                    size = 11,
                                    color = darkJungleGreen.copy(0.2f),
                                    fontWeight = 400
                                )
                            )
                        }

                        Spacer(modifier = Modifier.weight(1f))

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
                    }
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ){
                        if (isWithIcon) {
                            Image(
                                modifier = Modifier,
                                painter = icon!!,
                                contentDescription = null
                            )
                        }

                        innerTextField()
                    }
                }
            }
        )

        AnimatedVisibility(error) {
            Text(
                text = errorMessage,
                style = poppinsFont(size = 10, fontWeight = 400, color = Color.Black),
            )
        }
    }
}

@Preview(showBackground = false)
@Composable
private fun YMProfileTextFieldPreview() {
    YMProfileTextField(
        title = "asdas",
        value = "",
        hint = "asdas",
        isWithIcon = true,
        onValueChange = { _ -> }
    )
}