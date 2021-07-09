package com.viswakarma.jewelleryworks

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.viswakarma.jewelleryworks.model.datasource.local.roomdb.models.Transaction
import com.viswakarma.jewelleryworks.model.datasource.local.roomdb.models.TransactionType
import com.viswakarma.jewelleryworks.model.repository.DataRepository
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.OffsetDateTime
import java.util.*

class AddTransactionViewModel(private val dataRepository: DataRepository) : ViewModel() {

    lateinit var orderId: String
    private val _transactionTypes: MutableLiveData<List<String>> = MutableLiveData()
    val transactionTypes: LiveData<List<String>> = _transactionTypes

    private val _purity: MutableLiveData<List<Float>> = MutableLiveData()
    val purity: MutableLiveData<List<Float>> = _purity

    private val _isTransactionAdded: MutableLiveData<Boolean> = MutableLiveData(false)
    val isTransactionAdded: LiveData<Boolean> = _isTransactionAdded

    private val _weightLayoutVisibility: MutableLiveData<Boolean> = MutableLiveData(false)
    val weightLayoutVisibility: LiveData<Boolean> = _weightLayoutVisibility
    private val _amountLayoutVisibility: MutableLiveData<Boolean> = MutableLiveData(false)
    val amountLayoutVisibility: LiveData<Boolean> = _amountLayoutVisibility

    private val _amountInputError: MutableLiveData<String> = MutableLiveData(null)
    val amountInputError: LiveData<String> = _amountInputError



    private val _transactionType: MutableLiveData<String> = MutableLiveData("")
    val transactionType: LiveData<String> = _transactionType
    var weightInput: String = ""
    var purityPercentage: Float = 0.0f
    var amountInput: String = ""
    var descriptionInput: String = ""


    init {
        View.VISIBLE
        View.GONE
        _transactionTypes.value = TransactionType.getAllTransactionTypes()
        _purity.value = Transaction.getPurityPercentages().sortedDescending()
    }


    fun onConfirm(){
        Timber.d("onConfirm add Transaction wt:$weightInput, amount: $amountInput, desc: $descriptionInput")
        viewModelScope.launch {
            when(transactionType.value){
                TransactionType.MAKING_CHARGE.value -> {
                    if (amountInput.isNotBlank() && descriptionInput.isNotBlank()) {
                        dataRepository.addNewTransaction(
                            Transaction.newTransaction(
                                customerId = "",
                                orderId = orderId,
                                type = TransactionType.MAKING_CHARGE,
                                description = descriptionInput,
                                amount = amountInput.toInt()
                            )
                        )
                        _isTransactionAdded.postValue(true)
                    }
                }
                TransactionType.DISCOUNT.value -> {
                    if (amountInput.isNotBlank() && descriptionInput.isNotBlank()) {
                        dataRepository.addNewTransaction(
                            Transaction.newTransaction(
                                customerId = "",
                                orderId = orderId,
                                type = TransactionType.DISCOUNT,
                                description = descriptionInput,
                                amount = amountInput.toInt()
                            )
                        )
                        _isTransactionAdded.postValue(true)
                    }
                }
                TransactionType.PAID_AMOUNT.value ->{
                    if (amountInput.isNotBlank() && descriptionInput.isNotBlank()) {
                        dataRepository.addNewTransaction(
                            Transaction.newTransaction(
                                customerId = "",
                                orderId = orderId,
                                type = TransactionType.PAID_AMOUNT,
                                description = descriptionInput,
                                amount = amountInput.toInt()
                            )
                        )
                        _isTransactionAdded.postValue(true)
                    }
                }
                TransactionType.MISCELLANEOUS_CHARGES.value ->{
                    if (amountInput.isNotBlank() && descriptionInput.isNotBlank()) {
                        dataRepository.addNewTransaction(
                            Transaction.newTransaction(
                                customerId = "",
                                orderId = orderId,
                                type = TransactionType.MISCELLANEOUS_CHARGES,
                                description = descriptionInput,
                                amount = amountInput.toInt()
                            )
                        )
                        _isTransactionAdded.postValue(true)
                    }
                }
                else->{

                }
            }
        }
    }

    fun setSelectedPurityPercentage(percentage: Float){
        purityPercentage = percentage
    }

    fun setSelectedTransactionType(transactionType: String) {
        _transactionType.value = transactionType
        when {
            TransactionType.getMetalRelatedTransactionTypes().contains(transactionType) -> {
                _weightLayoutVisibility.value = true
                _amountLayoutVisibility.value = false
            }
            TransactionType.getAmountRelatedTransactionTypes().contains(transactionType) ->{
                _weightLayoutVisibility.value = false
                _amountLayoutVisibility.value = true
            }
            else ->{
                _weightLayoutVisibility.value = false
                _amountLayoutVisibility.value = false
            }
        }
    }
}