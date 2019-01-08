package com.project.iosephknecht.mvvmsamplewithviper.application.assembly

import android.app.Application
import android.content.Context
import com.project.iosephknecht.mvvmsamplewithviper.application.scopes.PerApplicationLayerScope
import dagger.Component

@Component(modules = [AppModule::class])
@PerApplicationLayerScope
interface AppComponent {
    fun getContext(): Context
    fun getApplication(): Application
}