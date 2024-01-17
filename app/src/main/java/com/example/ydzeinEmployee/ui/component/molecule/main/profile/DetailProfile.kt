package com.ydzmobile.employee.ui.component.molecule.main.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ydzeinemployeemonitoring.R
import com.ydzmobile.employee.core.domain.model.auth.User
import com.ydzmobile.employee.ui.component.atom.text.YMTextWithDetail
import com.ydzmobile.employee.ui.theme.*

@Composable
fun DetailProfile(
    user: User?,
    onEditButtonPressed: () -> Unit,
    modifier: Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(littleBoyBlue)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
           Text(
               text = stringResource(id = R.string.detail_profile),
               style = poppinsFont(size = 20, fontWeight = 700, color = Color.White)
           )

            EditButton(onEditButtonPressed) 
        }

        YMTextWithDetail(
            title = stringResource(id = R.string.birthday_date),
            value = user?.dateBirth ?: "-",
            titleStyle = poppinsFont(size = 14, fontWeight = 700, color = Color.White),
            valueStyle = poppinsFont(size = 14, color = Color.White),
        )
        YMTextWithDetail(
            title = stringResource(id = R.string.phone_number),
            value = user?.phoneNumber ?: "-",
            titleStyle = poppinsFont(size = 14, fontWeight = 700, color = Color.White),
            valueStyle = poppinsFont(size = 14, color = Color.White),
        )
        YMTextWithDetail(
            title = stringResource(id = R.string.adress),
            value = user?.address ?: "-",
            titleStyle = poppinsFont(size = 14, fontWeight = 700, color = Color.White),
            valueStyle = poppinsFont(size = 14, color = Color.White),
        )
        YMTextWithDetail(
            title = stringResource(id = R.string.rt_rw),
            value = user?.rt ?: "-",
            titleStyle = poppinsFont(size = 14, fontWeight = 700, color = Color.White),
            valueStyle = poppinsFont(size = 14, color = Color.White),
        )
        YMTextWithDetail(
            title = stringResource(id = R.string.division),
            value = user?.division ?: "-",
            titleStyle = poppinsFont(size = 14, fontWeight = 700, color = Color.White),
            valueStyle = poppinsFont(size = 14, color = Color.White),
        )

        YMTextWithDetail(
            title = stringResource(id = R.string.blood_type),
            value = user?.bloodType ?: "-",
            titleStyle = poppinsFont(size = 14, fontWeight = 700, color = Color.White),
            valueStyle = poppinsFont(size = 14, color = Color.White),
        )
    }
}


@Composable
private fun EditButton(
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        color = Color.Transparent
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.edit_profile),
                style = poppinsFont(size = 10, fontWeight = 700, color = Color.White)
            )

            Icon(
                modifier = Modifier.size(16.dp),
                painter = painterResource(id = R.drawable.ic_pencil),
                contentDescription = null ,
                tint = Color.White
            )
        }
    }
}

@Preview
@Composable
private fun DetailProfilePreview() {
    DetailProfile(null, onEditButtonPressed = {}, modifier = Modifier)
}