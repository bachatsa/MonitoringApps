package com.ydzmobile.employee.ui.screen.auth

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.UiComposable
import com.ydzmobile.employee.ui.component.molecule.auth.AuthNavigationBar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AuthScreenWrapper(
    title: String,
    content: @Composable @UiComposable () -> Unit,
) {
    Scaffold(
        topBar = {
            AuthNavigationBar(title = title)
        }
    ) {
        content()
    }
}