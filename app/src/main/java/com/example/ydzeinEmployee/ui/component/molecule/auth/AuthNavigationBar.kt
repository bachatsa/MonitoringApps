package com.ydzmobile.employee.ui.component.molecule.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ydzeinemployeemonitoring.R
import com.ydzmobile.employee.ui.theme.littleBoyBlue
import com.ydzmobile.employee.ui.theme.poppinsFont

@Composable
fun AuthNavigationBar(
    title: String
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().padding(24.dp)
    ) {
        Image(
            modifier = Modifier.size(42.dp),
            painter = painterResource(id = R.drawable.ic_auth_profile),
            contentDescription = null,
            contentScale = ContentScale.Fit
        )

        Text(
            text = title,
            style = poppinsFont(size = 20, color = littleBoyBlue, fontWeight = 700)
        )
    }
}

@Preview
@Composable
private fun AuthNavigationBarPreview() {
    AuthNavigationBar(title = "Monitoring")
}