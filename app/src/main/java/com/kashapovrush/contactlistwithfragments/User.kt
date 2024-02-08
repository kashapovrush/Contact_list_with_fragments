package com.kashapovrush.contactlistwithfragments

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: Int = 0,
    val image: Uri? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val phoneNumber: String? = null
): Parcelable
