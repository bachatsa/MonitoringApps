package com.ydzmobile.employee.ui.screen.main.attendance

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.ydzeinemployeemonitoring.R
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.maps.model.LatLng
import com.ydzmobile.employee.core.extension.hasLocationPermission
import com.ydzmobile.employee.core.viewModel.AttendanceUIState
import com.ydzmobile.employee.core.viewModel.LocationState
import com.ydzmobile.employee.core.viewModel.PermissionEvent
import com.ydzmobile.employee.ui.component.atom.button.YMBorderedButton
import com.ydzmobile.employee.ui.component.molecule.main.attendance.AttendanceMaps
import com.ydzmobile.employee.ui.component.molecule.main.attendance.AttendanceNavBar
import com.ydzmobile.employee.ui.navigation.Screen
import com.ydzmobile.employee.ui.theme.appleGreen
import com.ydzmobile.employee.ui.theme.englishVermillion
import com.ydzmobile.employee.ui.theme.poppinsFont

@SuppressLint("MissingPermission")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun AttendanceScreen(
    navController: NavController,
    uiState: AttendanceUIState,
    locationState: LocationState,
    onPermissionChanged:  (PermissionEvent) -> Unit,
    onPresentPressed: () -> Unit,
    onFinishShowToast: () -> Unit
) {
    val context = LocalContext.current
    val density = LocalDensity.current
    val permissionState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )

    LaunchedEffect(key1 = uiState.toastMessages) {
        if (uiState.toastMessages.isNotEmpty()) {
            Toast.makeText(
                context,
                uiState.toastMessages,
                Toast.LENGTH_SHORT
            ).show()
            onFinishShowToast()
        }
    }

    LaunchedEffect(!context.hasLocationPermission()) {
        permissionState.launchMultiplePermissionRequest()
    }

    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess){
            navController.popBackStack()
        }
    }

    when {
        permissionState.allPermissionsGranted -> {
            LaunchedEffect(Unit) {
                onPermissionChanged(PermissionEvent.Granted)
            }
        }

        permissionState.shouldShowRationale -> {
            RationaleAlert(onDismiss = { }) {
                permissionState.launchMultiplePermissionRequest()
            }
        }

        !permissionState.allPermissionsGranted && !permissionState.shouldShowRationale -> {
            LaunchedEffect(Unit) {
                onPermissionChanged(PermissionEvent.Revoked)
            }
        }
    }

    Box(modifier = Modifier) {
        when (locationState) {
            LocationState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            LocationState.RevokedPermissions -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("We need permissions to use this app")
                    Button(
                        onClick = { Intent(Settings.ACTION_LOCALE_SETTINGS) },
                        enabled = !context.hasLocationPermission()
                    ) {
                        if (context.hasLocationPermission()) CircularProgressIndicator(
                            modifier = Modifier.size(14.dp),
                            color = Color.White
                        )
                        else Text("Settings")
                    }
                }
            }
            is LocationState.Success -> {
                val currentLoc =
                    LatLng(
                        locationState.location?.latitude ?: 0.0,
                        locationState.location?.longitude ?: 0.0
                    )
                AttendanceMaps(currentLoc)
            }

            else -> {}
        }

        AnimatedVisibility(
            visible = locationState is LocationState.Success,
            enter = slideInVertically(animationSpec = tween(durationMillis = 450, easing = LinearOutSlowInEasing)) {
                with(density) { 40.dp.roundToPx() }
            } + fadeIn(),
            exit = slideOutVertically(animationSpec = tween(durationMillis = 450, easing = FastOutLinearInEasing)) {
                with(density) { (40).dp.roundToPx() }
            } + fadeOut()
        ) {
            AttendanceCard(navController = navController, uiState.isAbleToDoAttendance, Modifier, onPresentPressed)
        }
    }
}

@Composable
private fun AttendanceCard(
    navController: NavController,
    isAbleToDoAttendance: Boolean,
    modifier: Modifier,
    onPresentPressed: () -> Unit
) {
    Column(
        modifier = modifier
    ) {
        AttendanceNavBar(
            title = stringResource(id = R.string.attendance)
        ) {
            navController.popBackStack()
        }

        Spacer(modifier = Modifier.weight(4f))
        Column(
            modifier = Modifier
                .weight(6f)
                .background(Color.White, RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp))
                .clip(RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp))
                .padding(30.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Text(
                text = stringResource(id = R.string.attendance_card_title),
                style = poppinsFont(size = 24, fontWeight = 700)
            )
            Text(
                text = stringResource(id = R.string.attendance_card_desc),
                style = poppinsFont(size = 14, fontWeight = 400)
            )

            Spacer(modifier = Modifier.weight(1f))

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.height(62.dp)
            ){
                YMBorderedButton(
                    modifier = Modifier
                        .weight(1f),
                    title = stringResource(id = R.string.permit),
                    backgroundColor = englishVermillion,
                    foregroundColor = Color.White,
                    cornerRadius = 20
                ) {
                    navController.navigate(Screen.Permit.route)
                }

                YMBorderedButton(
                    modifier = Modifier
                        .weight(1f),
                    title = stringResource(id = R.string.present),
                    backgroundColor = appleGreen.copy(if (isAbleToDoAttendance) 1f else 0.5f),
                    foregroundColor = Color.White,
                    cornerRadius = 20,
                    enabled = isAbleToDoAttendance,
                    onClick = onPresentPressed
                )
            }
        }
    }
}

@Composable
fun RationaleAlert(onDismiss: () -> Unit, onConfirm: () -> Unit) {

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties()
    ) {
        Surface(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight(),
            shape = MaterialTheme.shapes.large,
            tonalElevation = AlertDialogDefaults.TonalElevation
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "We need location permissions to use this app",
                )
                Spacer(modifier = Modifier.height(24.dp))
                TextButton(
                    onClick = {
                        onConfirm()
                        onDismiss()
                    },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("OK")
                }
            }
        }
    }
}



@Preview(showSystemUi = true)
@Composable
private fun AttendanceScreenPreview() {
    AttendanceScreen(rememberNavController(), AttendanceUIState(), locationState = LocationState.Loading, {}, {}, {})
}