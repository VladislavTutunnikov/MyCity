package ru.tutunnikov.mycity.data

import android.util.EventLogTags.Description
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.yandex.mapkit.geometry.Point

data class Place(
    @DrawableRes val imageId: Int,
    @StringRes val name: Int,
    @StringRes val address: Int,
    val mapCoordinates: Point,
    val placeType: PlaceType
)
