package com.viswakarma.jewelleryworks.datasources

import com.viswakarma.jewelleryworks.api.ViswakarmaApiService
import javax.inject.Inject

class VRemoteDataSource @Inject constructor(private val viswakarmaApiService: ViswakarmaApiService) :
    BaseDataSource(){

  //  suspend fun getAllUserData(map:Map<String,String>,jsonBody:JsonObject) = getResult { webApiService.getAllUserData(map) }


}