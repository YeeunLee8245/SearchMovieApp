package kr.co.yeeun.lee.demoi.searchmovieapp.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.room.util.query
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kr.co.yeeun.lee.demoi.searchmovieapp.R
import kr.co.yeeun.lee.demoi.searchmovieapp.databinding.FragmentSearchBinding
import kr.co.yeeun.lee.demoi.searchmovieapp.ui.activity.MovieViewModel
import kr.co.yeeun.lee.demoi.searchmovieapp.ui.adapter.PagingLoadStateAdapter
import kr.co.yeeun.lee.demoi.searchmovieapp.ui.adapter.SearchAdapter
import kr.co.yeeun.lee.demoi.searchmovieapp.util.ShowAlert

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>() {
    private val viewmodel: MovieViewModel by viewModels({requireActivity()})
    private var searchAdapter: SearchAdapter? = null
    private var pagingJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("부모 액티비티", requireActivity().toString())
        searchAdapter = SearchAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeUi()
    }

    override fun getBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSearchBinding {
        return FragmentSearchBinding.inflate(inflater, container, false)
    }

    override fun subscribeUi() {
        binding?.apply {
            loadingProgressBar.isVisible = false
            movieRecycler.apply {
                adapter = searchAdapter?.withLoadStateFooter(PagingLoadStateAdapter())
                searchAdapter?.addLoadStateListener { loadState ->
                    checkErrorState(loadState)
                }
            }
            searchButton.setOnClickListener {clickSearch()}
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        searchAdapter = null
        pagingJob?.cancel()
    }

    private fun clickSearch() {
        binding?.apply {
            if (searchEditText.text.trim().isNotEmpty()) {
                Log.d("유효한 쿼리", searchEditText.text.toString())
                searchEditText.error = null
                movieRecycler.scrollToPosition(0)
                getMovieResponse(searchEditText.text.toString())
            } else {
                pagingJob?.cancel()
                searchEditText.error = getString(R.string.no_query)
            }
        }
    }

    private fun getMovieResponse(query: String) {
        pagingJob?.cancel()
        pagingJob = lifecycleScope.launch {
            viewmodel.getMoviePagingSource(query).collectLatest { data ->
                Log.d("데이터 확인", "$query $data")
                searchAdapter?.submitData(data)
            }
        }
    }

    private fun checkErrorState(loadState: CombinedLoadStates) {
        binding?.apply {
            when (loadState.refresh) {
                is LoadState.Error -> {
                    Log.d("오류 확인", loadState.toString())
                    if (loadingProgressBar.isVisible) {
                        (loadState.refresh as LoadState.Error).error.message?.let {
                            ShowAlert.showAlert(
                                root.context,
                                it
                            )
                        }
                    }
                    loadingProgressBar.isVisible = false
                }
                is LoadState.Loading -> {
                    binding?.loadingProgressBar?.isVisible = true
                }
                is LoadState.NotLoading -> {
                    binding?.loadingProgressBar?.isVisible = false
                }
            }
        }
    }
}