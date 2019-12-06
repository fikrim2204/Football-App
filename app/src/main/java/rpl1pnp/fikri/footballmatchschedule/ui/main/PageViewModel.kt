package rpl1pnp.fikri.footballmatchschedule.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class PageViewModel : ViewModel() {

    private val _index = MutableLiveData<Int>()
    private var idLeague: String? = "4328"
    val text: LiveData<String> = Transformations.map(_index) {
        "Hello world from section: $it"
    }

    fun setIndex(index: Int) {
        _index.value = index
    }

    fun setSelectedItem(idLeague: String?) {
        this.idLeague = idLeague
    }

    fun getSelectedItem(): String? {
        return idLeague
    }
}