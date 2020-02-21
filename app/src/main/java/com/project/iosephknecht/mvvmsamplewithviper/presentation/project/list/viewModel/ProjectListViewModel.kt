package com.project.iosephknecht.mvvmsamplewithviper.presentation.project.list.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.iosephknecht.mvvmsamplewithviper.data.Project
import com.project.iosephknecht.mvvmsamplewithviper.domain.GithubApiService
import com.project.iosephknecht.mvvmsamplewithviper.presentation.add
import com.project.iosephknecht.mvvmsamplewithviper.presentation.project.common.utils.SingleLiveEvent
import com.project.iosephknecht.mvvmsamplewithviper.presentation.project.list.contract.ProjectListContract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ProjectListViewModel(
    private val userName: String,
    private val githubService: GithubApiService
) : ViewModel(),
    ProjectListContract.ViewModel {

    override val projects = MutableLiveData<List<Project>>()
    override val showProjectDetail =
        SingleLiveEvent<Pair<String, String>>()
    override val toastMsg =
        SingleLiveEvent<String>()
    override val isLoading = MutableLiveData<Boolean>()

    private val compositeDisposable = CompositeDisposable()

    init {
        getProjectList(userName)
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    override fun onClickProject(project: Project) {
        val repoName = project.name

        if (repoName != null) {
            showProjectDetail.value = repoName to userName
        }
    }

    private fun getProjectList(userId: String) {
        githubService.getProjectList(userId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                isLoading.value = true
            }
            .doFinally {
                isLoading.value = false
            }
            .subscribe(
                {
                    projects.value = it
                },
                {
                    toastMsg.value = it.localizedMessage
                }
            )
            .add(compositeDisposable)
    }
}