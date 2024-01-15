package com.fftools.roomdatabase.utils.extension

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import java.io.Serializable


fun <T : Parcelable?> Intent.parcelable(name: String?, clazz: Class<T>): T? {
    return this.extras?.parcelable(name, clazz)
}


fun <T : Parcelable?> Bundle.parcelable(name: String?, clazz: Class<T>): T? = when {
    Build.VERSION.SDK_INT >= 33 -> this.getParcelable(name, clazz)
    else -> @Suppress("DEPRECATION") clazz.cast(this.getParcelable(name))
}