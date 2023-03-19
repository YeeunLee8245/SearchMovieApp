package kr.co.yeeun.lee.demoi.searchmovieapp.ui.activity

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kr.co.yeeun.lee.demoi.searchmovieapp.data.model.Movie
import kr.co.yeeun.lee.demoi.searchmovieapp.data.remote.MovieDataStore
import kr.co.yeeun.lee.demoi.searchmovieapp.data.remote.MoviePagingSource
import kr.co.yeeun.lee.demoi.searchmovieapp.util.Constant
import org.json.JSONArray
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(private val movieDataStore: MovieDataStore, private val sharedPreferences: SharedPreferences) :
    ViewModel() {
    private val _historyList = MutableLiveData<LinkedList<String>>(LinkedList<String>())
    val historyList: LiveData<LinkedList<String>> = _historyList

    fun getMoviePagingSource(query: String): Flow<PagingData<Movie>> {
        return Pager(PagingConfig(Constant.ITEM_SIZE)) {
            MoviePagingSource(query, movieDataStore)
        }.flow.cachedIn(viewModelScope)
    }

    fun addHistoryItem(keyword: String) {
        historyList.value?.let { historyLi ->
            while (historyLi.size >= Constant.HISTORY_LIST_SIZE) {
                _historyList.value?.removeLast()
            }
            _historyList.value?.addFirst(keyword)
        }
        saveHistoryList()
    }

    fun setHistoryList() {
        sharedPreferences.getString(Constant.HISTORY_EDITOR_TAG, null)?.let { history ->
            val itemList = LinkedList<String>()
            val jsonArray = JSONArray(history)
            for (i in 0 until jsonArray.length()) {
                itemList.addLast(jsonArray.optString(i))
            }
//            Log.d("불러온 기록", jsonArray.toString())
            _historyList.value = itemList
        }
    }

    private fun saveHistoryList() {
        val jsonArray = JSONArray()
        historyList.value?.let { list ->
            for (item in list) {
                if (item.trim().isNotEmpty())
                    jsonArray.put(item)
            }
        }
//        Log.d("저장되는 기록", jsonArray.toString())
        sharedPreferences.edit().putString(Constant.HISTORY_EDITOR_TAG, jsonArray.toString()).apply()
    }
}