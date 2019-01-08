package com.project.iosephknecht.mvvmsamplewithviper.presentation.project.list.presenter

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.project.iosephknecht.mvvmsamplewithviper.data.gson.Project
import com.project.iosephknecht.mvvmsamplewithviper.presentation.project.list.contract.ProjectListContract
import com.project.iosephknecht.mvvmsamplewithviper.presentation.project.list.contract.ProjectListContract.Presenter.State
import com.project.iosephknecht.viper.presenter.AbstractPresenter
import com.project.iosephknecht.viper.view.AndroidComponent

class ProjectListPresenter private constructor(application: Application,
                                               private val interactor: ProjectListContract.Interactor,
                                               private val router: ProjectListContract.Router)
    : AbstractPresenter(application), ProjectListContract.Presenter, ProjectListContract.Listener, ProjectListContract.ObserverStorage {

    private var state: State = State.IDLE

    override val projectList = MutableLiveData<List<Project>>()

    override fun attachAndroidComponent(androidComponent: AndroidComponent) {
        super.attachAndroidComponent(androidComponent)
        interactor.setListener(this)
        registerObservers(projectList)

        if (state == State.IDLE) {
            obtainProjectList("Google")
        }
    }

    override fun detachAndroidComponent() {
        interactor.setListener(null)
        unregisterObservers()
        super.detachAndroidComponent()
    }

    override fun obtainProjectList(userId: String) {
        interactor.getProjectList(userId)
    }

    override fun showProjectDetails(userId: String, projectId: String) {
        router.showProjectDetails(androidComponent!!, userId, projectId)
    }

    override fun onObtainProjectList(projectList: List<Project>?, throwable: Throwable?) {
        state = State.INIT
        this.projectList.postValue(projectList)
    }

    override fun onDestroy() {
        interactor.onDestroy()
    }

    class ProjectListFactory(private val application: Application,
                             private val interactor: ProjectListContract.Interactor,
                             private val router: ProjectListContract.Router)
        : ViewModelProvider.AndroidViewModelFactory(application) {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ProjectListPresenter(application, interactor, router) as T
        }
    }
}