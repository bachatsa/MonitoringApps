package com.ydzmobile.employee.ui.component.molecule.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.ydzmobile.employee.ui.theme.poppinsFont

@Composable
fun AuthBanner(
    painter: Painter,
    title: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Image(
            modifier = Modifier.size(222.dp),
            painter = painter,
            contentDescription = null
        )

        Text(
            text = title,
            style = poppinsFont(size = 30, fontWeight = 700)
        )
    }
}