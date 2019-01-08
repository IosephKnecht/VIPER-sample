package com.project.iosephknecht.mvvmsamplewithviper.application.assembly

import com.project.iosephknecht.mvvmsamplewithviper.application.scopes.PerBusinessLayerScope
import com.project.iosephknecht.mvvmsamplewithviper.domain.GithubApiService
import com.project.iosephknecht.mvvmsamplewithviper.domain.GithubService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class BusinessModule(private val baseUrl: String) {

    @Provides
    @PerBusinessLayerScope
    fun provideGithubService(): GithubService {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        return retrofit.create(GithubService::class.java)
    }

    @Provides
    @PerBusinessLayerScope
    fun provideGithubApiService(githubService: GithubService): GithubApiService {
        return GithubApiService(githubService)
    }
}