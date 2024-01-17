package com.ydzmobile.employee.ui.navigation.bottomTabNav

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.ydzmobile.employee.core.viewModel.AttendanceHistoryViewModel
import com.ydzmobile.employee.core.viewModel.HomeViewModel
import com.ydzmobile.employee.core.viewModel.ProfileViewModel
import com.ydzmobile.employee.core.viewModel.TargetViewModel
import com.ydzmobile.employee.ui.navigation.TARGET_ARGUMENT_KEY
import com.ydzmobile.employee.ui.screen.main.attendanceHisotry.AttendanceHistoryScreen
import com.ydzmobile.employee.ui.screen.main.home.HomeScreen
import com.ydzmobile.employee.ui.screen.main.profile.ProfileScreen
import com.ydzmobile.employee.ui.screen.main.target.SubTargetScreen
import com.ydzmobile.employee.ui.screen.main.target.TargetScreen

@Composable
fun BottomNavGraph(
    navController: NavHostController,
    rootNavController: NavController
) {
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Home.route
    ) {
        composable(route = BottomBarScreen.Home.route)
        {
            val viewModel = hiltViewModel<HomeViewModel>()
            val uiState by viewModel.uiState.collectAsState()

            HomeScreen(homeUIState = uiState, viewModel::getAllTargets)
        }

        composable(route = BottomBarScreen.History.route)
        {
            val viewModel = hiltViewModel<AttendanceHistoryViewModel>()
            val uiState by viewModel.uiState.collectAsState()

            AttendanceHistoryScreen(attendanceHistoryUIState = uiState, viewModel::getHistories)
        }

        targetScreenNavGraph(navController, rootNavController)

        composable(route = BottomBarScreen.Profile.route)
        {
            val viewModel = hiltViewModel<ProfileViewModel>()
            val uiState by viewModel.uiState.collectAsState()

            ProfileScreen(navController, rootNavController, profileUIState = uiState, viewModel::logout, viewModel::getCurrentUser)
        }
    }
}

fun NavGraphBuilder.targetScreenNavGraph(
    navController: NavHostController,
    rootNavController: NavController
) {
    navigation(
        route = BottomBarScreen.NestedTarget.route,
        startDestination = BottomBarScreen.NestedTarget.Target.route
    ){
        composable(route = BottomBarScreen.NestedTarget.Target.route)
        {
            val viewModel = hiltViewModel<TargetViewModel>()
            val uiState by viewModel.uiState.collectAsState()
            TargetScreen(navController = navController, targetUIState = uiState, viewModel::getMyTargets)
        }
        composable(
            route = BottomBarScreen.NestedTarget.SubTarget.route,
            arguments = listOf(
                navArgument(TARGET_ARGUMENT_KEY) {
                    type = NavType.StringType
                }
            )
        ){ entry ->
            val targetName = entry.arguments?.getString(TARGET_ARGUMENT_KEY)
            val viewModel = hiltViewModel<TargetViewModel>()
            val uiState by viewModel.uiState.collectAsState()
            Log.d("NestedTarget.SubTarget.route", targetName!!)
            viewModel.setTargetName(targetName ?: "")
            SubTargetScreen(navController = rootNavController, uiState)
        }
    }
}