package com.project.iosephknecht.mvvmsamplewithviper.presentation.project.details.contract

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import com.project.iosephknecht.mvvmsamplewithviper.data.gson.Project
import com.project.iosephknecht.viper.interacor.MvpInteractor
import com.project.iosephknecht.viper.presenter.MvpPresenter

interface ProjectDetailsContract {
    enum class State {
        IDLE, INIT
    }

    interface ViewModelObserver {
        val project: LiveData<Project>
    }

    interface Presenter : MvpPresenter, ViewModelObserver {
        fun obtainProjectDetails(projectId: String, userId: String)
    }

    interface Listener : MvpInteractor.Listener {
        fun onObtainProjectDetails(project: Project?, throwable: Throwable?)
    }

    interface Interactor : MvpInteractor<Listener> {
        fun getProjectDetails(projectId: String, userId: String)
    }

    interface InputModule {
        fun createFragment(userId: String, projectId: String): Fragment
    }
}