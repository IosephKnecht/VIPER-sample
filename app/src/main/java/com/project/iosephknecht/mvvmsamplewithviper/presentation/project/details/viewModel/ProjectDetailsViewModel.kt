package com.project.iosephknecht.mvvmsamplewithviper.presentation.project.details.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.iosephknecht.mvvmsamplewithviper.data.Project
import com.project.iosephknecht.mvvmsamplewithviper.domain.GithubApiService
import com.project.iosephknecht.mvvmsamplewithviper.presentation.add
import com.project.iosephknecht.mvvmsamplewithviper.presentation.project.common.utils.SingleLiveEvent
import com.project.iosephknecht.mvvmsamplewithviper.presentation.project.details.contract.ProjectDetailsContract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ProjectDetailsViewModel(
    private val githubApiService: GithubApiService,
    private val repoName: String,
    private val userName: String
) : ViewModel(),
    ProjectDetailsContract.ViewModel {

    override val project = MutableLiveData<Project>()
    override val isLoading = MutableLiveData<Boolean>()
    override val toastMsg =
        SingleLiveEvent<String>()

    private val compositeDisposable = CompositeDisposable()

    init {
        getDetails()
    }

    private fun getDetails() {
        githubApiService.getProjectDetails(
            repoName = repoName,
            userId = userName
        )
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
                    project.value = it
                },
                {
                    toastMsg.value = it.localizedMessage
                }
            )
            .add(compositeDisposable)
    }
}