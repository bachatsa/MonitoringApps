package com.ydzmobile.employee.ui.screen.main.attendance.permit

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.ydzeinemployeemonitoring.R
import com.ydzmobile.employee.core.domain.enum.PermitType
import com.ydzmobile.employee.ui.component.molecule.main.attendance.AttendanceNavBar
import com.ydzmobile.employee.ui.component.molecule.main.attendance.permit.PermitCard
import com.ydzmobile.employee.ui.navigation.MAIN_GRAPH_ROUTE
import com.ydzmobile.employee.ui.navigation.Screen
import com.ydzmobile.employee.ui.theme.lightCarminePink

@Composable
fun PermitScreen(
    navController: NavController,
    type: PermitType? = null,
    onSickSubmitPressed: (List<String>, String) -> Unit,
    onOtherSubmitPressed: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(lightCarminePink),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        AttendanceNavBar(
            title = stringResource(id = R.string.permit).uppercase(),
            foregroundColor = Color.White
        ) {
            navController.popBackStack()
        }

        Image(
            painter = painterResource(id = R.drawable.ic_permit),
            contentDescription = null,
            modifier = Modifier.weight(1f),
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.height(34.dp))
        PermitCard(
            navController = navController,
            type = type,
            modifier = Modifier
                .weight(3f),
            onSickPressed = {
                navController.navigate(Screen.Permit.Sick.route)
            },
            onOtherPressed = {
                navController.navigate(Screen.Permit.Other.route)
            },
            onSickSubmitPressed = onSickSubmitPressed,
            onOtherSubmitPressed = onOtherSubmitPressed
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun PermitScreenPreview() {
    PermitScreen(rememberNavController(), null, {_, _ ->}, {_ ->})
}