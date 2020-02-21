package com.project.iosephknecht.mvvmsamplewithviper.presentation.project.list.assembly

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.project.iosephknecht.mvvmsamplewithviper.domain.GithubApiService
import com.project.iosephknecht.mvvmsamplewithviper.presentation.PerFeatureLayerScope
import com.project.iosephknecht.mvvmsamplewithviper.presentation.project.list.contract.ProjectListContract
import com.project.iosephknecht.mvvmsamplewithviper.presentation.project.list.view.ProjectListFragment
import com.project.iosephknecht.mvvmsamplewithviper.presentation.project.list.viewModel.ProjectListViewModel
import dagger.BindsInstance
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import javax.inject.Inject
import javax.inject.Named

private const val USER_NAME_KEY_NAMED = "USER_NAME_KEY_NAMED"

@Subcomponent(modules = [ProjectListModule::class])
@PerFeatureLayerScope
interface ProjectListComponent {
    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        fun with(fragment: Fragment): Builder

        @BindsInstance
        fun userName(@Named(USER_NAME_KEY_NAMED) userId: String): Builder

        fun build(): ProjectListComponent
    }

    fun inject(fragment: ProjectListFragment)
}

@Module
class ProjectListModule {
    @Provides
    @PerFeatureLayerScope
    fun provideViewModel(
        fragment: Fragment,
        factory: ProjectListFactory
    ): ProjectListContract.ViewModel {
        return ViewModelProvider(fragment, factory).get(ProjectListViewModel::class.java)
    }
}

@PerFeatureLayerScope
class ProjectListFactory @Inject constructor(
    @Named(USER_NAME_KEY_NAMED)
    private val userName: String,
    private val githubApiService: GithubApiService
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ProjectListViewModel(
            userName = userName,
            githubService = githubApiService
        ) as T
    }
}