package com.viswakarma.jewelleryworks.viewmodel

import androidx.lifecycle.*
import com.viswakarma.jewelleryworks.model.datasource.local.roomdb.models.Catalogue
import com.viswakarma.jewelleryworks.model.repository.DataRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class CatalogueViewModel(private val dataRepository: DataRepository) : ViewModel(){

    private var catalogueItemsListUpdateJob: Job? = null
    private var searchQuery: String = ""
    private val _catalogueItems: MutableLiveData<List<Catalogue>> = MutableLiveData()
    val catalogueItems: LiveData<List<Catalogue>> get() = _catalogueItems

    init {
      /*  _catalogueItems.value = arrayListOf<Catalogue>().apply {
            add(Catalogue(2, OffsetDateTime.now(),"necklace","longchain","12345","Laxmi Haram",
                image,"Gold",20.000))
            add(Catalogue(2, OffsetDateTime.now(),"necklace","longchain","12345","Laxmi Haram",
                image,"Gold",20.000))
            add(Catalogue(2, OffsetDateTime.now(),"necklace","longchain","12345","Laxmi Haram",
                image,"Gold",20.000))
        }*/
        updateRecyclerViewWithCatalogueList()
    }

    private fun updateRecyclerViewWithCatalogueList(searchQuery: String = this.searchQuery){
        catalogueItemsListUpdateJob?.cancel()
        catalogueItemsListUpdateJob = viewModelScope.launch {
            dataRepository.getAllCatalogueItems(searchQuery).map { catalogueItems ->
                _catalogueItems.postValue(catalogueItems)
            }.collect()
        }
    }

    fun setSearchQuery(searchQuery: String){
        this.searchQuery = searchQuery
        updateRecyclerViewWithCatalogueList()
    }

}
