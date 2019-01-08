package com.project.iosephknecht.mvvmsamplewithviper.application.assembly

import com.project.iosephknecht.mvvmsamplewithviper.application.scopes.PerPresentationLayerScope
import com.project.iosephknecht.mvvmsamplewithviper.presentation.project.details.ProjectDetailsInputModule
import com.project.iosephknecht.mvvmsamplewithviper.presentation.project.details.contract.ProjectDetailsContract
import dagger.Module
import dagger.Provides

@Module
class PresentationModule {
    @Provides
    @PerPresentationLayerScope
    fun provideProjectDetailsInputModule(): ProjectDetailsContract.InputModule {
        return ProjectDetailsInputModule()
    }
}