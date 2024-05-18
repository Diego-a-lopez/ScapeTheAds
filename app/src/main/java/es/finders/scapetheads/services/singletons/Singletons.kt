package es.finders.scapetheads.services.singletons

import es.finders.scapetheads.services.unity.UnityBridge
import org.koin.dsl.module

val appModule = module {
    single { UnityBridge() }
}