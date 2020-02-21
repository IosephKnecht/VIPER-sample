package com.project.iosephknecht.mvvmsamplewithviper.domain

import com.project.iosephknecht.mvvmsamplewithviper.data.Project
import io.reactivex.Single

interface GithubApiService {
    fun getProjectList(userId: String): Single<List<Project>>
    fun getProjectDetails(repoName: String, userId: String): Single<Project>
}