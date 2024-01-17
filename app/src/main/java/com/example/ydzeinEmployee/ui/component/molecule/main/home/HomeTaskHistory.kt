package com.ydzmobile.employee.ui.component.molecule.main.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ydzeinemployeemonitoring.R
import com.ydzmobile.employee.ui.component.atom.text.YMTextWithDetail
import com.ydzmobile.employee.ui.theme.littleBoyBlue
import com.ydzmobile.employee.ui.theme.poppinsFont

@Composable
fun HomeTaskHistory(
    total: Int,
    target: Int,
    modifier: Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .fillMaxWidth()
            .background(littleBoyBlue)
            .padding(22.dp)
    ) {
        Text(
            text = stringResource(id =
            R.string.work_history),
            style = poppinsFont(size = 20, fontWeight = 600, color = Color.White)
        )

        YMTextWithDetail(title = stringResource(id = R.string.work_progress_total), value = total.toString())
        YMTextWithDetail(title = stringResource(id = R.string.work_progress_target), value = target.toString())
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeTaskHistoryPreview() {
    HomeTaskHistory(1, 2, Modifier)
}