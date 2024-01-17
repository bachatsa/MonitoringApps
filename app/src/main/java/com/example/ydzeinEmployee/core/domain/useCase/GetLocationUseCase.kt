package com.ydzmobile.employee.core.domain.useCase

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.android.gms.maps.model.LatLng
import com.ydzmobile.employee.core.utils.ILocationService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLocationUseCase @Inject constructor(
    private val locationService: ILocationService
) {
    
    operator fun invoke(): Flow<LatLng?> = locationService.requestLocationUpdates()

}