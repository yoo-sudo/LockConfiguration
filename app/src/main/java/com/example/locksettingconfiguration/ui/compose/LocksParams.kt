package com.example.locksettingconfiguration.ui.compose

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.locksettingconfiguration.R
import com.example.locksettingconfiguration.model.LockConfigDetail
import com.example.locksettingconfiguration.model.LockParams
import com.example.locksettingconfiguration.ui.theme.charcoalGray
import com.example.locksettingconfiguration.ui.theme.gray
import com.example.locksettingconfiguration.ui.theme.white
import com.example.locksettingconfiguration.viewmodel.MainViewModel

@Composable
fun LocksParams(mainViewModel: MainViewModel, onClick: () -> Unit) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
                    .fillMaxWidth()
                    .background(color = charcoalGray)
            ) {
                Text(
                    text = "Primary", modifier = Modifier
                        .padding(12.dp)
                        .padding(start = 8.dp), color = white
                )
                Text(
                    text = "Secondary", modifier = Modifier
                        .padding(12.dp)
                        .padding(end = 24.dp), color = white
                )
            }
            val remoteData = mainViewModel.getLockConfig().collectAsState().value
            val savedParams = mainViewModel.listenToSavedParam.collectAsState(initial = mainViewModel.getSavedLockParams()).value
            LazyColumnWithSelection(modifier = Modifier.height(700.dp), remoteData, savedParams) { index ->
                mainViewModel.dataToEdit(remoteData[index])
                mainViewModel.setSelectedIndex(index)
                onClick.invoke()
            }
        }
    }
}

@Composable
fun LazyColumnWithSelection(modifier: Modifier, lockConfigDetails: List<LockConfigDetail>, savedParams: List<LockParams>, onClick: (Int) -> Unit) {
    LazyColumn(
        modifier = modifier,
    ) {
        itemsIndexed(items = lockConfigDetails) { index, lockDetail ->
            ItemView(
                index = index,
                onClick = onClick,
                lockConfigDetail = lockDetail,
                savedParams = savedParams
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
                    .background(color = gray)
                    .padding(top = 14.dp)
            )
        }
    }
}

@Composable
fun ItemView(index: Int, lockConfigDetail: LockConfigDetail, savedParams: List<LockParams>, onClick: (Int) -> Unit,) {
    Row(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .clickable {
                onClick.invoke(index)
            }
            .padding(8.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .align(Alignment.Center)
            ) {
                Text(
                    text = lockConfigDetail.lockName,
                    modifier = Modifier
                        .padding(12.dp)
                        .wrapContentHeight()
                        .wrapContentWidth()
                        .align(Alignment.CenterHorizontally)
                )

                if (lockConfigDetail.common) {
                    Text(
                        text = if (savedParams.isNotEmpty() && savedParams.size > index && savedParams[index].commonValue != "") savedParams[index].commonValue else lockConfigDetail.primaryLockValue,
                        modifier = Modifier
                            .wrapContentHeight()
                            .wrapContentWidth()
                            .align(Alignment.CenterHorizontally),
                        fontWeight = FontWeight.Bold
                    )
                } else {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(), horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = if (savedParams.isNotEmpty() && savedParams.size > index && savedParams[index].primaryLockValue != "") savedParams[index].primaryLockValue else lockConfigDetail.primaryLockValue,
                            modifier = Modifier
                                .wrapContentHeight()
                                .padding(start = 12.dp, end = 24.dp),
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = if (savedParams.isNotEmpty() && savedParams.size > index && savedParams[index].secondaryLockValue != "") savedParams[index].secondaryLockValue else lockConfigDetail.secondaryLockValue,
                            modifier = Modifier
                                .wrapContentHeight()
                                .padding(start = 12.dp, end = 24.dp),
                            fontWeight = FontWeight.Bold
                        )

                    }
                }
            }
            Image(
                painter = painterResource(id = R.mipmap.right_arrow), contentDescription = "Arrow", modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .width(24.dp)
            )
        }
    }
}