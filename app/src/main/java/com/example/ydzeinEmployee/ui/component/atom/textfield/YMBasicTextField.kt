package com.ydzmobile.employee.ui.component.atom.textfield

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ydzmobile.employee.ui.theme.darkJungleGreen
import com.ydzmobile.employee.ui.theme.outerSpace
import com.ydzmobile.employee.ui.theme.poppinsFont

@Composable
fun YMBasicTextField(
    title: String,
    titleColor: Color = Color.White,
    value: String,
    onValueChange: (String) -> Unit,
    hint: String = "",
    cornerRadius: Dp = 6.dp,
    foregroundColor: Color = darkJungleGreen,
    backgroundColor: Color = Color.White,
    keyboardType: KeyboardType = KeyboardType.Text,
    isEnable: Boolean = true,
    isKeepBackgroundColor: Boolean = false
) {
    val interactionSource = remember { MutableInteractionSource() }

    Column(
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(
            text = title,
            style = poppinsFont(size = 16, fontWeight = 700, color = titleColor),
        )
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            maxLines = 1,
            singleLine = true,
            textStyle = poppinsFont(size = 14, color = foregroundColor, fontWeight = 700),
            enabled = isEnable,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            interactionSource = interactionSource,
            modifier = Modifier
                .height(40.dp)
                .fillMaxWidth(1f)
                .clip(RoundedCornerShape(cornerRadius))
                .background(backgroundColor),
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .background(if (isEnable || isKeepBackgroundColor) backgroundColor else outerSpace.copy(0.2f))
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                ) {
                    if (value.isEmpty()) {
                        Text(
                            text = hint,
                            style = poppinsFont(
                                size = 14,
                                color = darkJungleGreen.copy(0.2f),
                                fontWeight = 700
                            )
                        )
                    }
                    innerTextField()
                }
            }
        )
    }
}

@Preview(showBackground = false)
@Composable
private fun YMBasicTextFieldPreview() {
    YMBasicTextField(
        title = "asdas",
        value = "asdas",
        onValueChange = { _ -> }
    )
}