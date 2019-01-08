package com.project.iosephknecht.mvvmsamplewithviper.application.assembly

import android.app.Application
import com.project.iosephknecht.mvvmsamplewithviper.application.scopes.PerBusinessLayerScope
import com.project.iosephknecht.mvvmsamplewithviper.domain.GithubApiService
import dagger.Component

@Component(modules = [BusinessModule::class], dependencies = [AppComponent::class])
@PerBusinessLayerScope
interface BusinessComponent {
    fun getApplication(): Application

    fun getGithubApiService(): GithubApiService
}