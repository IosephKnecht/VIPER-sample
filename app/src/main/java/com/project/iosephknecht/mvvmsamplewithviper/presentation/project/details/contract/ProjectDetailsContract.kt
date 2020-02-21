package com.project.iosephknecht.mvvmsamplewithviper.presentation.project.details.contract

import androidx.lifecycle.LiveData
import com.project.iosephknecht.mvvmsamplewithviper.data.Project
import com.project.iosephknecht.mvvmsamplewithviper.presentation.project.common.viewModel.ViewModelArch

interface ProjectDetailsContract {
    interface ViewModel :
        ViewModelArch {
        val project: LiveData<Project>
        val isLoading: LiveData<Boolean>
        val toastMsg: LiveData<String>
    }
}