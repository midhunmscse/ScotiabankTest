package com.scotiabank.data.model

import android.os.Parcel
import android.os.Parcelable


data class RepoEntryItem(
    val description: String?,
    val forks: Int,
    val full_name: String?,
    val id: Int,
    val name: String?,
    val owner: Owner?
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readParcelable(Owner::class.java.classLoader)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(description)
        parcel.writeInt(forks)
        parcel.writeString(full_name)
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeParcelable(owner, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RepoEntryItem> {
        override fun createFromParcel(parcel: Parcel): RepoEntryItem {
            return RepoEntryItem(parcel)
        }

        override fun newArray(size: Int): Array<RepoEntryItem?> {
            return arrayOfNulls(size)
        }
    }
}

data class Owner(
    val avatar_url: String?,
    val login: String?,
    val id: Int,
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(avatar_url)
        parcel.writeString(login)
        parcel.writeInt(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Owner> {
        override fun createFromParcel(parcel: Parcel): Owner {
            return Owner(parcel)
        }

        override fun newArray(size: Int): Array<Owner?> {
            return arrayOfNulls(size)
        }
    }
}