package com.example.gorental.view.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.gorental.R
import com.example.gorental.utils.AppConstants
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerBox(modifier: Modifier, label:String, date:String, onclick : (data:String)->Unit) {
    val dateState = remember { mutableStateOf(date) }
    val openDialog = remember { mutableStateOf(false) }

    val today = System.currentTimeMillis()
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = today,
        selectableDates =  object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                return utcTimeMillis >= today // disabling past dates
            }
        }
    )

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Text(text =label , style = TextStyle(
            fontWeight = FontWeight.Bold
        ),color = colorResource(id = R.color.black) )
        Button(onClick = { openDialog.value = true },
            colors = ButtonDefaults.buttonColors(colorResource(id = R.color.light_grey)),
            shape = RoundedCornerShape(10.dp),
        ) {
            Icon(imageVector = Icons.Default.DateRange, contentDescription = stringResource(
                id = R.string.date_icon_desc
            ), tint = colorResource(id = R.color.grey)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = dateState.value , color = colorResource(id = R.color.grey))
        }

        if (openDialog.value) {
            DatePickerDialog(
                onDismissRequest = { openDialog.value = false },
                confirmButton = {
                    TextButton(onClick = { openDialog.value = false
                        onclick.invoke(dateState.value)
                    }) {
                        Text(stringResource(id = R.string.ok_text))
                    }
                }
            ) {
                DatePicker(state = datePickerState, showModeToggle = false)
                LaunchedEffect(datePickerState.selectedDateMillis) {
                    datePickerState.selectedDateMillis?.let { millis ->
                        dateState.value = SimpleDateFormat(AppConstants.YYYY_MM_DD, Locale.getDefault()).format(
                            Date(millis)
                        )
                    }
                }
            }
        }
    }
}
