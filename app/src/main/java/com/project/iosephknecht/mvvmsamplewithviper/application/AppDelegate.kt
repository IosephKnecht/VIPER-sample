package com.project.iosephknecht.mvvmsamplewithviper.application

import android.app.Application
import com.project.iosephknecht.mvvmsamplewithviper.application.assembly.*

class AppDelegate : Application() {

    private val HTTPS_API_GITHUB_URL = "https://api.github.com/"

    companion object {
        lateinit var businessComponent: BusinessComponent
        lateinit var presentationComponent: PresentationComponent
    }

    override fun onCreate() {
        super.onCreate()

        initDependencies()
    }

    private fun initDependencies() {
        val appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()

        businessComponent = DaggerBusinessComponent.builder()
            .appComponent(appComponent)
            .businessModule(BusinessModule(HTTPS_API_GITHUB_URL))
            .build()

        presentationComponent = DaggerPresentationComponent.builder()
            .businessComponent(businessComponent)
            .presentationModule(PresentationModule())
            .build()
    }
}