package com.ydzmobile.employee.ui.navigation

const val ROOT_GRAPH_ROUTE = "root"
const val MAIN_GRAPH_ROUTE = "main"
const val AUTH_GRAPH_ROUTE = "auth"


const val TARGET_ARGUMENT_KEY = "ARG_TARGET"
const val TARGET_ID_ARG_MODEL = "TARGET_ID_ARG_MODEL"
sealed class Screen(
    val route: String
) {
    object Main: Screen("main_route")
    object Login: Screen( "login_route")
    object ForgotPassword: Screen( "forgot_password_route")
    object Home: Screen( "HOME")
    object History: Screen( "Riwayat")
    object RootTarget: Screen( "ROOT_TARGET")
    object Target: Screen( "TARGET")
    object SubTarget: Screen( "SUB_TARGET/{${TARGET_ARGUMENT_KEY}}")
    object Profile: Screen( "Profile")
    object ChangePassword : Screen("change_password_route")
    object UpdateWorkProgress: Screen( "update_work_progress/{$TARGET_ID_ARG_MODEL}")

    object Attendance: Screen( "attendance_route")

    object Permit : Screen("permit_route") {
        object Other : Screen("permit_other_route")
        object Sick : Screen("permit_sick_route")
    }

    object UpdateProfile : Screen("update_profile_route")
    object UpdatePasswordComplete : Screen("update_password_complete_route")
}