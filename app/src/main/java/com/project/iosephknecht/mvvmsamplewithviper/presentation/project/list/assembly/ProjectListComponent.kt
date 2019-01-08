package com.project.iosephknecht.mvvmsamplewithviper.presentation.project.list.assembly

import com.project.iosephknecht.mvvmsamplewithviper.presentation.PerFeatureLayerScope
import com.project.iosephknecht.mvvmsamplewithviper.presentation.project.list.contract.ProjectListContract
import dagger.Subcomponent

@Subcomponent(modules = [ProjectListModule::class])
@PerFeatureLayerScope
interface ProjectListComponent {
    fun getPresenter(): ProjectListContract.Presenter
}