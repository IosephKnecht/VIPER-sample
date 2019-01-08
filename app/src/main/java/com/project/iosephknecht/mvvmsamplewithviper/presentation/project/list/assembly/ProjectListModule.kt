package com.project.iosephknecht.mvvmsamplewithviper.presentation.project.list.assembly

import android.app.Application
import android.arch.lifecycle.ViewModelProvider
import com.project.iosephknecht.mvvmsamplewithviper.domain.GithubApiService
import com.project.iosephknecht.mvvmsamplewithviper.presentation.PerFeatureLayerScope
import com.project.iosephknecht.mvvmsamplewithviper.presentation.project.details.contract.ProjectDetailsContract
import com.project.iosephknecht.mvvmsamplewithviper.presentation.project.list.contract.ProjectListContract
import com.project.iosephknecht.mvvmsamplewithviper.presentation.project.list.interactor.ProjectListInteractor
import com.project.iosephknecht.mvvmsamplewithviper.presentation.project.list.presenter.ProjectListPresenter
import com.project.iosephknecht.mvvmsamplewithviper.presentation.project.list.router.ProjectListRouter
import com.project.iosephknecht.viper.view.AndroidComponent
import com.project.iosephknecht.viper.viewModelProvider
import dagger.Module
import dagger.Provides

@Module
class ProjectListModule(private val androidComponent: AndroidComponent) {
    @Provides
    @PerFeatureLayerScope
    fun providePresenter(factory: ViewModelProvider.AndroidViewModelFactory): ProjectListContract.Presenter {
        return viewModelProvider(androidComponent, factory).get(ProjectListPresenter::class.java)
    }

    @Provides
    @PerFeatureLayerScope
    fun provideInteractor(githubApiService: GithubApiService): ProjectListContract.Interactor {
        return ProjectListInteractor(githubApiService)
    }

    @Provides
    @PerFeatureLayerScope
    fun provideViewModelFactory(application: Application,
                                interactor: ProjectListContract.Interactor,
                                router: ProjectListContract.Router): ViewModelProvider.AndroidViewModelFactory {
        return ProjectListPresenter.ProjectListFactory(application, interactor, router)
    }

    @Provides
    @PerFeatureLayerScope
    fun provideRouter(projectDetailsInputModule: ProjectDetailsContract.InputModule): ProjectListContract.Router {
        return ProjectListRouter(projectDetailsInputModule)
    }
}