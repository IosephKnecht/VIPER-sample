package com.project.iosephknecht.mvvmsamplewithviper.presentation.project.details.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.project.iosephknecht.mvvmsamplewithviper.application.AppDelegate
import com.project.iosephknecht.mvvmsamplewithviper.databinding.FragmentProjectDetailsBinding
import com.project.iosephknecht.mvvmsamplewithviper.presentation.project.details.contract.ProjectDetailsContract
import com.project.iosephknecht.mvvmsamplewithviper.presentation.managedBy
import com.project.iosephknecht.mvvmsamplewithviper.presentation.visible
import javax.inject.Inject

class ProjectDetailsFragment : Fragment() {

    companion object {
        private const val USER_NAME_KEY = "user_id_key"
        private const val REPO_NAME_KEY = "project_name_key"

        fun createInstance(userName: String, repoName: String) = ProjectDetailsFragment().apply {
            arguments = Bundle().apply {
                putString(USER_NAME_KEY, userName)
                putString(REPO_NAME_KEY, repoName)
            }
        }
    }

    @set:Inject
    protected lateinit var viewModel: ProjectDetailsContract.ViewModel
    private var binding = MutableLiveData<FragmentProjectDetailsBinding>()

    override fun onAttach(context: Context) {
        super.onAttach(context)

        val userName = arguments!!.getString(USER_NAME_KEY)!!
        val repoName = arguments!!.getString(REPO_NAME_KEY)!!

        AppDelegate.presentationComponent
            .projectDetailsSubcomponent()
            .with(this)
            .repoName(repoName)
            .userName(userName)
            .build()
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.value = FragmentProjectDetailsBinding.inflate(inflater, container, false)
        return binding.value!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.managedBy(viewLifecycleOwner)
        binding.value!!.lifecycleOwner = viewLifecycleOwner
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        with(viewModel) {
            project.observe(viewLifecycleOwner, Observer { project ->
                project?.also { binding.value!!.project = it }
            })

            toastMsg.observe(viewLifecycleOwner, Observer { message ->
                message?.also { Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show() }
            })

            isLoading.observe(viewLifecycleOwner, Observer { isLoading ->
                isLoading.also { swapLoading(it) }
            })
        }
    }

    private fun swapLoading(isLoading: Boolean) {
        with(binding.value!!) {
            loadingProject.visible(isLoading)
            content.visible(!isLoading)
        }
    }
}