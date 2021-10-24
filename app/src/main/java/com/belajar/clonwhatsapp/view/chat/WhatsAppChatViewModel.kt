package com.belajar.clonwhatsapp.view.chat

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.belajar.clonwhatsapp.model.SampleData
import java.text.SimpleDateFormat
import java.util.*

class WhatsAppChatViewModel : ViewModel() {
    @SuppressLint("SimpleDateFormat")
    private val date = SimpleDateFormat("hh.mm a")
    private val strDate: String = date.format(Date())
    private val _getDateSample = MutableLiveData<List<SampleData>>()
    val getDataSample: LiveData<List<SampleData>> get() = _getDateSample
    private val _flag = MutableLiveData<Boolean>(false)
    val flag: LiveData<Boolean> get() = _flag
    private val data = mutableListOf<SampleData>()

    init {
        repeat(10) {
            data.add(
                SampleData(
                    name = "Hello, Welcome",
                    desc = "Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. $it",
                    imgUrl = "sample Url",
                    createDate = strDate
                )
            )
        }
        _getDateSample.value = data
    }

    fun addChat(list: SampleData) {
        _flag.value = !_flag.value!!
        data.add(list)
        _getDateSample.value = data
    }
}