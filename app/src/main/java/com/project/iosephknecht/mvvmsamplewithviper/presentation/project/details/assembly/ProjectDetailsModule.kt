package com.project.iosephknecht.mvvmsamplewithviper.presentation.project.details.assembly

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.project.iosephknecht.mvvmsamplewithviper.domain.GithubApiService
import com.project.iosephknecht.mvvmsamplewithviper.presentation.PerFeatureLayerScope
import com.project.iosephknecht.mvvmsamplewithviper.presentation.project.details.contract.ProjectDetailsContract
import com.project.iosephknecht.mvvmsamplewithviper.presentation.project.details.view.ProjectDetailsFragment
import com.project.iosephknecht.mvvmsamplewithviper.presentation.project.details.viewModel.ProjectDetailsViewModel
import dagger.BindsInstance
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import javax.inject.Inject
import javax.inject.Named

private const val REPO_NAME_NAMED_KEY = "REPO_NAME_NAMED_KEY"
private const val USER_NAME_NAMED_KEY = "USER_NAME_NAMED_KEY"

@Subcomponent(modules = [ProjectDetailsModule::class])
@PerFeatureLayerScope
interface ProjectDetailsComponent {

    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        fun with(fragment: Fragment): Builder

        @BindsInstance
        fun repoName(@Named(REPO_NAME_NAMED_KEY) repoName: String): Builder

        @BindsInstance
        fun userName(@Named(USER_NAME_NAMED_KEY) userName: String): Builder

        fun build(): ProjectDetailsComponent
    }

    fun inject(fragment: ProjectDetailsFragment)
}

@Module
class ProjectDetailsModule {
    @Provides
    @PerFeatureLayerScope
    fun provideViewModel(
        fragment: Fragment,
        factory: ProjectDetailsFactory
    ): ProjectDetailsContract.ViewModel {
        return ViewModelProvider(fragment, factory).get(ProjectDetailsViewModel::class.java)
    }
}

@PerFeatureLayerScope
class ProjectDetailsFactory @Inject constructor(
    @Named(REPO_NAME_NAMED_KEY)
    private val repoName: String,
    @Named(USER_NAME_NAMED_KEY)
    private val userName: String,
    private val githubApiService: GithubApiService
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ProjectDetailsViewModel(
            userName = userName,
            repoName = repoName,
            githubApiService = githubApiService
        ) as T
    }
}