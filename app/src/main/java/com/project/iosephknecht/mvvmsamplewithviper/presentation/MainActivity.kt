package com.project.iosephknecht.mvvmsamplewithviper.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.project.iosephknecht.mvvmsamplewithviper.R
import com.project.iosephknecht.mvvmsamplewithviper.presentation.project.common.fragment.FragmentBackPressed
import com.project.iosephknecht.mvvmsamplewithviper.presentation.project.list.view.ProjectListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            val fragment = ProjectListFragment.createInstance()

            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, fragment, ProjectListFragment.TAG)
                .commit()
        }
    }

    override fun onBackPressed() {
        supportFragmentManager.findFragmentById(R.id.fragment_container).also { fragment ->
            if (fragment is FragmentBackPressed && fragment.onBackPressed()) {
                return
            }
        }

        super.onBackPressed()
    }
}
