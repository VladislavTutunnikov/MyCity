package ru.tutunnikov.mycity.ui

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
import ru.tutunnikov.mycity.ui.theme.Typography

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetailsScreen(
    place: Place,
    onDetailScreenBackPressed: () -> Unit,
    isFullScreen: Boolean = false,
    modifier: Modifier = Modifier
) {

    BackHandler {
        onDetailScreenBackPressed()
    }

    val title = when (place.placeType) {
        PlaceType.Gym -> R.string.gym
        PlaceType.Food -> R.string.food
        PlaceType.Mall -> R.string.mall
        PlaceType.Coffee -> R.string.coffee
        else -> R.string.food
    }

    Scaffold(
        topBar = {
            if (isFullScreen) {
                DetailsScreenTopBar(
                    title = title,
                    onDetailScreenBackPressed = onDetailScreenBackPressed
                )
            }
        },
        modifier = modifier
    ) {
        DetailsScreenContent(
            place = place,
//            modifier = modifier
            //.padding(it)
        )
    }
}

@Composable
fun DetailsScreenContent(
    place: Place,
    modifier: Modifier = Modifier
) {
    val screenConfiguration = LocalConfiguration.current
    Box(modifier = modifier) {
        LazyColumn(
            contentPadding = WindowInsets.safeDrawing.asPaddingValues(),
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.inverseOnSurface)
        ) {
            item {
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
                            .fillParentMaxWidth()
                            .fillParentMaxHeight()
                            .clip(MaterialTheme.shapes.medium)
                    )
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreenTopBar(
    title: Int,
    onDetailScreenBackPressed: () -> Unit,
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(title),
                style = Typography.bodyLarge,
                fontSize = 25.sp,
            )
        },
        navigationIcon = @androidx.compose.runtime.Composable {
            IconButton(
                onClick = onDetailScreenBackPressed,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .size(35.dp)
            ) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBack,
                    contentDescription = null,
                )
            }
        },
        windowInsets = WindowInsets(
            top = 0.dp,
            bottom = 0.dp
        ),
        modifier = modifier.background(MaterialTheme.colorScheme.inverseOnSurface)
    )

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
        mapView.onStart()
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