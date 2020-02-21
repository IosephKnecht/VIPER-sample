package com.project.iosephknecht.mvvmsamplewithviper.domain

import com.project.iosephknecht.mvvmsamplewithviper.data.Project
import io.reactivex.Single

internal class GithubApiServiceImpl(
    private val githubService: GithubApi
) : GithubApiService {

    override fun getProjectList(userId: String): Single<List<Project>> {
        return githubService.getProjectList(userId)
            .map { gsonProjectList -> gsonProjectList.map { ProjectMapper.fromGson(it) } }
    }

    override fun getProjectDetails(repoName: String, userId: String): Single<Project> {
        return githubService.getProjectDetails(userId, repoName)
            .map { gsonProject -> ProjectMapper.fromGson(gsonProject) }
    }
}