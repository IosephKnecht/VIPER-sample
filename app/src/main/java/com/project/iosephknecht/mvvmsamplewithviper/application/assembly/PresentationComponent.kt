package com.project.iosephknecht.mvvmsamplewithviper.application.assembly

import com.project.iosephknecht.mvvmsamplewithviper.application.scopes.PerPresentationLayerScope
import com.project.iosephknecht.mvvmsamplewithviper.presentation.project.details.assembly.ProjectDetailsComponent
import com.project.iosephknecht.mvvmsamplewithviper.presentation.project.details.assembly.ProjectDetailsModule
import com.project.iosephknecht.mvvmsamplewithviper.presentation.project.list.assembly.ProjectListComponent
import com.project.iosephknecht.mvvmsamplewithviper.presentation.project.list.assembly.ProjectListModule
import dagger.Component

@Component(modules = [PresentationModule::class], dependencies = [BusinessComponent::class])
@PerPresentationLayerScope
interface PresentationComponent {
    fun projectListSubcomponent(module: ProjectListModule): ProjectListComponent
    fun projectDetailsSubcomponent(module: ProjectDetailsModule): ProjectDetailsComponent
}