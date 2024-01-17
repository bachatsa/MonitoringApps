package com.ydzmobile.employee.ui.component.molecule.main.attendance

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.ydzeinemployeemonitoring.R
import com.ydzmobile.employee.ui.theme.darkJungleGreen
import com.ydzmobile.employee.ui.theme.poppinsFont

@Composable
fun AttendanceNavBar(
    modifier: Modifier = Modifier,
    title: String,
    foregroundColor: Color = darkJungleGreen,
    onBackPressed: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 26.dp, top = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBackPressed) {
            Icon(
                modifier = Modifier
                    .size(22.dp),
                painter = painterResource(id = R.drawable.ic_arrow_left),
                contentDescription = null,
                tint = foregroundColor
            )
        }

        Text(
            text = title,
            style = poppinsFont(size = 24, fontWeight = 700, color = foregroundColor)
        )
    }
}