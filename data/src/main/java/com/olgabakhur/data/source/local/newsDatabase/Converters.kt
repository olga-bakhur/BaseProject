package com.olgabakhur.data.source.local.newsDatabase

import androidx.room.TypeConverter
import com.olgabakhur.data.model.news.Source
import com.olgabakhur.data.util.Constants.EMPTY

class Converters {

    @TypeConverter
    fun fromSource(source: Source): String {
        return source.name ?: EMPTY
    }

    @TypeConverter
    fun toSource(name: String): Source {
        return Source(id = name, name = name)
    }
}