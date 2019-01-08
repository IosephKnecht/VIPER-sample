package com.project.iosephknecht.mvvmsamplewithviper.presentation.project

import android.databinding.BindingAdapter
import android.view.View

@BindingAdapter("visibleOrGone")
fun View.setVisibleOrGone(show: Boolean) {
    visibility = if (show) View.VISIBLE else View.GONE
}