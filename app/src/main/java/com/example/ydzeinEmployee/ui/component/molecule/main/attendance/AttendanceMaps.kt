package com.ydzmobile.employee.ui.component.molecule.main.attendance

import android.content.Context
import android.graphics.Bitmap
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.ydzeinemployeemonitoring.R
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.Circle
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.ydzmobile.employee.ui.theme.darkJungleGreen
import com.ydzmobile.employee.ui.theme.paleSpringBud

@Composable
fun AttendanceMaps(currentUserLocation: LatLng) {
    val epic = LatLng(-6.2068924, 107.0079269)
    val cameraPositionState = rememberCameraPositionState {
         position = CameraPosition.fromLatLngZoom(currentUserLocation, 20f)
    }
    Column {
        Box(modifier = Modifier.weight(6f)) {
            GoogleMap(
                modifier = Modifier
                    .fillMaxSize(),
                cameraPositionState = cameraPositionState,
                uiSettings = MapUiSettings(
                    zoomControlsEnabled = false
                ),
                properties = MapProperties(isMyLocationEnabled = true)
            ) {
                Marker(
                    state = MarkerState(position = epic),
                    title = "Office",
                    icon = bitmapDescriptorFromVector(
                        LocalContext.current, R.drawable.ic_map_pin
                    )
                )

                Circle(
                    center = epic,
                    radius = 50.0,
                    strokeWidth = 10f,
                    strokeColor = darkJungleGreen,
                    fillColor = paleSpringBud.copy(0.8f)
                )
            }
        }

        Spacer(modifier = Modifier.weight(4f))
    }
}

private fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor {
    val vectorDrawable = context.getDrawable(vectorResId)
    val bitmap = Bitmap.createBitmap(
        vectorDrawable!!.intrinsicWidth,
        vectorDrawable.intrinsicHeight,
        Bitmap.Config.ARGB_8888
    )
    val canvas = android.graphics.Canvas(bitmap)
    vectorDrawable.setBounds(0, 0, canvas.width, canvas.height)
    vectorDrawable.draw(canvas)

    return BitmapDescriptorFactory.fromBitmap(bitmap)
}