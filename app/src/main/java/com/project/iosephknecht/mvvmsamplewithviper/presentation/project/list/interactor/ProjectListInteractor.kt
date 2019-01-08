package com.project.iosephknecht.mvvmsamplewithviper.presentation.project.list.interactor

import com.project.iosephknecht.mvvmsamplewithviper.domain.GithubApiService
import com.project.iosephknecht.mvvmsamplewithviper.presentation.project.list.contract.ProjectListContract
import com.project.iosephknecht.viper.interacor.AbstractInteractor
import io.reactivex.disposables.CompositeDisposable

class ProjectListInteractor(private val githubApiService: GithubApiService) : AbstractInteractor<ProjectListContract.Listener>(),
    ProjectListContract.Interactor {

    private val compositeDisposable = CompositeDisposable()

    override fun getProjectList(userId: String) {
        val observable = githubApiService.getProjectList(userId)

        compositeDisposable.add(discardResult(observable) { listener, result ->
            listener!!.onObtainProjectList(result.data, result.throwable)
        })
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }
}