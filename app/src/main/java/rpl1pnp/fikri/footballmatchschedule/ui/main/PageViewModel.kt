package rpl1pnp.fikri.footballmatchschedule.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class PageViewModel : ViewModel() {

    val _index = MutableLiveData<Int>()
    val idLeague = MutableLiveData<String>()
    val text: LiveData<String> = Transformations.map(idLeague) {
        "Hello world from section: $it"
    }

    fun setIndex(index: Int?) {
        _index.value = index
    }

    fun setIdLeague(idLeague: String?) {
        this.idLeague.value = idLeague
    }
}