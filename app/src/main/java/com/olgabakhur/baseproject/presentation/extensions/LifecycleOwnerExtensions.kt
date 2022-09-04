package com.olgabakhur.baseproject.presentation.extensions

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/* Collect when CREATED */
fun <T> LifecycleOwner.collectWhenCreated(flow: Flow<T>, collector: suspend (T) -> Unit) {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.CREATED) {
            flow.collect { collector(it) }
        }
    }
}

fun <T> LifecycleOwner.collectLatestWhenCreated(flow: Flow<T>, collector: suspend (T) -> Unit) {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.CREATED) {
            flow.collectLatest(collector)
        }
    }
}

/* Collect when STARTED  */
fun <T> LifecycleOwner.collectWhenStarted(flow: Flow<T>, collector: suspend (T) -> Unit) {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collect { collector(it) }
        }
    }
}

fun <T> LifecycleOwner.collectLatestWhenStarted(flow: Flow<T>, collector: suspend (T) -> Unit) {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collectLatest(collector)
        }
    }
}

/* Collect when RESUMED */
fun <T> LifecycleOwner.collectWhenResumed(flow: Flow<T>, collector: suspend (T) -> Unit) {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.RESUMED) {
            flow.collect { collector(it) }
        }
    }
}

fun <T> LifecycleOwner.collectLatestWhenResumed(flow: Flow<T>, collector: suspend (T) -> Unit) {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.RESUMED) {
            flow.collectLatest(collector)
        }
    }
}