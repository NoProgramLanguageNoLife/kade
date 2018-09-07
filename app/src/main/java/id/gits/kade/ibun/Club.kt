package id.gits.kade.ibun

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Club (val name: String, val description: String, val logo: String) : Parcelable