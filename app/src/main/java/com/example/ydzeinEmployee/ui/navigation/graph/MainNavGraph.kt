package com.ydzmobile.employee.ui.navigation.graph

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.ydzmobile.employee.core.domain.enum.PermitType
import com.ydzmobile.employee.core.domain.model.monitor.toTarget
import com.ydzmobile.employee.core.viewModel.AttendanceViewModel
import com.ydzmobile.employee.core.viewModel.ChangePasswordViewModel
import com.ydzmobile.employee.core.viewModel.PermitViewModel
import com.ydzmobile.employee.core.viewModel.ProfileViewModel
import com.ydzmobile.employee.core.viewModel.UpdateWorkProgressViewModel
import com.ydzmobile.employee.ui.component.molecule.main.attendance.AttendanceNavBar
import com.ydzmobile.employee.ui.navigation.MAIN_GRAPH_ROUTE
import com.ydzmobile.employee.ui.navigation.Screen
import com.ydzmobile.employee.ui.navigation.TARGET_ID_ARG_MODEL
import com.ydzmobile.employee.ui.screen.main.MainScreen
import com.ydzmobile.employee.ui.screen.main.attendance.AttendanceScreen
import com.ydzmobile.employee.ui.screen.main.attendance.permit.PermitScreen
import com.ydzmobile.employee.ui.screen.main.profile.changePassword.ChangePasswordScreen
import com.ydzmobile.employee.ui.screen.main.profile.changePassword.UpdatePasswordCompleteScreen
import com.ydzmobile.employee.ui.screen.main.profile.updateProfile.UpdateProfileScreen
import com.ydzmobile.employee.ui.screen.main.target.UpdateWorkProgressScreen

fun NavGraphBuilder.mainScreenNavGraph(
    navController: NavHostController
) {
    navigation(
        startDestination = Screen.Main.route,
        route = MAIN_GRAPH_ROUTE
    ) {
        composable(Screen.Main.route) {
            MainScreen(rootNavController = navController)
        }


        composable(
            route = Screen.UpdateWorkProgress.route,
            arguments = listOf(
                navArgument(TARGET_ID_ARG_MODEL) {
                    type = NavType.StringType
                }
            )
        )
        {entry ->
            val viewModel = hiltViewModel<UpdateWorkProgressViewModel>()
            val uiState by viewModel.uiState.collectAsState()
            val target = entry.arguments?.getString(TARGET_ID_ARG_MODEL) ?: ""
            viewModel.setupUsingModel(target = target.toTarget()!!
            )
            UpdateWorkProgressScreen(navController = navController, uiState = uiState, viewModel::onTargetBeenDone, viewModel::onSelectedImage, viewModel::updateTarget)
        }

        composable(route = Screen.Attendance.route) {
            val viewModel = hiltViewModel<AttendanceViewModel>()
            val locationState by viewModel.locationState.collectAsState()
            val uiState by viewModel.uiState.collectAsState()

            AttendanceScreen(
                navController,
                uiState,
                locationState,
                viewModel::handle,
                viewModel::onPresentPressed,
                viewModel::clearToast
            )
        }

        composable(route = Screen.Permit.route) {
            val viewModel = hiltViewModel<PermitViewModel>()
            PermitScreen(
                navController,
                null,
                viewModel::onSickSubmitPressed,
                viewModel::onPermitPressed
            )
        }

        composable(route = Screen.Permit.Sick.route) {
            val viewModel = hiltViewModel<PermitViewModel>()
            PermitScreen(
                navController,
                type = PermitType.SICK,
                viewModel::onSickSubmitPressed,
                viewModel::onPermitPressed
            )
        }

        composable(route = Screen.Permit.Other.route) {
            val viewModel = hiltViewModel<PermitViewModel>()
            PermitScreen(
                navController,
                type = PermitType.OTHER,
                viewModel::onSickSubmitPressed,
                viewModel::onPermitPressed
            )
        }

        composable(route = Screen.ChangePassword.route) {
            val viewModel = hiltViewModel<ChangePasswordViewModel>()
            val uiState by viewModel.uiState.collectAsState()
            Scaffold(
                topBar = {
                    AttendanceNavBar(title = "") {
                        navController.popBackStack()
                    }
                }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it)
                ) {
                    ChangePasswordScreen(
                        navController,
                        uiState,
                        viewModel::onPasswordChanged,
                        viewModel::onPasswordConfirmChanged,
                        viewModel::changePassword
                    )
                }
            }
        }
    }

    composable(
        route = Screen.UpdateProfile.route
    ) {
        val viewModel = hiltViewModel<ProfileViewModel>()
        val uiState by viewModel.uiState.collectAsState()
        UpdateProfileScreen(
            navController = navController,
            uiState = uiState,
            onAddressChanged = viewModel::onAddressChanged,
            onRtChanged = viewModel::onRtChanged,
            onBirthDateChanged = viewModel::onBirthDateChanged,
            onBloodTypeChanged = viewModel::onBloodTypeChanged,
            onDivisionChanged = viewModel::onDivisionChanged,
            onNameChanged = viewModel::onNameChanged,
            onPhoneNumberChanged = viewModel::onPhoneNumberChanged,
            onUpdateProfilePressed = viewModel::updateProfile,
            onSelectedImage = viewModel::onSelectedImage,
        )
    }

    composable(
        route = Screen.UpdatePasswordComplete.route
    ) {
        UpdatePasswordCompleteScreen(navController = navController)
    }
}