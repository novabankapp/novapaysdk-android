package com.novapay.ui.core.platform

import androidx.lifecycle.*
import org.koin.core.KoinComponent
import org.koin.core.inject

internal class AppLifecycleObserver : DefaultLifecycleObserver, KoinComponent {

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
    }

}