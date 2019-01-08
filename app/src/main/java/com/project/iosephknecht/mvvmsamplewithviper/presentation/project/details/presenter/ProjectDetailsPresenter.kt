package com.project.iosephknecht.mvvmsamplewithviper.presentation.project.details.presenter

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.databinding.ObservableField
import com.project.iosephknecht.mvvmsamplewithviper.data.gson.Project
import com.project.iosephknecht.mvvmsamplewithviper.presentation.project.details.contract.ProjectDetailsContract
import com.project.iosephknecht.viper.presenter.AbstractPresenter
import com.project.iosephknecht.viper.view.AndroidComponent
import com.project.iosephknecht.mvvmsamplewithviper.presentation.project.details.contract.ProjectDetailsContract.State

class ProjectDetailsPresenter(application: Application,
                              private val interactor: ProjectDetailsContract.Interactor,
                              private val projectId: String,
                              private val userId: String) : AbstractPresenter(application),
    ProjectDetailsContract.Presenter, ProjectDetailsContract.Listener, ProjectDetailsContract.ViewModelObserver {

    private var state = State.IDLE
    override val project = MutableLiveData<Project>()

    override fun attachAndroidComponent(androidComponent: AndroidComponent) {
        super.attachAndroidComponent(androidComponent)
        interactor.setListener(this)
        registerObservers(project)

        if (state == State.IDLE) {
            obtainProjectDetails(userId, projectId)
        }
    }

    override fun detachAndroidComponent() {
        interactor.setListener(null)
        unregisterObservers()
        super.detachAndroidComponent()
    }

    override fun onDestroy() {
        interactor.onDestroy()
    }

    override fun obtainProjectDetails(userId: String, projectId: String) {
        interactor.getProjectDetails(userId, projectId)
    }

    override fun onObtainProjectDetails(project: Project?, throwable: Throwable?) {
        if (project != null) {
            this.project.postValue(project)
            state = State.INIT
        }
    }

    class ProjectDetailsFactory(private val application: Application,
                                private val interactor: ProjectDetailsContract.Interactor,
                                private val projectId: String,
                                private val userId: String)
        : ViewModelProvider.AndroidViewModelFactory(application) {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ProjectDetailsPresenter(application, interactor, userId, projectId) as T
        }
    }
}