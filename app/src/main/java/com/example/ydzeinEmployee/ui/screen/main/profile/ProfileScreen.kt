package com.ydzmobile.employee.ui.screen.main.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.ydzeinemployeemonitoring.R
import com.ydzmobile.employee.core.domain.model.auth.User
import com.ydzmobile.employee.core.viewModel.ProfileUIState
import com.ydzmobile.employee.ui.component.atom.button.YMBorderedButton
import com.ydzmobile.employee.ui.component.molecule.main.profile.DetailProfile
import com.ydzmobile.employee.ui.component.molecule.main.profile.ProfileHeader
import com.ydzmobile.employee.ui.navigation.AUTH_GRAPH_ROUTE
import com.ydzmobile.employee.ui.navigation.Screen
import com.ydzmobile.employee.ui.theme.*

@Composable
fun ProfileScreen(
    navController: NavController,
    rootNavController: NavController,
    profileUIState: ProfileUIState,
    onLogoutPressed: () -> Unit,
    onViewAppear: () -> Unit
) {
    LaunchedEffect(Unit) {
        onViewAppear()
    }

    Column(
        modifier = Modifier
            .padding(horizontal = 28.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            text = stringResource(id = R.string.update_profile_reminder_title),
            style = poppinsFont(size = 16, fontWeight = 500)
        )

        ProfileHeader(
            name = profileUIState.user.name ?: "-",
            nik = profileUIState.user.idEmployee ?: "-",
            imageUrl = if (profileUIState.downloadedProfileImage != null) profileUIState.downloadedProfileImage.toString() else profileUIState.user.profilePicture ?: "",
            modifier = Modifier
                .padding(top = 7.dp)
        )

        DetailProfile(
            user = profileUIState.user,
            onEditButtonPressed = {
                rootNavController.navigate(Screen.UpdateProfile.route)
            },
            modifier = Modifier
                .padding(top = 32.dp)
        )

        YMBorderedButton(
            modifier = Modifier
                .padding(top = 22.dp),
            title = stringResource(id = R.string.changePassword_button_title),
            foregroundColor = littleBoyBlue,
            backgroundColor = Color.White,
            strokeColor = littleBoyBlue,
            strokeWidth = 2
        ) {
            rootNavController.navigate(Screen.ChangePassword.route)
        }

        YMBorderedButton(
            modifier = Modifier
                .padding(bottom = 32.dp),
            title = stringResource(id = R.string.logout_button_title),
            foregroundColor = Color.White,
            backgroundColor = englishVermillion.copy(0.75f),
            strokeWidth = 0,
            onClick = onLogoutPressed
        )

        LaunchedEffect(key1 = profileUIState.isLoggedOut) {
            if (profileUIState.isLoggedOut) {
                rootNavController.popBackStack()
                rootNavController.navigate(AUTH_GRAPH_ROUTE)
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun ProfileScreenPreview() {
    val nav = rememberNavController()
    val nav2 = rememberNavController()
    ProfileScreen(nav, nav2, ProfileUIState(User(uid = "", email = "")), {}) {}
}
