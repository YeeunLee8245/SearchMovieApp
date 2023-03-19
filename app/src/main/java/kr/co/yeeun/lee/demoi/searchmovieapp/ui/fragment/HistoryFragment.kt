package kr.co.yeeun.lee.demoi.searchmovieapp.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kr.co.yeeun.lee.demoi.searchmovieapp.R
import kr.co.yeeun.lee.demoi.searchmovieapp.databinding.FragmentHistoryBinding
import kr.co.yeeun.lee.demoi.searchmovieapp.ui.OnHistoryClickListener
import kr.co.yeeun.lee.demoi.searchmovieapp.ui.activity.MovieViewModel
import kr.co.yeeun.lee.demoi.searchmovieapp.ui.adapter.HistoryAdapter
import kr.co.yeeun.lee.demoi.searchmovieapp.util.Constant
import okhttp3.internal.notify
import okhttp3.internal.notifyAll

@AndroidEntryPoint
class HistoryFragment : BaseFragment<FragmentHistoryBinding>(), OnHistoryClickListener {
    private val viewmodel: MovieViewModel by viewModels({requireActivity()})
    private var historyAdapter: HistoryAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        historyAdapter = viewmodel.historyList.value?.let { HistoryAdapter(it, this) }
//        Log.d("히스토리 기록 화면에서 받음", viewmodel.historyList.value.toString())
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeUi()
    }

    override fun onDestroy() {
        super.onDestroy()
        historyAdapter = null
    }
    override fun getBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHistoryBinding {
        return FragmentHistoryBinding.inflate(inflater, container, false)
    }

    override fun subscribeUi() {
        binding?.apply {
            historyRecycler.apply {
                adapter = historyAdapter
            }
        }
    }

    override fun onHistoryItemClicked(keyword: String) {
//        Log.d("기록 클릭", keyword)
        val bundle = bundleOf(Constant.SEARCH_QUERY_TAG to keyword)
        findNavController().navigate(R.id.action_history_fragment_to_search_fragment, bundle)
    }

}