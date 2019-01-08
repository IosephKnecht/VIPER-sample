package com.project.iosephknecht.mvvmsamplewithviper.presentation.project.details.assembly

import com.project.iosephknecht.mvvmsamplewithviper.presentation.PerFeatureLayerScope
import com.project.iosephknecht.mvvmsamplewithviper.presentation.project.details.contract.ProjectDetailsContract
import dagger.Subcomponent

@Subcomponent(modules = [ProjectDetailsModule::class])
@PerFeatureLayerScope
interface ProjectDetailsComponent {
    fun getPresenter(): ProjectDetailsContract.Presenter
}