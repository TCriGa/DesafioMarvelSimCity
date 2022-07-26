package br.com.zup.marvel.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Users(
    var name: String = "",
    var email: String = "",
    var password: String = ""
) : Parcelable {
}