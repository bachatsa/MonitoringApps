package com.ydzmobile.employee.ui.screen.auth.forgotPassword

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.ydzeinemployeemonitoring.R
import com.ydzmobile.employee.core.extension.isValidEmail
import com.ydzmobile.employee.core.viewModel.ForgotPasswordUIState
import com.ydzmobile.employee.ui.component.atom.button.YMBorderedButton
import com.ydzmobile.employee.ui.component.atom.textfield.YMTextField
import com.ydzmobile.employee.ui.component.molecule.auth.AuthBanner
import com.ydzmobile.employee.ui.navigation.Screen
import com.ydzmobile.employee.ui.theme.littleBoyBlue
import com.ydzmobile.employee.ui.theme.poppinsFont

@Composable
fun ForgotPasswordScreen(
    navController: NavController,
    uiState: ForgotPasswordUIState,
    onEmailChanged: (String) -> Unit,
    onSubmitPressed: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 38.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Spacer(modifier = Modifier.weight(1f))
        AuthBanner(
            painterResource(id = R.drawable.ic_forgot_password),
            stringResource(id = R.string.forgotPasswordScreen_title)
        )

        YMTextField(
            value = uiState.email,
            hint = "Email",
            onValueChange = onEmailChanged,
            foregroundColor = littleBoyBlue,
            borderColor = littleBoyBlue
        )

        YMBorderedButton(
            modifier = Modifier.padding(horizontal = 36.dp),
            title = stringResource(id = R.string.forgotPassword_button_title),
            titleSize = 16,
            backgroundColor = littleBoyBlue,
            buttonHeight = 42,
            foregroundColor = Color.White,
            cornerRadius = 16,
            enabled = uiState.email.isValidEmail(),
            onClick = onSubmitPressed)

        ClickableText(
            modifier = Modifier,
            text = AnnotatedString(stringResource(id = R.string.back_to_signin_button_title)),
            style = poppinsFont(size = 14),
            onClick = {
                navController.popBackStack()
                navController.navigate(Screen.Login.route)
            }
        )

        LaunchedEffect(key1 = uiState.isSuccess) {
            if (uiState.isSuccess) {
                navController.popBackStack()
            }
        }


        Spacer(modifier = Modifier.weight(1f))
    }
}

@Preview(showBackground = true)
@Composable
private fun ForgotPasswordScreenPreview() {
    val navController = rememberNavController()
    ForgotPasswordScreen(
        navController = navController,
        uiState = ForgotPasswordUIState(email = ""),
        {}, {}
    )
}