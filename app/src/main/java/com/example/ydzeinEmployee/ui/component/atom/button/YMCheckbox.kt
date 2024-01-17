package com.ydzmobile.employee.ui.component.atom.button

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.ydzeinemployeemonitoring.R
import com.ydzmobile.employee.ui.theme.poppinsFont

@Composable
fun YMCheckbox(
    selected: Boolean,
    title: String,
    onPressed: () -> Unit
) {
    Button(
        modifier = Modifier.height(22.dp),
        onClick = onPressed,
        contentPadding = PaddingValues(0.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        ),
        shape = RectangleShape
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = if (selected)
                        painterResource(id = R.drawable.ic_radio_active) else
                        painterResource(id = R.drawable.ic_radio),
                contentDescription = null
            )

            Text(text = title, style = poppinsFont(size = 14, fontWeight = 400))

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}