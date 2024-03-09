package com.example.locksettingconfiguration.ui.compose

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.locksettingconfiguration.R
import com.example.locksettingconfiguration.model.LockRange
import com.example.locksettingconfiguration.ui.theme.charcoalGray
import com.example.locksettingconfiguration.ui.theme.gray
import com.example.locksettingconfiguration.viewmodel.MainViewModel

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun EditScreen(mainViewModel: MainViewModel, onClick: () -> Unit) {
    var primarySelectedValue = mainViewModel.getDetailsToEdit()?.primaryLockValue ?: ""
    var secondarySelectedValue = mainViewModel.getDetailsToEdit()?.secondaryLockValue ?: ""
    var commonValue = mainViewModel.getDetailsToEdit()?.primaryLockValue ?: ""

    Surface(modifier = Modifier.fillMaxSize()) {
        Column {
            TopAppBar(title = {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = stringResource(id = R.string.edit_title), style = MaterialTheme.typography.titleLarge
                    )
                }
            })

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
                    .background(color = gray)
            )

            mainViewModel.getDetailsToEdit()?.let { lockDetail ->
                Text(
                    text = lockDetail.lockName, modifier = Modifier
                        .padding(12.dp)
                        .padding(start = 8.dp), color = charcoalGray, fontWeight = FontWeight.Bold
                )
                if (lockDetail.lockRange == null && lockDetail.common.not()) {

                    val primarySelected = mainViewModel.getSavedLockParams()[mainViewModel.displayedLockIndex].primaryLockValue
                    Primary(items = lockDetail.primaryLockValues, selectedIndex = lockDetail.primaryLockValues.indexOf(primarySelected)) {
                        primarySelectedValue = lockDetail.primaryLockValues[it]
                    }

                    val secondarySelected = mainViewModel.getSavedLockParams()[mainViewModel.displayedLockIndex].secondaryLockValue
                    Secondary(items = lockDetail.secondaryLockValues, selectedIndex = lockDetail.secondaryLockValues.indexOf(secondarySelected)) {
                        secondarySelectedValue = lockDetail.secondaryLockValues[it]
                    }

                } else {
                    val common = mainViewModel.getSavedLockParams()[mainViewModel.displayedLockIndex].commonValue
                    val selectedIndex = lockDetail.primaryLockValues.indexOf(common)
                    Common(items = lockDetail.primaryLockValues, lockRange = lockDetail.lockRange, selectedIndex = selectedIndex) {
                        commonValue = it
                        primarySelectedValue = it
                        secondarySelectedValue = it
                    }
                }

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(24.dp)
                ) {
                    Button(
                        onClick = { onClick() },
                        modifier = Modifier
                            .padding(8.dp)
                            .clip(RoundedCornerShape(2.dp))
                    ) {
                        Text(text = "CANCEL")
                    }
                    Button(
                        onClick = {
                            mainViewModel.updateDataToDb(primarySelectedValue, secondarySelectedValue, commonValue)
                            onClick()
                        },
                        modifier = Modifier
                            .padding(8.dp)
                            .clip(RoundedCornerShape(2.dp))
                    ) {
                        Text(text = "SAVE")
                    }
                }
            }
        }
    }
}

@Composable
fun Choices(items: List<String>, selectedIndex: Int, onClick: (Int) -> Unit) {
    val selectedValue = remember { mutableIntStateOf(selectedIndex) }

    val isSelectedItem: (Int) -> Boolean = { selectedValue.intValue == it }
    val onChangeState: (Int) -> Unit = { selectedValue.intValue = it }

    Column(Modifier.padding(8.dp)) {
        items.forEachIndexed { index, item ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .selectable(
                        selected = isSelectedItem(index),
                        onClick = {
                            onChangeState(index)
                            onClick(index)
                        },
                        role = Role.RadioButton
                    )
                    .padding(8.dp)
            ) {
                RadioButton(
                    selected = isSelectedItem(index),
                    onClick = null
                )
                Text(
                    text = item,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun Primary(items: List<String>, selectedIndex: Int, onClick: (Int) -> Unit) {
    Text(
        text = "Primary", modifier = Modifier
            .padding(top = 24.dp, start = 24.dp, end = 24.dp, bottom = 0.dp), color = charcoalGray, fontWeight = FontWeight.Bold
    )
    Choices(items = items, selectedIndex = selectedIndex, onClick = onClick)
}

@Composable
fun Secondary(items: List<String>, selectedIndex: Int, onClick: (Int) -> Unit) {
    Text(
        text = "Secondary", modifier = Modifier
            .padding(top = 24.dp, start = 24.dp, end = 24.dp, bottom = 0.dp), color = charcoalGray, fontWeight = FontWeight.Bold
    )
    Choices(items = items, selectedIndex = selectedIndex, onClick = onClick)
}

@Composable
fun Common(items: List<String>, lockRange: LockRange?, selectedIndex: Int, onClick: (String) -> Unit) {
    Text(
        text = "Common", modifier = Modifier
            .padding(top = 24.dp, start = 24.dp, end = 24.dp, bottom = 12.dp), color = charcoalGray, fontWeight = FontWeight.Bold
    )
    if (lockRange != null) {
        NumberDropdown(lockRange.min.toInt(), lockRange.max.toInt(), onSelected = onClick)
    } else {
        Choices(items = items, selectedIndex = selectedIndex) {
            onClick(items[it])
        }
    }
}

@Composable
fun NumberDropdown(
    min: Int,
    max: Int,
    onSelected: (String) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    var text by remember { mutableStateOf("Select") }

    val options = (min..max).map { it.toString() }


    Box(modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.fillMaxWidth().padding(8.dp), horizontalArrangement = Arrangement.Center) {
            Text(
                text = text,
                modifier = Modifier
                    .clickable { expanded = true }
                    .padding(16.dp),
                textAlign = TextAlign.Center
            )
            IconButton(onClick = { expanded = true }) {
                Icon(painterResource(id = R.mipmap.down_arrow), contentDescription = "Dropdown Menu", modifier = Modifier.size(24.dp))
            }
        }
        DropdownMenu(
            modifier = Modifier.fillMaxWidth().align(Alignment.Center),
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { value ->
                DropdownMenuItem(text = { Text(text = value, textAlign = TextAlign.Center) }, onClick = {
                    text = value
                    expanded = false
                    onSelected(value)
                })
            }
        }
    }
}