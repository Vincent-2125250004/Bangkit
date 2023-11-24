package com.dicoding.courseschedule.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.courseschedule.data.Course
import com.dicoding.courseschedule.data.DataRepository
import com.dicoding.courseschedule.util.QueryType
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: DataRepository) : ViewModel() {

    private val _queryType = MutableLiveData<QueryType>()
    private lateinit var getNearestCourse: LiveData<Course?>

    init {
        _queryType.value = QueryType.CURRENT_DAY
        setNearestCourse(QueryType.CURRENT_DAY)
    }

    fun setQueryType(queryType: QueryType) {
        _queryType.value = queryType
    }

    fun setNearestCourse(queryType: QueryType) {
        viewModelScope.launch {
            getNearestCourse = repository.getNearestSchedule(queryType)
        }
    }

    fun getNearestCourse(): LiveData<Course?> {
        return getNearestCourse
    }
}


