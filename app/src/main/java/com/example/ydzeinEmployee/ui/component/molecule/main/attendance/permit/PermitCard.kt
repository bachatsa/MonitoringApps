package com.ydzmobile.employee.ui.component.molecule.main.attendance.permit

import android.widget.Toast
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ydzeinemployeemonitoring.R
import com.ydzmobile.employee.core.domain.enum.PermitType
import com.ydzmobile.employee.ui.component.atom.button.YMBorderedButton
import com.ydzmobile.employee.ui.component.atom.button.YMCheckbox
import com.ydzmobile.employee.ui.theme.appleGreen
import com.ydzmobile.employee.ui.theme.englishVermillion
import com.ydzmobile.employee.ui.theme.poppinsFont
import com.ydzmobile.employee.ui.theme.tuftsBlue

@Composable
fun PermitCard(
    navController: NavController,
    type: PermitType? = null,
    modifier: Modifier,
    onOtherPressed: () -> Unit,
    onSickPressed: () -> Unit,
    onSickSubmitPressed: (List<String>, String) -> Unit,
    onOtherSubmitPressed: (String) -> Unit,
) {
    Column(
        modifier = modifier
            .background(Color.White, RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp))
            .border(1.dp, color = Color.Black, RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp))
            .clip(RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp))
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        AnimatedContent(targetState = type, label = "") {
            when(it) {
                PermitType.OTHER -> Other(navController, onOtherSubmitPressed)
                PermitType.SICK -> Sick(navController, onSickSubmitPressed)
                else -> Root(
                    onOtherPressed = onOtherPressed,
                    onSickPressed = onSickPressed
                )
            }
        }
    }
}

@Composable
private fun Root(
    onSickPressed: () -> Unit,
    onOtherPressed: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Text(
            text = stringResource(id = R.string.attendance_card_permit_title),
            style = poppinsFont(size = 24, fontWeight = 700),
        )
        Text(
            text = stringResource(id = R.string.attendance_card_permit_desc),
            style = poppinsFont(size = 14, fontWeight = 400),
            textAlign = TextAlign.Justify
        )

        Spacer(modifier = Modifier.weight(1f))

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ){
            YMBorderedButton(
                modifier = Modifier
                    .weight(1f),
                title = stringResource(id = R.string.sick),
                backgroundColor = englishVermillion,
                foregroundColor = Color.White,
                cornerRadius = 20,
                onClick = onSickPressed
            )

            YMBorderedButton(
                modifier = Modifier
                    .weight(1f),
                title = stringResource(id = R.string.other),
                backgroundColor = tuftsBlue,
                foregroundColor = Color.White,
                cornerRadius = 20,
                onClick = onOtherPressed
            )
        }
    }
}

@Composable
private fun Sick(
    navController: NavController,
    onSickSubmitPressed: (List<String>, String) -> Unit
) {
    val context = LocalContext.current
    var reason by remember { mutableStateOf("") }
    val selectedOptions = remember { mutableStateOf(setOf<String>()) }
    val options = listOf("Batuk", "Pilek", "Pusing", "Demam", "Lainnya")

    Column(
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Text(
            text = stringResource(id = R.string.sick_form_title),
            style = poppinsFont(size = 24, fontWeight = 700),
        )
        Text(
            text = stringResource(id = R.string.sick_form_desc),
            style = poppinsFont(size = 14, fontWeight = 400),
            textAlign = TextAlign.Justify
        )

        Column {
            options.forEach { option ->
                YMCheckbox(selected = selectedOptions.value.contains(option), title = option) {
                    val currentSelected = selectedOptions.value.toMutableSet()
                    if (selectedOptions.value.contains(option)) {
                        currentSelected.remove(option)
                    } else {
                        currentSelected.add(option)
                    }
                    selectedOptions.value = currentSelected
                }
            }
        }

        AnimatedVisibility(
            visible = selectedOptions.value.contains("Lainnya"),
            modifier = Modifier.weight(2f)
        ){
            TextField(
                value = reason,
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.sick_form_placeholder),
                        style = poppinsFont(
                            size = 14,
                            fontWeight = 300,
                            color = Color.Black.copy(0.5f)
                        )
                    )
                },
                onValueChange = {
                    if (it.count() <= 100) {
                        reason = it
                    }
                }, modifier = Modifier
                    .fillMaxWidth()
                    .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(18.dp)),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent

                ),
                shape = RoundedCornerShape(18.dp)
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        YMBorderedButton(
            modifier = Modifier
                .padding(horizontal = 14.dp),
            title = stringResource(id = R.string.send),
            backgroundColor = appleGreen,
            foregroundColor = Color.White,
            cornerRadius = 20,
            enabled = (reason.count() > 10) && selectedOptions.value.isNotEmpty()
        ) {
            Toast.makeText(
                context,
                "Success",
                Toast.LENGTH_SHORT
            ).show()
            navController.popBackStack()
            navController.popBackStack()
            navController.popBackStack()
            onSickSubmitPressed(selectedOptions.value.toList(), reason)
        }
    }
}

@Composable
private fun Other(
    navController: NavController,
    onOtherSubmitPressed: (String) -> Unit,
) {
    val context = LocalContext.current
    var reason by remember { mutableStateOf("") }
    Column(
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Text(
            text = stringResource(id = R.string.other_form_title),
            style = poppinsFont(size = 24, fontWeight = 700),
        )
        Text(
            text = stringResource(id = R.string.other_form_desc),
            style = poppinsFont(size = 14, fontWeight = 400),
            textAlign = TextAlign.Justify
        )

        TextField(
            value = reason,
            placeholder = {
                Text(
                    text = stringResource(id = R.string.other_form_placeholder),
                    style = poppinsFont(size = 14, fontWeight = 300, color = Color.Black.copy(0.5f))
                )
            },
            onValueChange = {
                if (it.count() <= 100) {
                    reason = it
                }
            }, modifier = Modifier
                .fillMaxWidth()
                .weight(3f)
                .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(18.dp)),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent

            ),
            shape = RoundedCornerShape(18.dp)
        )

        Spacer(modifier = Modifier.weight(1f))

        YMBorderedButton(
            modifier = Modifier
                .padding(horizontal = 14.dp),
            title = stringResource(id = R.string.send),
            backgroundColor = appleGreen,
            foregroundColor = Color.White,
            cornerRadius = 20,
            enabled = (reason.count() > 10)
        ) {
            Toast.makeText(
                context,
                "Success",
                Toast.LENGTH_SHORT
            ).show()
            navController.popBackStack()
            navController.popBackStack()
            navController.popBackStack()
            onOtherSubmitPressed(reason)
        }
    }
}