package com.project.iosephknecht.mvvmsamplewithviper.presentation.project.list.router

import com.project.iosephknecht.mvvmsamplewithviper.R
import com.project.iosephknecht.mvvmsamplewithviper.presentation.project.details.contract.ProjectDetailsContract
import com.project.iosephknecht.mvvmsamplewithviper.presentation.project.details.view.ProjectDetailsFragment
import com.project.iosephknecht.mvvmsamplewithviper.presentation.project.list.contract.ProjectListContract
import com.project.iosephknecht.viper.view.AndroidComponent

class ProjectListRouter(private val projectDetailsInputModule: ProjectDetailsContract.InputModule)
    : ProjectListContract.Router {
    override fun showProjectDetails(androidComponent: AndroidComponent, userId: String, projectId: String) {
        androidComponent.fragmentManagerComponent?.apply {
            beginTransaction()
                .replace(R.id.fragment_container,
                    projectDetailsInputModule.createFragment(userId, projectId),
                    ProjectDetailsFragment.TAG)
                .addToBackStack(null)
                .commit()
        }
    }
}