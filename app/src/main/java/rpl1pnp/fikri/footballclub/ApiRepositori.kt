package rpl1pnp.fikri.footballclub

import java.net.URL

class ApiRepositori {
    fun doRequest(url: String) : String {
        return URL(url).readText()
    }
}