package com.project.iosephknecht.mvvmsamplewithviper.presentation

import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.project.iosephknecht.mvvmsamplewithviper.presentation.project.common.viewModel.ViewModelArch
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

fun Disposable.add(disposable: CompositeDisposable) {
    disposable.add(this)
}

fun View.visible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

@Suppress("unused")
fun ViewModelArch.managedBy(lifecycleOwner: LifecycleOwner) {
    lifecycleOwner.lifecycle.addObserver(object : LifecycleObserver {
        @OnLifecycleEvent(Lifecycle.Event.ON_START)
        fun onStart() {
            this@managedBy.onStart()
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
        fun onStop() {
            this@managedBy.onStop()
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        fun onDestroy() {
            lifecycleOwner.lifecycle.removeObserver(this)
        }
    })
}

@Suppress("unused")
fun <T : Any> T.managedBy(
    lifecycleOwner: LifecycleOwner,
    onCreate: ((T) -> Unit)? = null,
    onStart: ((T) -> Unit)? = null,
    onResume: ((T) -> Unit)? = null,
    onPause: ((T) -> Unit)? = null,
    onStop: ((T) -> Unit)? = null,
    onDestroy: ((T) -> Unit)? = null
) {
    lifecycleOwner.lifecycle.addObserver(object : LifecycleObserver {
        @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
        fun onCreate() {
            onCreate?.invoke(this@managedBy)
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_START)
        fun onStart() {
            onStart?.invoke(this@managedBy)
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
        fun onResume() {
            onResume?.invoke(this@managedBy)
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
        fun onPause() {
            onPause?.invoke(this@managedBy)
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
        fun onStop() {
            onStop?.invoke(this@managedBy)
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        fun onDestroy() {
            onDestroy?.invoke(this@managedBy)
            lifecycleOwner.lifecycle.removeObserver(this)
        }
    })
}