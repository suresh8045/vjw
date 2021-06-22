package com.viswakarma.jewelleryworks

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.viswakarma.jewelleryworks.model.datasource.local.roomdb.models.Catalogue
import com.viswakarma.jewelleryworks.model.datasource.local.roomdb.models.Metal
import com.viswakarma.jewelleryworks.model.repository.DataRepository
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.time.OffsetDateTime


class AddToCatalogueViewModel(private val dataRepository: DataRepository) : ViewModel() {

    private val _isCatalogueItemAdded: MutableLiveData<Boolean> = MutableLiveData(false)
    val isCatalogueItemAdded: LiveData<Boolean> = _isCatalogueItemAdded
    private val _itemTypes: MutableLiveData<List<String>> = MutableLiveData()
    val itemTypes: LiveData<List<String>> = _itemTypes

    private var catalogueImageBase64: String = ""
    private var selectedItemType: String = ""

    init {
        _itemTypes.value = dataRepository.getCatalogueItemTypes()
    }

    fun getItemFromPos(itemTypePos: Int): String{
        return _itemTypes.value?.get(itemTypePos).toString()
    }

    fun setSelectedItemType(itemType: String){
        selectedItemType = itemType
    }

    fun addCatalogueItem(
        name: String,
        imageBase64: String,
        category: String,
        type: String,
        weight: Double,
        metal: Metal
    ) {
        viewModelScope.launch {
            val catalogue = Catalogue(
                id = 0,
                OffsetDateTime.now(),
                category,
                type,
                modelNo = OffsetDateTime.now().toEpochSecond().toString(),
                name,
                imageBase64,
                metal.value,
                weight
            )
            dataRepository.addNewCatalogueItem(catalogue)
            _isCatalogueItemAdded.value = true
        }
    }

    fun getChoosenImageBase64(): String {
        return catalogueImageBase64
    }

    fun setChoosenImageBase64(context: Context, imageUri: Uri) {
        val imageStream = context.contentResolver.openInputStream(imageUri)
        val selectedImage = BitmapFactory.decodeStream(imageStream)
        val encodedImage = encodeImage(selectedImage)
        catalogueImageBase64 = encodedImage.toString()
    }

    private fun encodeImage(bm: Bitmap): String? {
        val baos = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val b: ByteArray = baos.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
    }

    fun getSelectedItemType(): String {
        return selectedItemType
    }

}