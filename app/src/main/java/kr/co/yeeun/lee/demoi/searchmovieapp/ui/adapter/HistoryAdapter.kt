package kr.co.yeeun.lee.demoi.searchmovieapp.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.co.yeeun.lee.demoi.searchmovieapp.databinding.ItemHistoryBinding
import kr.co.yeeun.lee.demoi.searchmovieapp.ui.OnHistoryClickListener
import java.util.LinkedList

class HistoryAdapter(private val itemList: LinkedList<String>, private val listener: OnHistoryClickListener) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemHistoryBinding.inflate(inflater, parent, false)
        return ViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int = itemList.size

    class ViewHolder(private val binding: ItemHistoryBinding, private val listener: OnHistoryClickListener) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            binding.apply {
//                Log.d("히스토리 아이템", item)
                keywordText.text = "${absoluteAdapterPosition+1}. $item"
                root.setOnClickListener { listener.onHistoryItemClicked(item) }
            }
        }
    }

}