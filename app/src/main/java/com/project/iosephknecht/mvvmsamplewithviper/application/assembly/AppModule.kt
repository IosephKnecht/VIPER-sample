package com.project.iosephknecht.mvvmsamplewithviper.application.assembly

import android.app.Application
import android.content.Context
import com.project.iosephknecht.mvvmsamplewithviper.application.AppDelegate
import com.project.iosephknecht.mvvmsamplewithviper.application.scopes.PerApplicationLayerScope
import dagger.Module
import dagger.Provides

@Module
class AppModule(private val context: Context) {

    @Provides
    @PerApplicationLayerScope
    fun provideContext() = context

    @Provides
    @PerApplicationLayerScope
    fun provideApplication(): Application {
        return context as AppDelegate
    }
}