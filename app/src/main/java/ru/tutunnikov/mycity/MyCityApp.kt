package ru.tutunnikov.mycity

import android.app.Application
import com.yandex.mapkit.MapKitFactory

class MyCityApp : Application() {
    override fun onCreate() {
        super.onCreate()

        MapKitFactory.setApiKey(BuildConfig.API_KEY)
        MapKitFactory.initialize(this)
    }
}