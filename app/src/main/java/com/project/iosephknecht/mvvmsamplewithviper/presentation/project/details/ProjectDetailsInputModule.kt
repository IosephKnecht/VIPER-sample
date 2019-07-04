package com.project.iosephknecht.mvvmsamplewithviper.presentation.project.details

import androidx.fragment.app.Fragment
import com.project.iosephknecht.mvvmsamplewithviper.presentation.project.details.contract.ProjectDetailsContract
import com.project.iosephknecht.mvvmsamplewithviper.presentation.project.details.view.ProjectDetailsFragment

class ProjectDetailsInputModule : ProjectDetailsContract.InputModule {
    override fun createFragment(userId: String, projectId: String): Fragment {
        return ProjectDetailsFragment.createInstance(userId, projectId)
    }
}