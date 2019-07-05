package com.project.iosephknecht.mvvmsamplewithviper.presentation.project.details.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.project.iosephknecht.mvvmsamplewithviper.R
import com.project.iosephknecht.mvvmsamplewithviper.application.AppDelegate
import com.project.iosephknecht.mvvmsamplewithviper.databinding.FragmentProjectDetailsBinding
import com.project.iosephknecht.mvvmsamplewithviper.presentation.project.details.assembly.ProjectDetailsComponent
import com.project.iosephknecht.mvvmsamplewithviper.presentation.project.details.contract.ProjectDetailsContract
import com.project.iosephknecht.viper.view.AbstractFragment

class ProjectDetailsFragment : AbstractFragment<ProjectDetailsContract.Presenter>() {
    private lateinit var diComponent: ProjectDetailsComponent
    private lateinit var binding: FragmentProjectDetailsBinding

    companion object {
        const val TAG = "project_fragment"
        private const val USER_ID_KEY = "user_id_key"
        private const val PROJECT_NAME_KEY = "project_name_key"

        fun createInstance(userId: String, projectName: String) = ProjectDetailsFragment().apply {
            arguments = Bundle().apply {
                putString(USER_ID_KEY, userId)
                putString(PROJECT_NAME_KEY, projectName)
            }
        }
    }

    override fun inject() {
        val userId = arguments?.getString(USER_ID_KEY)
            ?: throw RuntimeException("user id could not be null")
        val projectId = arguments?.getString(PROJECT_NAME_KEY)
            ?: throw RuntimeException("project id could not be null")

        diComponent = AppDelegate.presentationComponent
            .projectDetailsSubcomponent()
            .with(this)
            .projectId(projectId)
            .userId(userId)
            .build()
    }

    override fun providePresenter(): ProjectDetailsContract.Presenter = diComponent.getPresenter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_project_details, container, false)
        binding.setLifecycleOwner(this)

        binding.viewModel = presenter
        binding.isLoading = true

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        presenter.project.observe(viewLifecycleOwner, Observer {
            if (it != null) binding.isLoading = false
        })
    }
}