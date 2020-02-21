package com.project.iosephknecht.mvvmsamplewithviper.presentation.project.list.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.iosephknecht.mvvmsamplewithviper.R
import com.project.iosephknecht.mvvmsamplewithviper.application.AppDelegate
import com.project.iosephknecht.mvvmsamplewithviper.databinding.FragmentProjectListBinding
import com.project.iosephknecht.mvvmsamplewithviper.presentation.project.details.view.ProjectDetailsFragment
import com.project.iosephknecht.mvvmsamplewithviper.presentation.project.list.contract.ProjectListContract
import com.project.iosephknecht.mvvmsamplewithviper.presentation.project.list.view.adapter.ProjectListAdapter
import com.project.iosephknecht.mvvmsamplewithviper.presentation.managedBy
import com.project.iosephknecht.mvvmsamplewithviper.presentation.project.common.fragment.FragmentBackPressed
import com.project.iosephknecht.mvvmsamplewithviper.presentation.visible
import kotlinx.android.synthetic.main.fragment_project_list.*
import javax.inject.Inject

class ProjectListFragment : Fragment(), FragmentBackPressed {
    private lateinit var binding: FragmentProjectListBinding
    private lateinit var adapter: ProjectListAdapter

    companion object {
        const val TAG = "project_list_fragment"
        private const val PROJECT_DETAILS_TAG = "PROJECT_DETAILS_TAG"

        fun createInstance() = ProjectListFragment()
    }

    @set:Inject
    protected lateinit var viewModel: ProjectListContract.ViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)

        AppDelegate.presentationComponent
            .projectListSubcomponent()
            .with(this)
            .userName("Google")
            .build()
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProjectListBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ProjectListAdapter(viewModel::onClickProject)

        project_list.apply {
            this.adapter = this@ProjectListFragment.adapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(false)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL))
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.managedBy(viewLifecycleOwner)

        with(viewModel) {
            projects.observe(viewLifecycleOwner, Observer { projects ->
                projects?.also {
                    adapter.projectList = it
                    adapter.notifyDataSetChanged()
                }
            })

            showProjectDetail.observe(viewLifecycleOwner, Observer { (repoName, userName) ->
                showDetails(repoName = repoName, userName = userName)
            })

            toastMsg.observe(viewLifecycleOwner, Observer { message ->
                message?.also { Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show() }
            })

            isLoading.observe(viewLifecycleOwner, Observer { isLoading ->
                isLoading?.also { swapVisibility(it) }
            })
        }
    }

    override fun onBackPressed(): Boolean {
        if (childFragmentManager.backStackEntryCount >= 1) {
            childFragmentManager.popBackStackImmediate()
            return true
        }

        return false
    }

    private fun swapVisibility(isLoading: Boolean) {
        with(binding) {
            loadingProjects.visible(isLoading)
            projectList.visible(!isLoading)
        }
    }

    private fun showDetails(userName: String, repoName: String) {
        childFragmentManager.findFragmentByTag(PROJECT_DETAILS_TAG).also { fragment ->
            if (fragment == null) {
                childFragmentManager.beginTransaction()
                    .replace(
                        R.id.root,
                        ProjectDetailsFragment.createInstance(
                            repoName = repoName,
                            userName = userName
                        ),
                        PROJECT_DETAILS_TAG
                    )
                    .addToBackStack(null)
                    .commit()
            }
        }
    }
}