package com.project.iosephknecht.mvvmsamplewithviper.domain

import com.project.iosephknecht.mvvmsamplewithviper.domain.gson.Project
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

internal interface GithubApi {
    @GET("users/{user}/repos")
    fun getProjectList(@Path("user") user: String): Single<List<Project>>

    @GET("/repos/{user}/{reponame}")
    fun getProjectDetails(@Path("user") user: String, @Path("reponame") projectName: String): Single<Project>
}