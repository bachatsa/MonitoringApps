package com.ydzmobile.employee.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ydzmobile.employee.ui.navigation.graph.RootNavGraph
import com.ydzmobile.employee.ui.theme.YdzMobileEmployeeKotlinTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            YdzMobileEmployeeKotlinTheme {
                navController = rememberNavController()
                RootNavGraph(navController = navController)
            }
        }
    }
}
//
//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    YdzMobileEmployeeKotlinTheme {
//    }
//}