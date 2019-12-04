package rpl1pnp.fikri.footballmatchschedule

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class League (val idLeague: Int?, val nameLeague: String?, val photoLeague: Int?) :Parcelable