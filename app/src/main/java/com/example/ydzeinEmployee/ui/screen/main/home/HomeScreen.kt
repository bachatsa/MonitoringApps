package com.ydzmobile.employee.ui.screen.main.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ydzeinemployeemonitoring.R
import com.ydzmobile.employee.core.viewModel.HomeUIState
import com.ydzmobile.employee.ui.component.molecule.main.home.HomeTargetListCell
import com.ydzmobile.employee.ui.component.molecule.main.home.HomeTaskHistory
import com.ydzmobile.employee.ui.theme.poppinsFont

@Composable
fun HomeScreen(
    homeUIState: HomeUIState,
    onViewAppear: () -> Unit
) {
    LaunchedEffect(Unit) {
        onViewAppear()
    }

    Column(
        modifier = Modifier
            .padding(horizontal = 28.dp)
            .verticalScroll(rememberScrollState())
    ) {
        HomeTaskHistory(homeUIState.totalItemDone, homeUIState.totalItemTargets, Modifier)

        Spacer(modifier = Modifier.height(82.dp))

        Text(
            text = stringResource(id = R.string.target_list_title),
            style = poppinsFont(size = 26, fontWeight = 700)
        )

        AnimatedVisibility(visible = homeUIState.targets.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier.heightIn(max = (126 * homeUIState.targets.count() + ((homeUIState.targets.count() - 1) * 16)).dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(
                    items = homeUIState.targets,
                    key = { data ->
                        data.id
                    },
                ) {
                    HomeTargetListCell(data = it)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    HomeScreen(homeUIState = HomeUIState(listOf(), 0, 0), {})
}