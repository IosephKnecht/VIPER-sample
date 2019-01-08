package com.project.iosephknecht.mvvmsamplewithviper.domain

import com.project.iosephknecht.mvvmsamplewithviper.data.gson.Project
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class GithubApiService(private val githubService: GithubService) {
    fun getProjectList(userId: String): Observable<List<Project>> {
        return githubService.getProjectList(userId)
            .subscribeOn(Schedulers.io())
    }

    fun getProjectDetails(user: String, projectId: String): Observable<Project> {
        return githubService.getProjectDetails(user, projectId)
            .subscribeOn(Schedulers.io())
    }
}