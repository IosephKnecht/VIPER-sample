package com.project.iosephknecht.mvvmsamplewithviper.presentation.project.details.assembly

import android.app.Application
import android.arch.lifecycle.ViewModelProvider
import com.project.iosephknecht.mvvmsamplewithviper.domain.GithubApiService
import com.project.iosephknecht.mvvmsamplewithviper.presentation.PerFeatureLayerScope
import com.project.iosephknecht.mvvmsamplewithviper.presentation.project.details.contract.ProjectDetailsContract
import com.project.iosephknecht.mvvmsamplewithviper.presentation.project.details.interactor.ProjectDetailsInteractor
import com.project.iosephknecht.mvvmsamplewithviper.presentation.project.details.presenter.ProjectDetailsPresenter
import com.project.iosephknecht.viper.view.AndroidComponent
import com.project.iosephknecht.viper.viewModelProvider
import dagger.Module
import dagger.Provides

@Module
class ProjectDetailsModule(private val androidComponent: AndroidComponent,
                           private val projectId: String,
                           private val userId: String) {

    @Provides
    @PerFeatureLayerScope
    fun provideViewModelFactory(application: Application,
                                interactor: ProjectDetailsContract.Interactor): ViewModelProvider.AndroidViewModelFactory {
        return ProjectDetailsPresenter.ProjectDetailsFactory(application, interactor, projectId, userId)
    }

    @Provides
    @PerFeatureLayerScope
    fun providePresenter(factory: ViewModelProvider.AndroidViewModelFactory): ProjectDetailsContract.Presenter {
        return viewModelProvider(androidComponent, factory).get(ProjectDetailsPresenter::class.java)
    }

    @Provides
    @PerFeatureLayerScope
    fun provideInteractor(githubApiService: GithubApiService): ProjectDetailsContract.Interactor {
        return ProjectDetailsInteractor(githubApiService)
    }
}