package ru.tutunnikov.mycity.ui

import ru.tutunnikov.mycity.data.Place
import ru.tutunnikov.mycity.data.PlaceType
import ru.tutunnikov.mycity.data.local.LocalPlacesDataProvider

data class MyCityUiState(
    val currentSelectedPlace: Place = LocalPlacesDataProvider.defaultPlace,
    val currentPlaceType: PlaceType = PlaceType.Food,
    val isShowingHomeScreen: Boolean = true,
    val placesMap: Map<PlaceType, List<Place>> = emptyMap()
) {
    val currentPlacesList: List<Place> by lazy { placesMap[currentPlaceType]!! }
}
