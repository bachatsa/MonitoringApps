package com.ydzmobile.employee.ui.screen.main.profile.updateProfile

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.ydzeinemployeemonitoring.R
import com.ydzmobile.employee.core.domain.model.auth.User
import com.ydzmobile.employee.core.extension.dateStringToLong
import com.ydzmobile.employee.core.extension.longToDateStr
import com.ydzmobile.employee.core.viewModel.ProfileUIState
import com.ydzmobile.employee.ui.component.atom.button.YMBorderedButton
import com.ydzmobile.employee.ui.component.atom.button.YMDateProfileDatePicker
import com.ydzmobile.employee.ui.component.atom.button.YMDropDown
import com.ydzmobile.employee.ui.component.atom.textfield.YMProfileTextField
import com.ydzmobile.employee.ui.theme.littleBoyBlue
import com.ydzmobile.employee.ui.theme.poppinsFont
import com.ydzmobile.employee.ui.theme.tuftsBlue

@Composable
fun UpdateProfileScreen(
    navController: NavController,
    uiState: ProfileUIState,
    onUpdateProfilePressed: () -> Unit,
    onNameChanged: (String) -> Unit,
    onBirthDateChanged: (String) -> Unit,
    onPhoneNumberChanged: (String) -> Unit,
    onAddressChanged: (String) -> Unit,
    onRtChanged: (String) -> Unit,
    onDivisionChanged: (String) -> Unit,
    onBloodTypeChanged: (String) -> Unit,
    onSelectedImage: (Uri?) -> Unit,
) {
    var showDatePicker by remember { mutableStateOf(false) }

    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> onSelectedImage(uri) }
    )

    fun launchPhotoPicker() {
        singlePhotoPickerLauncher.launch(
            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
        )
    }

    LaunchedEffect(key1 = uiState.isUpdated) {
        if (uiState.isUpdated) {
            navController.popBackStack()
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            Modifier
                .padding(horizontal = 28.dp)
                .verticalScroll(rememberScrollState()),
            Arrangement.spacedBy(12.dp)
        ) {
            Header {
                navController.popBackStack()
            }

            FormHeader(
                user = uiState.user,
                modifier = Modifier.padding(bottom = 22.dp),
                selectedImages = uiState.downloadedProfileImage,
                onEditProfilePicturePressed = {
                    launchPhotoPicker()
                }
            )


            YMProfileTextField(
                title = stringResource(id = R.string.full_name),
                hint = stringResource(id = R.string.full_name),
                value = uiState.user.name ?: "",
                icon = painterResource(id = R.drawable.ic_outline_person),
                onValueChange = onNameChanged
            )

            Surface(
                onClick = {
                    showDatePicker = true
                }
            ) {
                YMProfileTextField(
                    title = stringResource(id = R.string.birthday_date),
                    hint = stringResource(id = R.string.birthday_date),
                    value = uiState.user.dateBirth ?: "",
                    icon = painterResource(id = R.drawable.ic_calendar),
                    isEnable = false,
                    onValueChange = onBirthDateChanged
                )
            }

            YMProfileTextField(
                title = stringResource(id = R.string.phone_number2),
                hint = stringResource(id = R.string.phone_number2),
                value = uiState.user.phoneNumber ?: "",
                icon = painterResource(id = R.drawable.ic_phone),
                onValueChange = onPhoneNumberChanged
            )

            YMProfileTextField(
                title = stringResource(id = R.string.adress),
                hint = stringResource(id = R.string.adress),
                value = uiState.user.address ?: "",
                icon = painterResource(id = R.drawable.ic_map_marker),
                onValueChange = onAddressChanged
            )

            YMProfileTextField(
                title = stringResource(id = R.string.rt_rw),
                hint = stringResource(id = R.string.rt_rw),
                value = uiState.user.rt ?: "",
                icon = painterResource(id = R.drawable.ic_map_marker),
                onValueChange = onRtChanged
            )

            YMDropDown(
                selectedDropDown = uiState.user.division ?: "",
                label = stringResource(id = R.string.division),
                icon = painterResource(id = R.drawable.ic_division),
                options = uiState.divisions,
                onDropDownSelected = onDivisionChanged,
                modifier = Modifier
            )

            YMDropDown(
                selectedDropDown = uiState.user.bloodType ?: "",
                label = stringResource(id = R.string.blood_type),
                icon = painterResource(id = R.drawable.ic_blood_type),
                options = uiState.bloodTypes,
                onDropDownSelected = onBloodTypeChanged,
                modifier = Modifier
            )

            YMBorderedButton(
                modifier = Modifier
                    .padding(bottom = 32.dp, top = 16.dp),
                title = stringResource(id = R.string.save),
                backgroundColor = littleBoyBlue,
                foregroundColor = Color.White,
                enabled = true,
                onClick = onUpdateProfilePressed
            )
        }

        YMDateProfileDatePicker(
            showDatePicker = showDatePicker,
            selectedDate = uiState.user.dateBirth?.dateStringToLong() ?: System.currentTimeMillis()
        ) { selectedDate ->
            showDatePicker = false
            if (selectedDate != null) {
                onBirthDateChanged(selectedDate.longToDateStr())
            }
        }
    }
}


@Composable
private fun Header(
    onBackPressed: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 26.dp, top = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBackPressed) {
            Icon(
                modifier = Modifier
                    .size(22.dp),
                painter = painterResource(id = R.drawable.ic_arrow_left),
                contentDescription = null,
                tint = Color.Black
            )
        }

        Text(
            text = stringResource(id = R.string.private_detail),
            style = poppinsFont(size = 20, fontWeight = 700, color = Color.Black)
        )

        Spacer(
            modifier = Modifier
                .size(22.dp),
        )

    }
}

@Composable
private fun FormHeader(
    user: User,
    selectedImages: Uri?,
    onEditProfilePicturePressed: () -> Unit,
    modifier: Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(22.dp)
    ) {
        AsyncImage(
            model = selectedImages
                ?: ImageRequest.Builder(LocalContext.current)
                    .data(user.profilePicture)
                    .crossfade(true)
                    .build(),
            placeholder = painterResource(R.drawable.ic_image_default),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(98.dp)
                .clip(CircleShape)
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = user.name ?: "-",
                style = poppinsFont(size = 18, fontWeight = 700, color = Color.Black)
            )
            Text(
                text = user.idEmployee ?: "-",
                style = poppinsFont(size = 14, fontWeight = 400, color = Color.Black)
            )

            Button(
                modifier = Modifier
                    .height(28.dp)
                    .width(138.dp),
                onClick = onEditProfilePicturePressed,
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                ),
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(1.dp, tuftsBlue)
            ) {
                Text(
                    text = stringResource(id = R.string.edit_profile_picture),
                    style = poppinsFont(size = 12, fontWeight = 500, color = Color.Black)
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun UpdateProfileScreenPreview() {
    UpdateProfileScreen(
        rememberNavController(),
        ProfileUIState(),
        {},
        {},
        {},
        {},
        {},
        {},
        {},
        {},
        {})
}