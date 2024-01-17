package com.ydzmobile.employee.ui.component.atom.button

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ydzmobile.employee.core.extension.longToDateStr

@SuppressLint("RememberReturnType")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YMDateProfileDatePicker(
    showDatePicker: Boolean,
    selectedDate: Long,
    onDismissPressed: (Long?) -> Unit
) {
    val state = rememberDatePickerState(initialSelectedDateMillis = selectedDate)

    if (showDatePicker) {
        DatePickerDialog(
            modifier = Modifier.fillMaxWidth(1f),
            onDismissRequest = {
                onDismissPressed(state.selectedDateMillis)
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDismissPressed(state.selectedDateMillis)
                    }
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        onDismissPressed(null)
                    }
                ) {
                    Text("CANCEL")
                }
            }
        ) {
            DatePicker(
                state = state,
                headline = {
                    Text(
                        text = state.selectedDateMillis?.longToDateStr("dd MMM YYYY") ?: "Tanggal yang dipilih",
                        modifier = Modifier.padding(PaddingValues(start = 24.dp, end = 12.dp, bottom = 12.dp))
                    )
                }
            )
        }
    }
}

//@Preview
//@Composable
//private fun YMDateProfileDatePickerPreview() {
//    YMDateProfileDatePicker()
//}