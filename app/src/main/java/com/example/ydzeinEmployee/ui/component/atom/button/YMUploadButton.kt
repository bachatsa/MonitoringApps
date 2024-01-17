package com.ydzmobile.employee.ui.component.atom.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.ydzeinemployeemonitoring.R
import com.ydzmobile.employee.ui.theme.darkJungleGreen
import com.ydzmobile.employee.ui.theme.outerSpace
import com.ydzmobile.employee.ui.theme.poppinsFont

@Composable
fun YMUploadButton(
    title: String,
    titleColor: Color = Color.White,
    selectedItemName: String,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(
            text = title,
            style = poppinsFont(size = 16, fontWeight = 700, color = titleColor),
        )
        Button(
            modifier = Modifier
                .height(40.dp),
            onClick = onClick,
            contentPadding = PaddingValues(0.dp),
            enabled = enabled,
            shape = RoundedCornerShape(6.dp),
        ) {
            Row(
                modifier = Modifier
                    .background(if (enabled) Color.White else Color.White.copy(0.8f))
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
            ) {
                Text(
                    text = selectedItemName.ifEmpty { stringResource(id = R.string.upload) },
                    style = poppinsFont(
                        size = 12,
                        color = darkJungleGreen.copy(if (selectedItemName.isEmpty()) 0.4f else 1f),
                        fontWeight = 500
                    )
                )

                Spacer(modifier = Modifier.weight(1f))

                Icon(
                    painter = painterResource(id = R.drawable.ic_upload),
                    contentDescription = null,
                    tint = darkJungleGreen.copy(0.4f),
                )
            }
        }
    }
}