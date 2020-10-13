package rpl1pnp.fikri.footballmatchschedule.ui

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import rpl1pnp.fikri.footballmatchschedule.util.CoroutineContextProvider
import kotlin.coroutines.CoroutineContext

class TestContextProvider : CoroutineContextProvider() {
    @ExperimentalCoroutinesApi
    override val main: CoroutineContext = Dispatchers.Unconfined
}