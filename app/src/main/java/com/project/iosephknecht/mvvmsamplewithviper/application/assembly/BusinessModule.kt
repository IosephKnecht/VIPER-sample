package com.project.iosephknecht.mvvmsamplewithviper.application.assembly

import com.project.iosephknecht.mvvmsamplewithviper.application.scopes.PerBusinessLayerScope
import com.project.iosephknecht.mvvmsamplewithviper.domain.GithubApiServiceImpl
import com.project.iosephknecht.mvvmsamplewithviper.domain.GithubApi
import com.project.iosephknecht.mvvmsamplewithviper.domain.GithubApiService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
internal class BusinessModule(private val baseUrl: String) {

    @Provides
    @PerBusinessLayerScope
    fun provideGithubService(): GithubApi {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        return retrofit.create(GithubApi::class.java)
    }

    @Provides
    @PerBusinessLayerScope
    fun provideGithubApiService(githubService: GithubApi): GithubApiService {
        return GithubApiServiceImpl(githubService)
    }
}