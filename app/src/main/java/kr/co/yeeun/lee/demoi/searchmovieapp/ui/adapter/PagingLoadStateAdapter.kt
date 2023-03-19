package kr.co.yeeun.lee.demoi.searchmovieapp.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import kr.co.yeeun.lee.demoi.searchmovieapp.databinding.LayoutLoadingBinding

class PagingLoadStateAdapter() : LoadStateAdapter<PagingLoadStateAdapter.ViewHolder>() {
    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LayoutLoadingBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    class ViewHolder(private val binding: LayoutLoadingBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(state: LoadState) {
//            Log.d("페이징 로딩 상태", state.toString())
            binding.apply {
                when (state) {
                    is LoadState.Error -> {
                        errorText.text = state.error.message
                        errorText.isVisible = true
                        loadingProgressBar.isVisible = false
                    }
                    is LoadState.Loading -> {
                        loadingProgressBar.isVisible = true
                    }
                    else -> {
                        errorText.isVisible = false
                        loadingProgressBar.isVisible = false
                    }
                }
            }
        }
    }
}