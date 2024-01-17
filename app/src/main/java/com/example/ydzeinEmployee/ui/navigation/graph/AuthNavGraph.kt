package com.ydzmobile.employee.ui.navigation.graph

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.ydzeinemployeemonitoring.R
import com.ydzmobile.employee.core.viewModel.ForgotPasswordViewModel
import com.ydzmobile.employee.core.viewModel.LoginViewModel
import com.ydzmobile.employee.ui.navigation.AUTH_GRAPH_ROUTE
import com.ydzmobile.employee.ui.navigation.Screen
import com.ydzmobile.employee.ui.screen.auth.AuthScreenWrapper
import com.ydzmobile.employee.ui.screen.auth.forgotPassword.ForgotPasswordScreen
import com.ydzmobile.employee.ui.screen.auth.login.LoginScreen


fun NavGraphBuilder.authNavGraph(
    navController: NavHostController,
) {
    navigation(
        startDestination = Screen.Login.route,
        route = AUTH_GRAPH_ROUTE
    ) {

        composable(Screen.Login.route) {
            val viewModel = hiltViewModel<LoginViewModel>()
            val uiState by viewModel.uiState.collectAsState()

            AuthScreenWrapper(
                title = stringResource(id = R.string.auth_navigation_title)
            ) {
                LoginScreen(
                    navController = navController,
                    uiState = uiState,
                    onEmailChanged = viewModel::onEmailChanged,
                    onPasswordChanged = viewModel::onPasswordChanged,
                    onLoginPressed = viewModel::loginUser
                )
            }
        }

        composable(Screen.ForgotPassword.route) {
            val viewModel = hiltViewModel<ForgotPasswordViewModel>()
            val uiState by viewModel.uiState.collectAsState()

            AuthScreenWrapper(
                title = stringResource(id = R.string.auth_navigation_title)
            ) {
                ForgotPasswordScreen(
                    navController = navController,
                    uiState = uiState,
                    onEmailChanged = viewModel::onEmailChanged,
                    viewModel::onSubmitPressed
                )
            }
        }
    }
}