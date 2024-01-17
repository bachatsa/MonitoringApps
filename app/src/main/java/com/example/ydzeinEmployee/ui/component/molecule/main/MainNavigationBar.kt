package com.ydzmobile.employee.ui.component.molecule.main

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ydzmobile.employee.ui.theme.poppinsFont

@Composable
fun MainNavigationBar(
    title: String
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 28.dp, vertical = 8.dp)
    ){
        Text(
            text = title,
            style = poppinsFont(size = 26, fontWeight = 700)
        )
    }
}