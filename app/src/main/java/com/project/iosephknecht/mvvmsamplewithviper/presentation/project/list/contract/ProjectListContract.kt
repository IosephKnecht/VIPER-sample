package com.project.iosephknecht.mvvmsamplewithviper.presentation.project.list.contract

import androidx.lifecycle.LiveData
import com.project.iosephknecht.mvvmsamplewithviper.data.Project
import com.project.iosephknecht.mvvmsamplewithviper.presentation.project.common.viewModel.ViewModelArch

interface ProjectListContract {
    interface ViewModel :
        ViewModelArch {
        val projects: LiveData<List<Project>>
        val showProjectDetail: LiveData<Pair<String, String>>
        val toastMsg: LiveData<String>
        val isLoading: LiveData<Boolean>

        fun onClickProject(project: Project)
    }
}