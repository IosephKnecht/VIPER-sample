package com.project.iosephknecht.mvvmsamplewithviper.presentation.project.list.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
    companion object {
        const val TAG = "project_list_fragment"
        private const val PROJECT_DETAILS_TAG = "PROJECT_DETAILS_TAG"

        fun createInstance() = ProjectListFragment()
    }

    @set:Inject
    protected lateinit var viewModel: ProjectListContract.ViewModel

    private val binding = MutableLiveData<FragmentProjectListBinding>()
    private val adapter = MutableLiveData<ProjectListAdapter>()

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
        binding.value = FragmentProjectListBinding.inflate(inflater, container, false)

        return binding.value!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter.value = ProjectListAdapter(viewModel::onClickProject)

        viewModel.managedBy(viewLifecycleOwner)

        binding.value!!.projectList.managedBy(viewLifecycleOwner,
            onCreate = { recyclerView ->
                recyclerView.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = this@ProjectListFragment.adapter.value!!
                    setHasFixedSize(false)
                    addItemDecoration(
                        DividerItemDecoration(
                            context,
                            DividerItemDecoration.HORIZONTAL
                        )
                    )
                }
            },
            onDestroy = {
                adapter.value = null
            }
        )
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        with(viewModel) {
            projects.observe(viewLifecycleOwner, Observer { projects ->
                projects?.also {
                    adapter.value?.apply {
                        reload(projects)
                        notifyDataSetChanged()
                    }
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
        with(binding.value!!) {
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