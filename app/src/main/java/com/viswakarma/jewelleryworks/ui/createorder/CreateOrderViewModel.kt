package com.viswakarma.jewelleryworks.ui.createorder

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.viswakarma.jewelleryworks.repository.ViswakarmaRepository
import com.viswakarma.jewelleryworks.roomdb.models.Orders
import com.viswakarma.jewelleryworks.util.Common
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class CreateOrderViewModel @Inject constructor(var viswakarmaRepository: ViswakarmaRepository) : ViewModel() {

    private var isSubmitted: MutableLiveData<Boolean> = MutableLiveData()

    fun createOrder(name: String, phone: String, description: String, amount: Int) {
        viewModelScope.launch {
            viswakarmaRepository.insertOrder(Orders(
                System.currentTimeMillis().toString(),
                name,
                (Date().time/1000L).toInt(),
                phone,
                description,
                amount,
                Common.getDateTimeStamp((Date().time/1000L).toInt()),
                Common.getDateTimeStamp((Date().time/1000L).toInt())
            ))
            isSubmitted.value = true
        }
    }


    fun getIsSumbitted(): LiveData<Boolean> {
        return isSubmitted
    }

}
