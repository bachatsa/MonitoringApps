package com.ydzmobile.employee.ui.navigation.bottomTabNav

import com.example.ydzeinemployeemonitoring.R
import com.ydzmobile.employee.ui.navigation.Screen

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: Int,
) {
    object Home: BottomBarScreen(
        route = Screen.Home.route,
        title = Screen.Home.route,
        icon = R.drawable.ic_home
    )

    object History: BottomBarScreen(
        route = Screen.History.route,
        title = Screen.History.route,
        icon = R.drawable.ic_history
    )

    object NestedTarget: BottomBarScreen(
        route = Screen.RootTarget.route,
        title = Screen.RootTarget.route,
        icon = R.drawable.ic_target
    ) {
        object Target : BottomBarScreen(
            route = Screen.Target.route,
            title = Screen.Target.route,
            icon = R.drawable.ic_target
        )
        object SubTarget : BottomBarScreen(
            route = Screen.SubTarget.route,
            title = Screen.SubTarget.route,
            icon = R.drawable.ic_target
        )
    }

    object Profile: BottomBarScreen(
        route = Screen.Profile.route,
        title = Screen.Profile.route,
        icon = R.drawable.ic_profile
    )

}