package com.ydzmobile.employee.ui.component.molecule.main.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.ydzeinemployeemonitoring.R
import com.ydzmobile.employee.ui.theme.littleBoyBlue
import com.ydzmobile.employee.ui.theme.poppinsFont

@Composable
fun ProfileHeader(
    name: String,
    nik: String,
    imageUrl: String,
    modifier: Modifier
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .fillMaxWidth()
            .background(littleBoyBlue)
            .padding(horizontal = 16.dp, vertical = 22.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(22.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(true)
                .build(),
            placeholder = painterResource(R.drawable.ic_image_default),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(98.dp).clip(CircleShape)
        )

        Column(

        ) {
            Text(
                text = name,
                style = poppinsFont(size = 20, fontWeight = 700, color = Color.White)
            )
            Text(
                text = nik,
                style = poppinsFont(size = 16, fontWeight = 400, color = Color.White)
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun ProfileHeaderPreview() {
    ProfileHeader("name", "12345", "\"https://jagofoto.com/uploads/01_igfr7hd4.jpg\"", Modifier)
}