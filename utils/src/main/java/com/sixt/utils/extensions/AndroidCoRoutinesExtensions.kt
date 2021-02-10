package com.sixt.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job

fun Fragment.viewLaunchWhenStarted(handler: suspend CoroutineScope.() -> Unit): Job {
    return viewLifecycleOwner.lifecycleScope.launchWhenStarted(handler)
}

fun Fragment.launchWhenStarted(handler: suspend CoroutineScope.() -> Unit): Job {
    return lifecycleScope.launchWhenStarted(handler)
}

fun AppCompatActivity.launchWhenStarted(handler: suspend CoroutineScope.() -> Unit): Job {
    return lifecycleScope.launchWhenStarted(handler)
}
