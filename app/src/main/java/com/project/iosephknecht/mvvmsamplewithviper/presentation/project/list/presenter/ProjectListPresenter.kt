package com.project.iosephknecht.mvvmsamplewithviper.presentation.project.list.presenter

import androidx.lifecycle.MutableLiveData
import com.project.iosephknecht.mvvmsamplewithviper.data.gson.Project
import com.project.iosephknecht.mvvmsamplewithviper.presentation.project.list.contract.ProjectListContract
import com.project.iosephknecht.mvvmsamplewithviper.presentation.project.list.contract.ProjectListContract.Presenter.State
import com.project.iosephknecht.viper.presenter.AbstractPresenter
import com.project.iosephknecht.viper.view.AndroidComponent

class ProjectListPresenter constructor(private val interactor: ProjectListContract.Interactor,
                                       private val router: ProjectListContract.Router)
    : AbstractPresenter(),
    ProjectListContract.Presenter,
    ProjectListContract.Listener,
    ProjectListContract.RouterListener {

    private var state: State = State.IDLE

    override val projectList = MutableLiveData<List<Project>>()

    override fun attachAndroidComponent(androidComponent: AndroidComponent) {
        super.attachAndroidComponent(androidComponent)
        interactor.setListener(this)
        router.setListener(this)

        if (state == State.IDLE) {
            obtainProjectList("Google")
        }
    }

    override fun detachAndroidComponent() {
        interactor.setListener(null)
        router.setListener(null)
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
}