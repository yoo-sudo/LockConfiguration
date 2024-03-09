import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.locksettingconfiguration.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Search(viewModel: MainViewModel, onClick: () -> Unit) {
    val searchText by viewModel.searchText.collectAsState()
    val isSearching by viewModel.isSearching.collectAsState()
    val lockConfigNames by viewModel.searchList.collectAsState()

    SearchBar(
        query = searchText,
        onQueryChange = viewModel::onSearchTextChange,
        onSearch = viewModel::onSearchTextChange,
        active = isSearching,
        onActiveChange = { viewModel.onToggleSearch() },
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(bottom = 16.dp)
    ) {
        LazyColumn(modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()) {
            itemsIndexed(lockConfigNames) { index, lockConfigName ->
                Text(
                    text = lockConfigName,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(
                            start = 8.dp,
                            top = 18.dp,
                            end = 8.dp,
                            bottom = 4.dp
                        )
                        .fillMaxWidth()
                        .clickable {
                            viewModel.dataToEdit(viewModel.getLockConfig().value[index])
                            viewModel.setSelectedIndex(index)
                            onClick()
                        }
                )
            }
        }
    }
}