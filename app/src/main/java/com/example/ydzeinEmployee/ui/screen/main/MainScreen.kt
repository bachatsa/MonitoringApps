package com.ydzmobile.employee.ui.screen.main

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.ydzeinemployeemonitoring.R
import com.ydzmobile.employee.ui.component.molecule.main.MainNavigationBar
import com.ydzmobile.employee.ui.navigation.Screen
import com.ydzmobile.employee.ui.navigation.bottomTabNav.BottomBarScreen
import com.ydzmobile.employee.ui.navigation.bottomTabNav.BottomNavGraph
import com.ydzmobile.employee.ui.theme.*

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    rootNavController: NavController
) {
    val navController = rememberNavController()
    val navStackBackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navStackBackEntry?.destination
    val currentRoutes = currentDestination?.route ?: ""
    val isHome = currentDestination?.hierarchy?.any { it.route == BottomBarScreen.Home.route } == true
    val isSubTarget = currentDestination?.hierarchy?.any { it.route == BottomBarScreen.NestedTarget.SubTarget.route} == true

    Scaffold(
        topBar =  { if (!isSubTarget) MainNavigationBar(currentRoutes) },
        bottomBar = { BottomBar(navController= navController, currentDestination = currentDestination) },
        floatingActionButton = {
            if (isHome) {
                AttendanceFloatingButton() {
                    rootNavController.navigate(Screen.Attendance.route)
                }
            }
        }
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(it)) {
            BottomNavGraph(navController = navController, rootNavController = rootNavController)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MainScreenPreview() {
    val nav = rememberNavController()
    MainScreen(nav)
}

@Composable
fun BottomBar(
    navController: NavHostController,
    currentDestination: NavDestination?
) {
    val screens = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.History,
        BottomBarScreen.NestedTarget,
        BottomBarScreen.Profile,
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(littleBoyBlue, RoundedCornerShape(6.dp)),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        screens.forEach { screen ->
            AddItem(
                modifier = Modifier.weight(1f),
                screen = screen,
                currentDestination = currentDestination,
                navController = navController
            )
        }
    }

}

@Composable
fun AddItem(
    modifier: Modifier,
    screen: BottomBarScreen,
    currentDestination: NavDestination?,
    navController: NavHostController,
    selected: Boolean = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
    backgroundColor: Color = if (selected) tuftsBlue else littleBoyBlue
) {
    Surface(
        modifier = modifier
            .height(66.dp),
        onClick = {
            navController.navigate(screen.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        },
        color = Color.Transparent
    ) {
        Row(
            modifier = Modifier
                .background(backgroundColor, RoundedCornerShape(6.dp))
                .padding(horizontal = 34.dp)
                .padding(top = 19.dp, bottom = 22.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                modifier = Modifier.size(34.dp),
                painter = painterResource(id = screen.icon),
                contentDescription = "tab icon",
                tint = if (selected) Color.White else Color.White.copy(0.75f)
            )
        }
    }
}

@Composable
private fun AttendanceFloatingButton(
    onClick: () -> Unit
) {
    Column {
        Button(
            modifier = Modifier
                .size(66.dp),
            onClick = onClick,
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(
                containerColor = tealBlue
            ),
            contentPadding = PaddingValues(0.dp)
        ) {
            Icon(
                modifier = Modifier.size(28.dp),
                painter = painterResource(id = R.drawable.ic_add),
                contentDescription = null,
                tint = Color.White
            )
        }

        Text(
            text = stringResource(id = R.string.attendance),
            style = poppinsFont(size = 20, fontWeight = 700, color = tealBlue)
        )
    }
}