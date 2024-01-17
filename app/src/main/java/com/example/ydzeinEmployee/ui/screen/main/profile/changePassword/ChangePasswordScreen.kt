package com.ydzmobile.employee.ui.screen.main.profile.changePassword

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.ydzeinemployeemonitoring.R
import com.ydzmobile.employee.core.viewModel.ChangePasswordUIState
import com.ydzmobile.employee.ui.component.atom.button.YMBorderedButton
import com.ydzmobile.employee.ui.component.atom.textfield.YMProfileTextField
import com.ydzmobile.employee.ui.navigation.Screen
import com.ydzmobile.employee.ui.theme.littleBoyBlue
import com.ydzmobile.employee.ui.theme.poppinsFont

@Composable
fun ChangePasswordScreen(
    navController: NavController,
    uiState: ChangePasswordUIState,
    onPasswordChanged: (String) -> Unit,
    onPasswordConfirmChanged: (String) -> Unit,
    onChangePasswordPressed: () -> Unit
) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(27.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = stringResource(id = R.string.change_password_title),
                style = poppinsFont(size = 20, fontWeight = 700)
            )

            Text(
                text = stringResource(id = R.string.change_password_subtitle),
                style = poppinsFont(size = 12, fontWeight = 500)
            )
        }


        YMProfileTextField(
            title = stringResource(id = R.string.password),
            hint = stringResource(id = R.string.password),
            errorMessage = stringResource(id = R.string.password_error),
            isPasswordField = true,
            value = uiState.password,
            error = uiState.passwordError,
            onValueChange = onPasswordChanged
        )

        YMProfileTextField(
            title = stringResource(id = R.string.password_confirm),
            hint = stringResource(id = R.string.password_confirm),
            errorMessage = stringResource(id = R.string.password_confirm_error),
            isPasswordField = true,
            value = uiState.passwordConfirm,
            error = uiState.passwordConfirmError,
            onValueChange = onPasswordConfirmChanged
        )

        YMBorderedButton(
            modifier = Modifier
                .padding(top = 27.dp),
            title = stringResource(id = R.string.changePassword_button_title),
            foregroundColor = Color.White,
            backgroundColor = littleBoyBlue,
            strokeWidth = 0,
            enabled = uiState.isValidForm
        ) {
            onChangePasswordPressed()
        }

        LaunchedEffect(key1 = uiState.isSuccess) {
            if (uiState.isSuccess) {
                navController.popBackStack()
                navController.navigate(Screen.UpdatePasswordComplete.route)
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun ChangePasswordScreenPreview() {
    ChangePasswordScreen(rememberNavController(), ChangePasswordUIState(), {}, {}) {

    }
}