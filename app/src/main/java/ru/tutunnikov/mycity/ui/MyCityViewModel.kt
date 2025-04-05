package ru.tutunnikov.mycity.ui

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import ru.tutunnikov.mycity.data.Place
import ru.tutunnikov.mycity.data.PlaceType
import ru.tutunnikov.mycity.data.local.LocalPlacesDataProvider

class MyCityViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(MyCityUiState())
    val uiState: StateFlow<MyCityUiState> = _uiState.asStateFlow()

    init {
        initializeUIState()
    }

    private fun initializeUIState() {
        val places: Map<PlaceType, List<Place>> =
            LocalPlacesDataProvider.listOfPlaces.groupBy { it.placeType }
        _uiState.value =
            MyCityUiState(
                placesMap = places,
                currentSelectedPlace = places[PlaceType.Food]?.get(0)
                    ?: LocalPlacesDataProvider.defaultPlace
            )
    }

    fun updateDetailsScreen(place: Place) {
        _uiState.update {
            it.copy(
                currentSelectedPlace = place,
                isShowingHomeScreen = false
            )
        }
    }

    fun resetHomeScreen() {
        _uiState.update {
            it.copy(
                currentSelectedPlace = it.placesMap[it.currentPlaceType]?.get(0)
                    ?: LocalPlacesDataProvider.defaultPlace,
                isShowingHomeScreen = true
            )
        }
    }

    fun updateCurrentPlaceType(placeType: PlaceType) {
        _uiState.update {
            it.copy(
                currentPlaceType = placeType
            )
        }
    }
}