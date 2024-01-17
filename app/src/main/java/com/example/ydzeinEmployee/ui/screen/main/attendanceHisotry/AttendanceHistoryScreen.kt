package com.ydzmobile.employee.ui.screen.main.attendanceHisotry

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.ydzeinemployeemonitoring.R
import com.ydzmobile.employee.core.viewModel.AttendanceHistoryUIState
import com.ydzmobile.employee.ui.component.molecule.main.attendanceHistory.AttendanceHistoryCell
import com.ydzmobile.employee.ui.theme.poppinsFont

@Composable
fun AttendanceHistoryScreen(
    attendanceHistoryUIState: AttendanceHistoryUIState,
    onViewAppear: () -> Unit
) {
    LaunchedEffect(Unit) {
        onViewAppear()
    }
   Column(
       modifier = Modifier.padding(horizontal = 28.dp),
       verticalArrangement = Arrangement.spacedBy(12.dp)
   ) {
       Text(
           text = stringResource(id = R.string.attandance_history_subtitle),
           style = poppinsFont(size = 16, fontWeight = 500)
       )
       
       LazyColumn(
           verticalArrangement = Arrangement.spacedBy(12.dp)
       ) {
           items(
               items = attendanceHistoryUIState.histories,
               key = {
                   it.id
               }
               ) {
               AttendanceHistoryCell(data = it)
           }
       }

       Spacer(modifier = Modifier.height(32.dp))
   }
}