package com.ydzmobile.employee.ui.component.atom.text

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.ydzmobile.employee.ui.theme.poppinsFont

@Composable
fun YMTextWithDetail(
    title: String,
    titleStyle: TextStyle = poppinsFont(size = 16, color = Color.White),
    value: String,
    valueStyle: TextStyle = poppinsFont(size = 16, color = Color.White),
) {
    Row(
       horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = title, style = titleStyle)

        Spacer(modifier = Modifier.weight(1f))

        Text(text = value, style = valueStyle)
    }
}