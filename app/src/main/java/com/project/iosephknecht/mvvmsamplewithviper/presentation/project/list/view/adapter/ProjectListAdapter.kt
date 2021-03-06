package com.project.iosephknecht.mvvmsamplewithviper.presentation.project.list.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.project.iosephknecht.mvvmsamplewithviper.R
import com.project.iosephknecht.mvvmsamplewithviper.data.gson.Project
import com.project.iosephknecht.mvvmsamplewithviper.databinding.ItemProjectListBinding

class ProjectListAdapter(private val block: ((project: Project) -> Unit)) :
    RecyclerView.Adapter<ProjectListAdapter.ViewHolder>() {

    var projectList = listOf<Project>()

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<ItemProjectListBinding>(
            LayoutInflater.from(p0.context),
            R.layout.item_project_list, p0, false
        )

        return ViewHolder(binding)
    }

    override fun getItemCount() = projectList.size

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        with(p0) {
            binding.project = projectList[p1]
            binding.executePendingBindings()
            binding.root.setOnClickListener { block.invoke(projectList[p1]) }
        }
    }

    class ViewHolder(val binding: ItemProjectListBinding) : RecyclerView.ViewHolder(binding.root)
}