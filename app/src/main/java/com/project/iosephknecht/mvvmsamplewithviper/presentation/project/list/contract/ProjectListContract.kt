package com.project.iosephknecht.mvvmsamplewithviper.presentation.project.list.contract

import android.arch.lifecycle.LiveData
import com.project.iosephknecht.mvvmsamplewithviper.data.gson.Project
import com.project.iosephknecht.viper.interacor.MvpInteractor
import com.project.iosephknecht.viper.presenter.MvpPresenter
import com.project.iosephknecht.viper.view.AndroidComponent

interface ProjectListContract {
    interface Listener : MvpInteractor.Listener {
        fun onObtainProjectList(projectList: List<Project>?, throwable: Throwable?)
    }

    interface Interactor : MvpInteractor<Listener> {
        fun getProjectList(userId: String)
    }

    interface ObserverStorage {
        val projectList: LiveData<List<Project>>
    }

    interface Presenter : MvpPresenter {
        enum class State {
            IDLE, INIT
        }

        fun obtainProjectList(userId: String)

        fun showProjectDetails(userId: String, projectId: String)
    }

    interface Router {
        fun showProjectDetails(androidComponent: AndroidComponent,
                               userId: String,
                               projectId: String)
    }
}