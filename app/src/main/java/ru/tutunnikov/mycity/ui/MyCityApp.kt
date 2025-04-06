package ru.tutunnikov.mycity.ui

import MyCityTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.tutunnikov.mycity.data.Place
import ru.tutunnikov.mycity.data.PlaceType

@Composable
fun MyCityApp(
    modifier: Modifier = Modifier
) {
    val viewModel: MyCityViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()

    HomeScreen(
        uiState = uiState,
        onTabPressed = {placeType: PlaceType ->
            viewModel.updateCurrentPlaceType(placeType)
            viewModel.resetHomeScreen()
        },
        onPlaceCardPressed = {place: Place ->
            viewModel.updateDetailsScreen(place)
        },
        onDetailScreenBackPressed = {
            viewModel.resetHomeScreen()
        },
        modifier = modifier
    )
}

@Preview(showSystemUi = true)
@Composable
fun MyCityAppPreview() {
    MyCityTheme {
        Surface {
            MyCityApp()
        }
    }
}