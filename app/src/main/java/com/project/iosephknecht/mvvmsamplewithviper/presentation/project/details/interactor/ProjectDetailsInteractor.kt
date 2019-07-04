package com.project.iosephknecht.mvvmsamplewithviper.presentation.project.details.interactor

import com.project.iosephknecht.mvvmsamplewithviper.domain.GithubApiService
import com.project.iosephknecht.mvvmsamplewithviper.presentation.project.details.contract.ProjectDetailsContract
import com.project.iosephknecht.viper.interacor.AbstractInteractor
import io.reactivex.disposables.CompositeDisposable

class ProjectDetailsInteractor(private val githubApiService: GithubApiService) : AbstractInteractor<ProjectDetailsContract.Listener>(),
    ProjectDetailsContract.Interactor {

    private val compositeDisposable = CompositeDisposable()

    override fun getProjectDetails(projectId: String, userId: String) {
        val observable = githubApiService.getProjectDetails(projectId, userId)

        compositeDisposable.add(discardResult(observable) { listener, result ->
            listener!!.onObtainProjectDetails(result.data, result.throwable)
        })
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }
}