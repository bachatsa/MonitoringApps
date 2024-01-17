package com.ydzmobile.employee.ui.component.molecule.main.attendanceHistory

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ydzmobile.employee.core.domain.enum.AttendanceType
import com.ydzmobile.employee.core.domain.model.AttendanceHistoryModel
import com.ydzmobile.employee.ui.theme.poppinsFont

@Composable
fun AttendanceHistoryCell(
    data: AttendanceHistoryModel
) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .fillMaxWidth()
            .background(data.getBackgroundColor())
            .padding(horizontal = 16.dp, vertical = 18.dp),
        horizontalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Image(
            modifier = Modifier
                .background(Color.White, RoundedCornerShape(20.dp))
                .padding(8.dp)
                .size(82.dp),
            painter = painterResource(id = data.getIcon()),
            contentDescription = null
        )

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = data.getTitle(),
                style = poppinsFont(size = 20, fontWeight = 700, color = Color.White)
            )

            Text(
                text = data.getSubTitle(),
                style = poppinsFont(size = 16, fontWeight = 700, color = Color.White)
            )

            Text(
                text = data.getDescription(),
                style = poppinsFont(size = 14, color = Color.White),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Preview
@Composable
private fun AttendanceHistoryCellPreview() {
    val dummy = AttendanceHistoryModel(
        "1",
        "",
        "2024-01-01 07:40:00",
        "2024-01-01 07:00:00",
        AttendanceType.SICK,
        listOf("Demam", "Pilek", "Batuk"),
        reasonOfPermission = "Permition Desc Permition Desc Permition Desc Permition Desc Permition Desc"
    )
    AttendanceHistoryCell(data = dummy)
}