package com.ydzmobile.employee.ui.screen.main.target

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ydzmobile.employee.core.domain.model.monitor.toUriString
import com.ydzmobile.employee.core.viewModel.TargetUIState
import com.ydzmobile.employee.ui.component.molecule.main.MainNavigationBar
import com.ydzmobile.employee.ui.component.molecule.main.target.TargetCell

@Composable
fun SubTargetScreen(
    navController: NavController,
    uiState: TargetUIState
) {
    val maxHeight = (66 * uiState.targets.count() + ((uiState.targets.count() - 1) * 30)).dp

    Column(
    ) {
        MainNavigationBar(uiState.targetTypeText)

        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 28.dp)
                .verticalScroll(rememberScrollState())
                .heightIn(max = maxHeight),
            verticalArrangement = Arrangement.spacedBy(30.dp)
        ) {
            items(
                items = uiState.targets,
                key = {
                    it.id
                }
            ) {
                TargetCell(title = it.productType) {
                    navController.navigate("update_work_progress/${it.toUriString()}")
                }
            }
        }
    }
}