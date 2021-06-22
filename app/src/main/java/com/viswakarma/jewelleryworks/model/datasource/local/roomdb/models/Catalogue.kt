package com.viswakarma.jewelleryworks.model.datasource.local.roomdb.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.OffsetDateTime

@Entity(tableName = "catalogue")
data class Catalogue(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var dateTime: OffsetDateTime,
    var category: String,
    var type: String,
    var modelNo: String,
    var name: String,
    var image: String,
    var metal: String,
    var weight: Double
) {
    fun areItemsTheSame(newItem: Catalogue): Boolean {
        return id == newItem.id && modelNo == newItem.modelNo
    }

    fun areContentsTheSame(newItem: Catalogue): Boolean {
        return this == newItem
    }

    fun getWeightFormat(): String {
        return "W %.3fg".format(weight)
    }

    companion object{
        fun getItemTypes(): List<String> {
            return arrayListOf<String>().apply {
                add("Chains")
                add("Earrings")
                add("Rings")
                add("Pendants")
                add("Bangles")
                add("Necklaces")
                add("Bracelets")
                add("Mangalsutra")
                add("Nose Pins")
                add("Nose Screws")
                add("Nose Rings")
                add("Kids Bracelets")
                add("Haram")
                add("Waist Belt")
            }
        }
    }
}
