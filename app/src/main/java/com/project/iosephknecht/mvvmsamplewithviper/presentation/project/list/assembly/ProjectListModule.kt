package com.project.iosephknecht.mvvmsamplewithviper.presentation.project.list.assembly

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.project.iosephknecht.mvvmsamplewithviper.domain.GithubApiService
import com.project.iosephknecht.mvvmsamplewithviper.presentation.PerFeatureLayerScope
import com.project.iosephknecht.mvvmsamplewithviper.presentation.project.details.contract.ProjectDetailsContract
import com.project.iosephknecht.mvvmsamplewithviper.presentation.project.list.contract.ProjectListContract
import com.project.iosephknecht.mvvmsamplewithviper.presentation.project.list.interactor.ProjectListInteractor
import com.project.iosephknecht.mvvmsamplewithviper.presentation.project.list.presenter.ProjectListPresenter
import com.project.iosephknecht.mvvmsamplewithviper.presentation.project.list.router.ProjectListRouter
import dagger.BindsInstance
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import javax.inject.Inject

@Subcomponent(modules = [ProjectListModule::class])
@PerFeatureLayerScope
interface ProjectListComponent {
    fun getPresenter(): ProjectListContract.Presenter

    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        fun with(fragment: Fragment): Builder

        fun build(): ProjectListComponent
    }
}

@Module
class ProjectListModule {
    @Provides
    @PerFeatureLayerScope
    fun providePresenter(fragment: Fragment,
                         factory: ProjectListFactory): ProjectListContract.Presenter {
        return ViewModelProviders.of(fragment, factory).get(ProjectListPresenter::class.java)
    }

    @Provides
    @PerFeatureLayerScope
    fun provideInteractor(githubApiService: GithubApiService): ProjectListContract.Interactor {
        return ProjectListInteractor(githubApiService)
    }

    @Provides
    @PerFeatureLayerScope
    fun provideRouter(projectDetailsInputModule: ProjectDetailsContract.InputModule): ProjectListContract.Router {
        return ProjectListRouter(projectDetailsInputModule)
    }
}

class ProjectListFactory @Inject constructor(private val interactor: ProjectListContract.Interactor,
                                             private val router: ProjectListContract.Router)
    : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ProjectListPresenter(interactor, router) as T
    }
}