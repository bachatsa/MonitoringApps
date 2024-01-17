package com.ydzmobile.employee.ui.screen.auth.login

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.ydzeinemployeemonitoring.R
import com.ydzmobile.employee.core.data.AuthState
import com.ydzmobile.employee.core.viewModel.LoginUIState
import com.ydzmobile.employee.ui.component.atom.button.YMBorderedButton
import com.ydzmobile.employee.ui.component.atom.textfield.YMTextField
import com.ydzmobile.employee.ui.component.molecule.auth.AuthBanner
import com.ydzmobile.employee.ui.navigation.MAIN_GRAPH_ROUTE
import com.ydzmobile.employee.ui.navigation.Screen
import com.ydzmobile.employee.ui.theme.littleBoyBlue

@Composable
fun LoginScreen(
    navController: NavController,
    uiState: LoginUIState,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onLoginPressed: () -> Unit,
) {
    val context = LocalContext.current

    LaunchedEffect(key1 = uiState.loginState) {
        if (uiState.loginState == AuthState.LOGIN) {
            navController.popBackStack()
            navController.navigate(MAIN_GRAPH_ROUTE)
        } else {
            Toast.makeText(
                context,
                uiState.errorMessage,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 38.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Spacer(modifier = Modifier.weight(1f))
        AuthBanner(
            painterResource(id = R.drawable.ic_signin),
            stringResource(id = R.string.loginScreen_title)
        )

        YMTextField(
            value = uiState.email,
            hint = "Email",
            onValueChange = onEmailChanged,
            borderColor = littleBoyBlue,
            foregroundColor = littleBoyBlue
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            YMTextField(
                value = uiState.password,
                hint = stringResource(id = R.string.password),
                onValueChange = onPasswordChanged,
                borderColor = littleBoyBlue,
                foregroundColor = littleBoyBlue,
                isPasswordField = true
            )

            ClickableText(
                modifier = Modifier,
                text = AnnotatedString(stringResource(id = R.string.forgotPassword_title)),
                onClick = {
                    navController.navigate(Screen.ForgotPassword.route)
                }
            )
        }

        YMBorderedButton(
            modifier = Modifier.padding(horizontal = 36.dp),
            title = stringResource(id = R.string.login_button_title),
            titleSize = 16,
            backgroundColor = littleBoyBlue,
            buttonHeight = 42,
            foregroundColor = Color.White,
            cornerRadius = 16,
            onClick = onLoginPressed)
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Preview(
    showBackground = true
)
@Composable
private fun LoginScreenPreview() {
    val nav = rememberNavController()

    LoginScreen(nav, uiState = LoginUIState(
        email = "",
        password = ""
    ), {}, {}, {})
}