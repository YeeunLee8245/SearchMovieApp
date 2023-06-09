package kr.co.yeeun.lee.demoi.searchmovieapp.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kr.co.yeeun.lee.demoi.searchmovieapp.R
import kr.co.yeeun.lee.demoi.searchmovieapp.databinding.FragmentSearchBinding
import kr.co.yeeun.lee.demoi.searchmovieapp.ui.OnMovieClickListener
import kr.co.yeeun.lee.demoi.searchmovieapp.ui.activity.MovieViewModel
import kr.co.yeeun.lee.demoi.searchmovieapp.ui.adapter.PagingLoadStateAdapter
import kr.co.yeeun.lee.demoi.searchmovieapp.ui.adapter.SearchAdapter
import kr.co.yeeun.lee.demoi.searchmovieapp.util.Constant
import kr.co.yeeun.lee.demoi.searchmovieapp.util.ShowAlert

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(), OnMovieClickListener {
    private val viewmodel: MovieViewModel by viewModels({requireActivity()})
    private var searchAdapter: SearchAdapter? = null
    private var pagingJob: Job? = null
    private var historyQuery: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        Log.d("부모 액티비티", requireActivity().toString())
//        Log.d("검색내역으로부터 받은 키워드", arguments?.getString(Constant.SEARCH_QUERY_TAG).toString())
        searchAdapter = SearchAdapter(this)
        historyQuery = arguments?.getString(Constant.SEARCH_QUERY_TAG)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewmodel.setHistoryList()
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
            latestButton.setOnClickListener { moveToHistoryScreen() }
            historyQuery?.let {
                searchHistory(it)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        searchAdapter = null
        pagingJob?.cancel()
    }

    private fun searchHistory(query: String) {
        binding?.apply {
            searchEditText.setText(query)
            getMovieResponse(query)
        }
    }

    private fun moveToHistoryScreen() {
        findNavController().navigate(R.id.action_search_fragment_to_history_fragment)
    }

    private fun clickSearch() {
        binding?.apply {
            if (searchEditText.text.trim().isNotEmpty()) {
//                Log.d("유효한 쿼리", searchEditText.text.toString())
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
//                Log.d("데이터 확인", "$query $data")
                searchAdapter?.submitData(data)
            }
        }
        viewmodel.addHistoryItem(query)
    }

    private fun checkErrorState(loadState: CombinedLoadStates) {
        binding?.apply {
            when (loadState.refresh) {
                is LoadState.Error -> {
//                    Log.d("오류 확인", loadState.toString())
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

    override fun onMovieItemClicked(url: String) {
        val uri = Uri.parse(url)
        val webIntent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(webIntent)
    }
}