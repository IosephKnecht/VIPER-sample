package com.project.iosephknecht.mvvmsamplewithviper.application.assembly

import com.project.iosephknecht.mvvmsamplewithviper.application.scopes.PerPresentationLayerScope
import com.project.iosephknecht.mvvmsamplewithviper.presentation.project.details.assembly.ProjectDetailsComponent
import com.project.iosephknecht.mvvmsamplewithviper.presentation.project.list.assembly.ProjectListComponent
import dagger.Component

@Component(modules = [PresentationModule::class], dependencies = [BusinessComponent::class])
@PerPresentationLayerScope
interface PresentationComponent {
    fun projectListSubcomponent(): ProjectListComponent.Builder
    fun projectDetailsSubcomponent(): ProjectDetailsComponent.Builder
}