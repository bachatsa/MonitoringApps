package com.ydzmobile.employee.ui.screen.main.target

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ydzmobile.employee.core.domain.model.toUriString
import com.ydzmobile.employee.core.viewModel.TargetUIState
import com.ydzmobile.employee.ui.component.molecule.main.target.TargetCell
import com.ydzmobile.employee.ui.navigation.TARGET_ARGUMENT_KEY
import com.ydzmobile.employee.ui.navigation.bottomTabNav.BottomBarScreen

@Composable
fun TargetScreen(
    navController: NavController,
    targetUIState: TargetUIState,
    onViewAppear: () -> Unit
) {
    LaunchedEffect(Unit) {
        onViewAppear()
    }
    LazyColumn(
        modifier = Modifier
            .padding(horizontal = 28.dp)
            .verticalScroll(rememberScrollState())
            .heightIn(max = (66 * targetUIState.targetType.count() + ((targetUIState.targetType.count() - 1) * 30)).dp),
        verticalArrangement = Arrangement.spacedBy(30.dp)
    ) {
        items(
            items = targetUIState.targetType,
            key = {
                it
            }
        ) {
            TargetCell(title = it + " Target") {
                navController.navigate("SUB_TARGET/${it}")
            }
        }
    }
}