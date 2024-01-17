package com.ydzmobile.employee.ui.component.atom.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ydzmobile.employee.ui.theme.darkJungleGreen
import com.ydzmobile.employee.ui.theme.littleBoyBlue
import com.ydzmobile.employee.ui.theme.poppinsFont

@Composable
fun YMBorderedButton(
    modifier: Modifier,
    title: String,
    titleSize: Int = 20,
    icon: Painter? = null,
    foregroundColor: Color = darkJungleGreen,
    buttonHeight: Int = 60,
    backgroundColor: Color = Color.White,
    strokeWidth: Int = 0,
    strokeColor: Color = littleBoyBlue,
    cornerRadius: Int = 8,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    val cardModifier = modifier
        .height(buttonHeight.dp)
        .fillMaxWidth()
    Card(
        modifier = cardModifier,
        shape = RoundedCornerShape(cornerRadius.dp),
        border = BorderStroke(strokeWidth.dp, strokeColor),
    ) {
        Button(
            modifier = Modifier.fillMaxSize(),
            shape = RoundedCornerShape(cornerRadius),
            colors = ButtonDefaults.buttonColors(
                containerColor = backgroundColor,
                disabledContainerColor = backgroundColor.copy(0.5f)
            ),
            onClick = { onClick() },
            contentPadding = PaddingValues(0.dp),
            enabled = enabled
        ) {
            Text(
                text = title,
                style = poppinsFont(size = titleSize, fontWeight = 700),
                color = foregroundColor,
                maxLines = 1
            )
        }
    }
}

@Preview
@Composable
fun YMBorderedButtonPreview() {
    YMBorderedButton(
        modifier = Modifier
            .width(400.dp),
        title = "Filter (0)",
    ) {

    }
}