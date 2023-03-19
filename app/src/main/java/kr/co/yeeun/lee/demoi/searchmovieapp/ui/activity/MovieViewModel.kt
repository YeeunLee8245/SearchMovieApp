package kr.co.yeeun.lee.demoi.searchmovieapp.ui.activity

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
import kr.co.yeeun.lee.demoi.searchmovieapp.data.remote.ApiService
import kr.co.yeeun.lee.demoi.searchmovieapp.data.remote.MovieDataStore
import kr.co.yeeun.lee.demoi.searchmovieapp.data.remote.MoviePagingSource
import kr.co.yeeun.lee.demoi.searchmovieapp.util.Constant
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(private val movieDataStore: MovieDataStore) :
    ViewModel() {
    fun getMoviePagingSource(query: String): Flow<PagingData<Movie>> {
        return Pager(PagingConfig(Constant.ITEM_SIZE)) {
            MoviePagingSource(query, movieDataStore)
        }.flow.cachedIn(viewModelScope)
    }
}