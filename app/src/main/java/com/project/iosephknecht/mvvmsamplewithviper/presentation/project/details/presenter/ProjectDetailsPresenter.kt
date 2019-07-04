package com.project.iosephknecht.mvvmsamplewithviper.presentation.project.details.presenter

import androidx.lifecycle.MutableLiveData
import com.project.iosephknecht.mvvmsamplewithviper.data.gson.Project
import com.project.iosephknecht.mvvmsamplewithviper.presentation.project.details.contract.ProjectDetailsContract
import com.project.iosephknecht.mvvmsamplewithviper.presentation.project.details.contract.ProjectDetailsContract.State
import com.project.iosephknecht.viper.presenter.AbstractPresenter
import com.project.iosephknecht.viper.view.AndroidComponent

class ProjectDetailsPresenter(private val interactor: ProjectDetailsContract.Interactor,
                              private val projectId: String,
                              private val userId: String) : AbstractPresenter(),
    ProjectDetailsContract.Presenter, ProjectDetailsContract.Listener, ProjectDetailsContract.ViewModelObserver {

    private var state = State.IDLE
    override val project = MutableLiveData<Project>()

    override fun attachAndroidComponent(androidComponent: AndroidComponent) {
        super.attachAndroidComponent(androidComponent)
        interactor.setListener(this)

        if (state == State.IDLE) {
            obtainProjectDetails(projectId, userId)
        }
    }

    override fun detachAndroidComponent() {
        interactor.setListener(null)
        super.detachAndroidComponent()
    }

    override fun onDestroy() {
        interactor.onDestroy()
    }

    override fun obtainProjectDetails(projectId: String, userId: String) {
        interactor.getProjectDetails(userId, projectId)
    }

    override fun onObtainProjectDetails(project: Project?, throwable: Throwable?) {
        if (project != null) {
            this.project.postValue(project)
            state = State.INIT
        }
    }
}