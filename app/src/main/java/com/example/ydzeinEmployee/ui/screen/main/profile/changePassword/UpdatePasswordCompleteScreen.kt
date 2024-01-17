package com.ydzmobile.employee.ui.screen.main.profile.changePassword

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.ydzeinemployeemonitoring.R
import com.ydzmobile.employee.ui.component.atom.button.YMBorderedButton
import com.ydzmobile.employee.ui.navigation.Screen
import com.ydzmobile.employee.ui.theme.littleBoyBlue
import com.ydzmobile.employee.ui.theme.poppinsFont

@Composable
fun UpdatePasswordCompleteScreen(
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 46.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Spacer(Modifier.weight(1f))
        Image(
            modifier = Modifier
                .fillMaxWidth(),
            painter = painterResource(id = R.drawable.ic_success),
            contentDescription = null,
            contentScale = ContentScale.Fit
        )

        Text(
            text = stringResource(id = R.string.password_changed_title),
            style = poppinsFont(size = 20, fontWeight = 600),
            textAlign = TextAlign.Center
        )

        Text(
            text = stringResource(id = R.string.password_changed_subtitle),
            style = poppinsFont(size = 14, fontWeight = 400),
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.weight(2f))

        YMBorderedButton(
            modifier = Modifier
                .padding(bottom = 32.dp),
            title = stringResource(id = R.string.ok),
            backgroundColor = littleBoyBlue,
            foregroundColor = Color.White,
            enabled = true
        ) {
            navController.popBackStack()
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun UpdatePasswordCompleteScreenPreview() {
    UpdatePasswordCompleteScreen(rememberNavController())
}