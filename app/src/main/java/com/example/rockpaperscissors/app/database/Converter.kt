package com.example.rockpaperscissors.app.database

import androidx.room.TypeConverter
import com.example.rockpaperscissors.app.model.Game
import java.util.*

class Converter {

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }

    @TypeConverter
    fun resultToInt(result: Game.Result?): Int? {
        return result?.value
    }

    @TypeConverter
    fun fromInt(value: Int?): Game.Result? {
        return Game.Result.values().associateBy(Game.Result::value)[value]
    }
}