package com.viswakarma.jewelleryworks.model.datasource.local.roomdb.dao

import androidx.room.*
import com.viswakarma.jewelleryworks.model.datasource.local.roomdb.models.Catalogue
import kotlinx.coroutines.flow.Flow

@Dao
interface CatalogueDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCustomer(catalogue: Catalogue)

    @Update
    suspend fun updateCatalogue(catalogue: Catalogue)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCatalogues(catalogues: List<Catalogue>)

    @Query("DELETE from catalogue")
    suspend fun deleteAllCatalogues()

    @Query("SELECT * FROM catalogue")
    suspend fun getAllCatalogues():List<Catalogue>

    @Query("SELECT * FROM catalogue WHERE id=(:id)")
    suspend fun getCatalogueById(id:String):Catalogue

//    @Query("SELECT * FROM catalogue ORDER BY dateTime DESC")
    @Query("""SELECT * FROM catalogue 
        WHERE name LIKE '%' || :searchText || '%' 
        or modelNo LIKE '%' || :searchText || '%' 
        or type LIKE '%' || :searchText || '%' 
        or metal LIKE '%' || :searchText || '%' 
        or weight LIKE '%' || :searchText || '%' 
        ORDER BY dateTime DESC""")
    fun getAllCataloguesFlow(searchText: String): Flow<List<Catalogue>>

}
