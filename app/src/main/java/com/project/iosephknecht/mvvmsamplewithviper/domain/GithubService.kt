package com.project.iosephknecht.mvvmsamplewithviper.domain

import com.project.iosephknecht.mvvmsamplewithviper.data.gson.Project
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubService {
    @GET("users/{user}/repos")
    fun getProjectList(@Path("user") user: String): Observable<List<Project>>

    @GET("/repos/{user}/{reponame}")
    fun getProjectDetails(@Path("user") user: String, @Path("reponame") projectName: String): Observable<Project>
}