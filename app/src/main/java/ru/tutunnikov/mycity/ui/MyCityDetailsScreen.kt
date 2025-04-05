package ru.tutunnikov.mycity.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.mapview.MapView
import ru.tutunnikov.mycity.R
import ru.tutunnikov.mycity.data.Place
import ru.tutunnikov.mycity.data.PlaceType
import ru.tutunnikov.mycity.data.local.LocalPlacesDataProvider
import ru.tutunnikov.mycity.ui.theme.Typography

@Composable
fun DetailsScreen(
    place: Place,
    modifier: Modifier = Modifier
) {
    val configuration = LocalConfiguration.current
    val title = when(place.placeType) {
        PlaceType.Gym -> R.string.gym
        PlaceType.Food -> R.string.food
        PlaceType.Mall -> R.string.mall
        PlaceType.Coffee -> R.string.coffee
        else -> R.string.food
    }


    LazyColumn(
        contentPadding = WindowInsets.safeDrawing.asPaddingValues(),
        modifier = Modifier
            .fillMaxSize()
    ) {
        item {
            DetailsScreenTopBar(title = title)

            Image(
                painter = painterResource(place.imageId),
                contentDescription = stringResource(place.name),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Text(
                    text = stringResource(place.name),
                    style = Typography.bodyLarge,
                    fontSize = 40.sp,
                    modifier = Modifier.padding(top = 16.dp)
                )
                Text(
                    text = stringResource(place.address),
                    style = Typography.bodyLarge,
                    color = Color(0x86000000)
                )
                Spacer(Modifier.padding(16.dp))
                YandexMap(
                    initialLocation = place.mapCoordinates,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(configuration.screenWidthDp.dp)
                        .clip(MaterialTheme.shapes.medium)
                )
            }

        }
    }
}

@Composable
fun DetailsScreenTopBar(
    title: Int,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.fillMaxWidth().padding(vertical = 8.dp)
    ) {
//        IconButton(
//            onClick = TODO("onclick")
//        ) {
//            Icon(
//                imageVector = Icons.Rounded.ArrowBack,
//                contentDescription = null
//            )
//        }
        Icon(
            imageVector = Icons.Rounded.ArrowBack,
            contentDescription = null,
            modifier = Modifier.size(35.dp)
        )
        Spacer(Modifier.weight(1f))
        Text(
            text = stringResource(title),
            style = Typography.bodyLarge,
            fontSize = 30.sp,
        )
        Spacer(Modifier.weight(1.5f))
    }

}

@Composable
fun YandexMap(
    modifier: Modifier = Modifier,
    initialLocation: Point = Point(48.470589, 38.829373) // Москва по умолчанию
) {
    val context = LocalContext.current
    val mapView = remember { MapView(context) }

    DisposableEffect(Unit) {
        MapKitFactory.getInstance().onStart()
        onDispose {
            mapView.onStop()
            MapKitFactory.getInstance().onStop()
        }
    }

    AndroidView(
        modifier = modifier,
        factory = { mapView }
    ) { view ->
        view.mapWindow.map.move(
            CameraPosition(initialLocation, 18f, 0f, 0f),
            Animation(Animation.Type.SMOOTH, 1f),
            null
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun DetailsScreenPreview() {
    DetailsScreen(LocalPlacesDataProvider.listOfPlaces[6])
}