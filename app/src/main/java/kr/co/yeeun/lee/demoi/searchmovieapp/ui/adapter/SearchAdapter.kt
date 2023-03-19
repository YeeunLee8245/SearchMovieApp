package kr.co.yeeun.lee.demoi.searchmovieapp.ui.adapter

import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import kr.co.yeeun.lee.demoi.searchmovieapp.R
import kr.co.yeeun.lee.demoi.searchmovieapp.data.model.Movie
import kr.co.yeeun.lee.demoi.searchmovieapp.databinding.ItemSearchResultBinding

class SearchAdapter : PagingDataAdapter<Movie, SearchAdapter.ViewHolder>(SearchDiffUtil) {

    object SearchDiffUtil : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.link == newItem.link
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemSearchResultBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    class ViewHolder(private val binding: ItemSearchResultBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Movie) {
            binding.apply {
                if (item.image.isEmpty()) {
                    movieImage.setImageResource(R.drawable.no_image)
                } else {
                    Glide.with(root.context)
                        .load(item.image)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(movieImage)
                }
                titleText.text = "${root.context.getString(R.string.title)} : ${Html.fromHtml(item.title, Html.FROM_HTML_MODE_LEGACY)}"
                releaseText.text = "${root.context.getString(R.string.release)} : ${item.pubDate}"
                gradeText.text = "${root.context.getString(R.string.rate)} : ${item.userRating}"
            }
        }

    }
}