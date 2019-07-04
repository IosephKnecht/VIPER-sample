package com.project.iosephknecht.mvvmsamplewithviper.presentation.project.details.assembly

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.project.iosephknecht.mvvmsamplewithviper.domain.GithubApiService
import com.project.iosephknecht.mvvmsamplewithviper.presentation.PerFeatureLayerScope
import com.project.iosephknecht.mvvmsamplewithviper.presentation.project.details.contract.ProjectDetailsContract
import com.project.iosephknecht.mvvmsamplewithviper.presentation.project.details.interactor.ProjectDetailsInteractor
import com.project.iosephknecht.mvvmsamplewithviper.presentation.project.details.presenter.ProjectDetailsPresenter
import dagger.BindsInstance
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import javax.inject.Inject
import javax.inject.Named

@Subcomponent(modules = [ProjectDetailsModule::class])
@PerFeatureLayerScope
interface ProjectDetailsComponent {
    fun getPresenter(): ProjectDetailsContract.Presenter

    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        fun with(fragment: Fragment): Builder

        @BindsInstance
        fun projectId(@Named("projectId") projectId: String): Builder

        @BindsInstance
        fun userId(@Named("userId") userId: String): Builder

        fun build(): ProjectDetailsComponent
    }
}

@Module
class ProjectDetailsModule {
    @Provides
    @PerFeatureLayerScope
    fun providePresenter(fragment: Fragment,
                         factory: ProjectDetailsFactory): ProjectDetailsContract.Presenter {
        return ViewModelProviders.of(fragment, factory).get(ProjectDetailsPresenter::class.java)
    }

    @Provides
    @PerFeatureLayerScope
    fun provideInteractor(githubApiService: GithubApiService): ProjectDetailsContract.Interactor {
        return ProjectDetailsInteractor(githubApiService)
    }
}

@PerFeatureLayerScope
class ProjectDetailsFactory @Inject constructor(private val interactor: ProjectDetailsContract.Interactor,
                                                @Named("projectId")
                                                private val projectId: String,
                                                @Named("userId")
                                                private val userId: String)
    : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ProjectDetailsPresenter(interactor, userId, projectId) as T
    }
}