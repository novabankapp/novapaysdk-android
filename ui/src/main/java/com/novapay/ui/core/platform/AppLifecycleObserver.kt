package com.novapay.ui.core.platform

import androidx.lifecycle.*
import org.koin.core.component.KoinComponent

internal class AppLifecycleObserver : DefaultLifecycleObserver, KoinComponent {

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
    }

}