package com.project.iosephknecht.mvvmsamplewithviper.presentation.project.details.contract

import android.arch.lifecycle.LiveData
import android.support.v4.app.Fragment
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

    interface Presenter : MvpPresenter {
        fun obtainProjectDetails(userId: String, projectId: String)
    }

    interface Listener : MvpInteractor.Listener {
        fun onObtainProjectDetails(project: Project?, throwable: Throwable?)
    }

    interface Interactor : MvpInteractor<Listener> {
        fun getProjectDetails(userId: String, projectId: String)
    }

    interface InputModule {
        fun createFragment(userId: String, projectId: String): Fragment
    }
}