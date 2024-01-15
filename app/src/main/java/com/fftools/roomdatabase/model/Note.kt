package com.fftools.roomdatabase.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import kotlinx.parcelize.Parcelize

//  Không cần phải override khi dùng Parcelable
@Parcelize
@Entity(tableName = "Note")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var title: String = "",
    var content: String = "",
    var editTime: Long = 0
): Parcelable


//class HighlightedTypeConverter {
//    @TypeConverter
//    fun fromHighlighted(highlighted: Highlighted): String? {
//        return Gson().toJson(highlighted)
//    }
//
//    @TypeConverter
//    fun toHighlighted(json: String?): Highlighted? {
//        return Gson().fromJson(json, Highlighted::class.java)
//    }
//}