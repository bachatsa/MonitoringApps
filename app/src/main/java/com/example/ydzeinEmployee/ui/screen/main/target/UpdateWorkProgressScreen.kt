package com.ydzmobile.employee.ui.screen.main.target

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.ydzeinemployeemonitoring.R
import com.ydzmobile.employee.core.domain.model.monitor.TargetModel
import com.ydzmobile.employee.core.viewModel.UpdateWorkProgressUIState
import com.ydzmobile.employee.ui.component.atom.button.YMBorderedButton
import com.ydzmobile.employee.ui.component.atom.button.YMUploadButton
import com.ydzmobile.employee.ui.component.atom.textfield.YMBasicTextField
import com.ydzmobile.employee.ui.theme.darkJungleGreen
import com.ydzmobile.employee.ui.theme.littleBoyBlue
import com.ydzmobile.employee.ui.theme.poppinsFont
import java.io.File
import kotlin.math.min

@Composable
fun UpdateWorkProgressScreen(
    navController: NavController,
    uiState: UpdateWorkProgressUIState,
    onTargetBeenDone: (String) -> Unit,
    onSelectedImage: (Uri?) -> Unit,
    onSubmitPressed: () -> Unit
) {
    val context = LocalContext.current

    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) {
            navController.popBackStack()
        }
    }

    LaunchedEffect(uiState.isHasDoAttendance) {
        if (!uiState.isHasDoAttendance && uiState.isLoaded) {
            Toast.makeText(
                context,
                "Mohon Lakukan Absensi Terlebih Dahulu",
                Toast.LENGTH_SHORT
            ).show()
        }
    }



    Column {
        NavigationBar() {
            navController.popBackStack()
        }

        Column(
            modifier = Modifier
                .padding(horizontal = 28.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            CardForm(uiState, onTargetBeenDone, onSelectedImage, onSubmitPressed)

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
private fun CardForm(
    uiState: UpdateWorkProgressUIState,
    onTargetBeenDone: (String) -> Unit,
    onSelectedImage: (Uri?) -> Unit,
    onSubmitPressed: () -> Unit
) {
    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            if (uri != null){ onSelectedImage(uri) }
        }
    )

    fun launchPhotoPicker() {
        singlePhotoPickerLauncher.launch(
            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
        )
    }

    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(littleBoyBlue)
            .padding(horizontal = 16.dp, vertical = 32.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        YMBasicTextField(
            title = stringResource(id = R.string.employee_id),
            hint = stringResource(id = R.string.employee_id),
            value = uiState.target?.idEmployee ?: "",
            isEnable = false,
            onValueChange = {

            }
        )

        YMBasicTextField(
            title = stringResource(id = R.string.product_type),
            hint = stringResource(id = R.string.product_type),
            value = uiState.target?.productType ?: "",
            isEnable = false,
            onValueChange = {

            }
        )

        YMBasicTextField(
            title = stringResource(id = R.string.code),
            hint = stringResource(id = R.string.code),
            isEnable = false,
            value = uiState.target?.productType?.slice(IntRange(0, min(uiState.target?.productType?.count() ?: 1 - 1, 2))) ?: "",
            onValueChange = {

            }
        )

        YMBasicTextField(
            title = stringResource(id = R.string.total_work_been_done),
            hint = stringResource(id = R.string.total_work_been_done),
            value = uiState.targetBeenDone,
            keyboardType = KeyboardType.Number,
            isEnable = !uiState.isFinishedTarget && uiState.isHasDoAttendance,
            onValueChange = { newValue ->
                if (newValue.isNotEmpty()){
                    if (newValue.toInt() > (uiState.target!!.totalTarget - uiState.target.targetBeenDone)) {
                        onTargetBeenDone((uiState.target!!.totalTarget - uiState.target!!.targetBeenDone).toString())
                    } else {
                        onTargetBeenDone(newValue)
                    }
                } else {
                    onTargetBeenDone(newValue)
                }
            }
        )

        YMUploadButton(
            title = stringResource(id = R.string.upload_work_picture),
            selectedItemName = if (uiState.selectedImage != null) {
                File(uiState.selectedImage!!.path).name
            } else {
                ""
            },
            enabled = !uiState.isFinishedTarget && uiState.isHasDoAttendance,
            onClick = {
                if (!uiState.isFinishedTarget){
                    launchPhotoPicker()
                }
            }
        )

        if (!uiState.isFinishedTarget || !uiState.isHasDoAttendance) {
            YMBorderedButton(
                modifier = Modifier
                    .padding(horizontal = 100.dp)
                    .padding(top = 32.dp),
                title = stringResource(id = R.string.upload),
                titleSize = 14,
                buttonHeight = 32,
                foregroundColor = littleBoyBlue,
                enabled = uiState.isValid,
                onClick = onSubmitPressed
            )
        }
    }
}

@Composable
private fun NavigationBar(
    onBackPressed: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        IconButton(onClick = onBackPressed) {
            Icon(
                modifier = Modifier.size(16.dp),
                painter = painterResource(id = R.drawable.ic_chevron_left),
                contentDescription = null,
                tint = darkJungleGreen
            )
        }

        Text(
            text = stringResource(id = R.string.update_work_progress_screen_title),
            style = poppinsFont(size = 26, fontWeight = 700)
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun UpdateWorkProgressScreenPreview() {
    UpdateWorkProgressScreen(rememberNavController(), UpdateWorkProgressUIState(TargetModel()), {}, {}, {})
}