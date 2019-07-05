package com.project.iosephknecht.mvvmsamplewithviper.presentation.project.list.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.iosephknecht.mvvmsamplewithviper.R
import com.project.iosephknecht.mvvmsamplewithviper.application.AppDelegate
import com.project.iosephknecht.mvvmsamplewithviper.databinding.FragmentProjectListBinding
import com.project.iosephknecht.mvvmsamplewithviper.presentation.project.list.assembly.ProjectListComponent
import com.project.iosephknecht.mvvmsamplewithviper.presentation.project.list.contract.ProjectListContract
import com.project.iosephknecht.mvvmsamplewithviper.presentation.project.list.presenter.ProjectListPresenter
import com.project.iosephknecht.mvvmsamplewithviper.presentation.project.list.view.adapter.ProjectListAdapter
import com.project.iosephknecht.viper.view.AbstractFragment
import kotlinx.android.synthetic.main.fragment_project_list.*

class ProjectListFragment : AbstractFragment<ProjectListContract.Presenter>() {
    private lateinit var diComponent: ProjectListComponent
    private lateinit var binding: FragmentProjectListBinding
    private lateinit var adapter: ProjectListAdapter

    companion object {
        const val TAG = "project_list_fragment"

        fun createInstance() = ProjectListFragment()
    }

    override fun inject() {
        diComponent = AppDelegate.presentationComponent
            .projectListSubcomponent()
            .with(this)
            .build()
    }

    override fun providePresenter() = diComponent.getPresenter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_project_list, container, false)
        binding.isLoading = true

        adapter = ProjectListAdapter { presenter.showProjectDetails("Google", it.name!!) }

        observeViewModel(diComponent.getPresenter() as ProjectListPresenter)

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        project_list.apply {
            this.adapter = this@ProjectListFragment.adapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(false)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL))
        }
    }

    private fun observeViewModel(projectViewModel: ProjectListPresenter) {
        projectViewModel.projectList.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                adapter.projectList = it
                adapter.notifyDataSetChanged()
                binding.isLoading = false
            }
        })
    }
}