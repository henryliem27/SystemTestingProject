package com.example.myapplication.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.model.Doctor
import com.example.myapplication.util.buildDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class BookingDoctorViewModel(application: Application): AndroidViewModel(application),
    CoroutineScope {

    private val job = Job()

    val doctorLiveData = MutableLiveData<Doctor>()

    fun fetchSchedule(uuid: Int){
        launch {
            val db = buildDB(getApplication())
            doctorLiveData.postValue(db.doctorDao().selectSchedule(uuid))
        }
    }

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO
}